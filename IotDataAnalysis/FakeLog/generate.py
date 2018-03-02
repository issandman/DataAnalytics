from conf.configure import CONFIG_USERAGENT_PC, CONFIG_USERAGENT_PHONE, Apache_Log_Path
import datetime
import random
import numpy
from faker import Faker
from FakeLog.models import User, MysqlHelper

"""
User-Agent looks like:
ip - - [dd/MMM/yyyy:HH:mm:ss] "url_str" status_code bytes "last_url" "cookie" receive_byte send_byte - host_addr handle_time
Particularly:
Cookie contains:
uuid=""
userId=""   #null refers to user not login
st=""       #sessionTimes
"""

faker = Faker()
mysqlhelper = MysqlHelper()


def get_random_ip():
    return faker.ipv4()


def get_random_pc_ua():
    return random.choice(CONFIG_USERAGENT_PC)


def get_random_phone_ua():
    return random.choice(CONFIG_USERAGENT_PHONE)


def get_random_time():
    from tzlocal import get_localzone
    local = get_localzone()
    increment = datetime.timedelta(seconds=random.randint(30, 300))
    dt = datetime.datetime.now().strftime('%d/%b/%Y:%H:%M:%S')
    tz = datetime.datetime.now(local).strftime('%z')
    return dt + " " + tz

def get_mysql_date(date):
    current_date = date.split(' ')[0]
    current_date = datetime.datetime.strptime(current_date, '%d/%b/%Y:%H:%M:%S').strftime('%Y-%m-%d %H:%M:%S')
    return current_date



def get_random_cookie(user_id = None):
    cookie = {
        'uuid': str(random.randint(1000, 1000000)).zfill(8),
        'userId': get_random_userid() if user_id is None else user_id,
        'st': str(random.randint(1000, 9999))
    }
    c = list()
    for key, value in cookie.items():
        c.append('%s=%s' % (key, value))
    cookie = ";".join(c)
    return cookie

def get_random_userid():
    sql = "SELECT userid from user ORDER BY RAND() LIMIT 1"
    userid = mysqlhelper.execute_query(sql)[0][0]
    return userid

def get_random_bookid():
    sql = "SELECT book_id from books ORDER BY RAND() LIMIT 1"
    bookid = mysqlhelper.execute_query(sql)[0][0]
    return bookid


def insert_new_order(user_id, book_id, date=None):
    sql=""
    if date is None:
        sql = "INSERT INTO orders(user_id, book_id, date) VALUES (%s,%s,NOW())" % (user_id, book_id)
    else:
        sql = 'INSERT INTO orders(user_id, book_id, date) VALUES (%s,%s,"%s")' % (user_id, book_id, date)

    mysqlhelper.execute_no_query(sql)



"""
User must login
Last url maybe login or detail page
"""


def generate_checkout_log():
    book_id = get_random_bookid()
    user_id = get_random_userid()

    insert_new_order(user_id=user_id, book_id=book_id)

    ip = get_random_ip()
    date = get_random_time()
    uri = '/checkout/%s' % book_id
    referer = "/detail/%s" % book_id
    user_agent = get_random_pc_ua()
    cookie = get_random_cookie(user_id=user_id)
    referer_log = generate_detail_log(book_id=book_id,
                                      ip=ip,
                                      date=date,
                                      user_agent=user_agent,
                                      cookie=cookie)
    log = generate_log(ip=ip,
                       date=date,
                       uri=uri,
                       referer=referer,
                       cookie=cookie,
                       user_agent=user_agent)
    return referer_log + log


"""
User may not login
Last url is index page
"""


def generate_detail_log(**kwargs):
    bookid = get_random_bookid() if kwargs.get('bookid') is None else kwargs.get('bookid')
    ip = get_random_ip() if kwargs.get('ip') is None else kwargs.get('ip')
    date = get_random_time() if kwargs.get('date') is None else kwargs.get('date')
    user_agent = get_random_pc_ua() if kwargs.get('user_agent') is None else kwargs.get('user_agent')
    cookie = get_random_cookie() if kwargs.get('cookie') is None else kwargs.get('cookie')
    uri = '/detail/%s' % bookid
    referer = "/index"
    referer_log = generate_index_log(ip=ip,
                                     date=date,
                                     user_agent=user_agent,
                                     cookie=cookie)
    log = generate_log(ip=ip,
                       date=date,
                       uri=uri,
                       referer=referer,
                       cookie=cookie,
                       user_agent=user_agent)
    return referer_log + log


"""
User may not login
Referer maybe "-" or logout page
"""


def generate_index_log(**kwargs):
    ip = get_random_ip() if kwargs.get('ip') is None else kwargs.get('ip')
    date = get_random_time() if kwargs.get('date') is None else kwargs.get('date')
    user_agent = get_random_pc_ua() if kwargs.get('user_agent') is None else kwargs.get('user_agent')
    cookie = get_random_cookie() if kwargs.get('cookie') is None else kwargs.get('cookie')
    ip = get_random_ip() if ip is None else ip
    date = get_random_time() if date is None else date
    uri = '/index'
    referer = "-"
    log = generate_log(ip, date, uri, referer, cookie, user_agent)
    return log


def generate_log(ip, date, uri, referer, cookie, user_agent, verb="GET"):
    resp = 200
    byt = str(int(random.gauss(5000, 50)))
    rbyte = str(int(random.gauss(5000, 50)))
    sbyte = str(int(random.gauss(5000, 50)))
    handle_time = str(int(random.gauss(5000, 50)))
    host_addr = "http://127.0.0.1"
    return '%s - - [%s] "%s %s HTTP/1.0" %s %s "%s" "%s" "%s" %s %s - %s %s\n' % (ip, date, verb, uri, resp, byt, referer, user_agent, cookie, rbyte, sbyte, host_addr, handle_time)


def insert_user(user):
    assert type(user) is User
    sql = 'INSERT INTO user(user_name, user_addr, user_email, user_password) VALUES ("%s","%s","%s","%s")' % (
        user.userName, user.userAddr, user.userEmail, user.userpwd)

    # print(sql)
    mysqlhelper.execute_no_query(sql)


def generate_fake_user():
    info = {
        'userId': None,
        'userName': faker.name(),
        'userAddr': faker.address().replace('\n', ' '),
        'userEmail': faker.free_email(),
        'userpwd': faker.password()
    }
    user = User(info)
    return user


def generate_fake_apache_log(log_path=None, num=100000):


    log_path = Apache_Log_Path if log_path is None else log_path
    f = open(log_path, 'w')
    glog = [generate_checkout_log, generate_detail_log, generate_index_log]
    for _ in range(0, num):
        g = numpy.random.choice(glog, p=[0.2, 0.3, 0.5])
        log = g()
        f.write(log)
    f.close()



if __name__ == '__main__':
    pass
