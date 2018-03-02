import subprocess
from conf import configure

def get_sqoop_args():
    sqoop_args = {
        'connect': 'jdbc:mysql://%s:3306/%s' % (configure.DATABASECONFIG['address'],
                                                configure.DATABASECONFIG['database']),
        'username': configure.DATABASECONFIG['user'],
        'password': configure.DATABASECONFIG['passwd'],
        'fields-terminated-by': '\'\\t\'',
        'm': 10,
	'bindir':'./sqoopgen'
    }
    return sqoop_args

def export_hive_table(hive, mysql):
    sqoop_args = get_sqoop_args()
    sqoop_args['export-dir'] = hive
    sqoop_args['table'] = mysql
    cmd = "sqoop export "
    for key, value in sqoop_args.items():
        cmd += "--%s %s " % (key, value)
    execute_sqoop(cmd)


def import_mysql_table(hive, mysql):
    sqoop_args = get_sqoop_args()
    sqoop_args['table'] = mysql
    sqoop_args['--target-dir'] = hive
    cmd = "sqoop import "
    for key, value in sqoop_args.items():
        cmd += "--%s %s" % (key, value)
    execute_sqoop(cmd)


def execute_sqoop(shell):
    print(shell)
    sts = subprocess.run(shell, shell=True, stdout=subprocess.PIPE)
    if sts.returncode != 0:
        print(sts.returncode)


if __name__ == '__main__':
    export_hive_table(configure.SqoopInput, configure.SqoopExportTable)
