from conf import configure
from Utils import hive, hadoop, sqoop
from datetime import datetime




"""
create_click_stream_log_table() --->
load_click_stream_log()         --->
create_conversation_table()     --->
exact_data()
"""

def create_click_stream_log_table():
    hql = 'create table if not EXISTS %s (' \
          'ip_address string, ' \
          'uuid string, ' \
          'url string, ' \
          'session_id string, ' \
          'session_times string, ' \
          'area_address string, ' \
          'local_address string, ' \
          'browser_type string, ' \
          'os string, ' \
          'refer_url string, ' \
          'receive_time string, ' \
          'user_id string,' \
          'csvp string) ' \
          'partitioned by (dt string) ' \
          'row format delimited fields terminated by \'\t\'' % (configure.CSLog_table)
    hive.execute_hql(hql=hql)


def create_conversion_table():
    # todo table content
    hql = 'create table if not EXISTS %s (' \
          'url string, ' \
          'uuid string, ' \
          'session_id string, ' \
          'csvp string) partitioned by (dt string)' \
          'row format delimited fields terminated by \'\t\'' % (configure.CInput_table)
    hive.execute_hql(hql)


def create_conversion_middle_result_table():
    hql = 'CREATE TABLE IF NOT EXISTS %s (' \
          'session STRING, ' \
          'uuid STRING, ' \
          'process STRING) ' \
          'PARTITIONED BY (dt STRING) ' \
          'ROW FORMAT DELIMITED FIELDS TERMINATED BY \'\t\'' % (configure.CMiddleResult_table)
    hive.execute_hql(hql)


def create_conversion_result_table():
    hql = 'CREATE TABLE IF NOT EXISTS %s (' \
          'process STRING,' \
          'counts INT,' \
          'countu INT)' \
          'PARTITIONED BY (dt STRING)' \
          'ROW FORMAT DELIMITED FIELDS TERMINATED BY \'\t\'' % (configure.CResult_table)
    hive.execute_hql(hql)


def create_tables():
    create_click_stream_log_table()
    create_conversion_table()
    create_conversion_middle_result_table()
    create_conversion_result_table()


def load_click_stream_log(dt):
    hql = 'load data inpath \'%s\' ' \
          'into table %s ' \
          'partition(dt=\'%s\')' % (configure.ClickStreamOutput, configure.CSLog_table,dt)
    hive.execute_hql(hql)


def load_conversion_result(dt):
    hql = 'load data inpath \'%s\' ' \
          'into table conversion_middle_result partition(dt=\'%s\')' % (configure.ConversionOutput, dt)
    hive.execute_hql(hql)
    hql = 'insert into table conversion_result partition(dt=\'%s\') ' \
          'select process, count(process), count(distinct(uuid)) ' \
          'from conversion_middle_result ' \
          'where dt=\'%s\' group by process' % (dt, dt)
    hive.execute_hql(hql)

def extract_data(old, new, dt):
    """
    select url uuid sessionid csvp from clickstream_log into conversion_table
    :return: None
    """
    hql = 'insert into table %s partition (dt=\'%s\') ' \
          'select url,uuid,uuid,csvp ' \
          'from %s ' \
          'where dt >= \'%s\' and dt <= \'%s\'' % (configure.CInput_table, dt, configure.CSLog_table, old, new)

    hive.execute_hql(hql)


def conversion(dt):

    if hadoop.check_dir(configure.ConversionOutput) == 0:
       hadoop.rm_dir(configure.ConversionOutput) 
    args = ['/index', '/detail', '/checkout']
    hadoop.execute_map_reduce(configure.ConversionJar,
                              configure.ConversionMain,
                              configure.ConversionInput+dt+'/',
                              configure.ConversionOutput,
                              *args)

def start_conversion(strnow):
    create_tables()
    load_click_stream_log(dt=strnow)
    extract_data(strnow, strnow, strnow)
    print("Start Conversion!!!!!!!!!!!!!!!!!!!!!!")
    conversion(strnow)
    load_conversion_result(strnow)
    #sqoop.export_hive_table(configure.SqoopInput+strnow, configure.SqoopExportTable)


if __name__ == '__main__':
    now = datetime.now()
    date_format = "%Y-%m-%d"
    strnow = now.strftime(date_format)
    conversion(strnow())
    load_conversion_result(strnow)
