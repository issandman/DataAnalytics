from Utils import hadoop
from conf import configure
import subprocess


"""
hadoop jar DataCleaning.jar
hdfs://localhost:9000/user/sandman/input/apache
hdfs://localhost:9000/user/sandman/output/clean
"""

def check_dir():
    if hadoop.check_dir(configure.ClickStreamInput) != 0:
        hadoop.mkdir(configure.ClickStreamInput)
    if hadoop.check_dir(configure.ClickStreamOutput) == 0:
        hadoop.rm_dir(configure.ClickStreamOutput)


def upload_log():
    hadoop.upload_file(configure.Apache_Log_Path, 'input/apache/log')


def clean_click_stream_log():
    check_dir()
    upload_log()
    hadoop.execute_map_reduce(configure.ClickStreamJar,
                              configure.ClickStreamMain,
                              configure.ClickStreamInput,
                              configure.ClickStreamOutput)


