from Conversion import conversion
from ClickStream import cleanlog
from FakeLog import generate
from _datetime import datetime
from Utils import sqoop
from Utils import hadoop
from conf import configure as conf


if __name__ == '__main__':
    now = datetime.now()
    date_format = "%Y-%m-%d"
    strnow = now.strftime(date_format)
    #generate.generate_fake_apache_log('log')
    cleanlog.clean_click_stream_log()
    conversion.start_conversion(strnow)
    hadoop.execute_map_reduce(conf.UploadJar,
    			      conf.UploadMain,
			      conf.UploadInput + strnow,
			      conf.UploadOutput)

