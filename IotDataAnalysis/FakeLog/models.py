from conf.configure import DATABASECONFIG
import pymysql

class User:

    
    def __init__(self, info_dict):
        self.userId = info_dict.get('userId', None)
        self.userName = info_dict.get('userName', None)
        self.userAddr = info_dict.get('userAddr', None)
        self.userEmail = info_dict.get('userEmail', None)
        self.userpwd = info_dict.get('userpwd', None)


class MysqlHelper:

    def __init__(self):
        self.db = pymysql.connect(DATABASECONFIG['address'],
                                  DATABASECONFIG['user'],
                                  DATABASECONFIG['passwd'],
                                  DATABASECONFIG['database'])
    def execute_no_query(self, sql):
        cursor = self.db.cursor()
        cursor.execute(sql)
        self.db.commit()
        cursor.close()

    def execute_query(self, sql):
        cursor = self.db.cursor()
        cursor.execute(sql)
        data = cursor.fetchall()
        cursor.close()
        self.db.commit()
        return data


if __name__ == '__main__':
    sqlhelper = MysqlHelper()
    SQL = "SELECT book_id FROM books ORDER BY RAND() LIMIT 10"
    data = sqlhelper.execute_query(SQL)
    print(data)