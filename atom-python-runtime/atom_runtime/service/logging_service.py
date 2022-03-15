#############################################################################
#Copyright (c) [Year] [name of copyright holder]
#[Software Name] is licensed under Mulan PubL v2.
#You can use this software according to the terms and conditions of the Mulan PubL v2.
#You may obtain a copy of Mulan PubL v2 at:
#         http://license.coscl.org.cn/MulanPubL-2.0
#THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
#EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
#MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
#See the Mulan PubL v2 for more details.
#############################################################################
#from connect.cloud.aliyun_oss_contect import AliyunOSSConnect
import sys
import os
import datetime
import logging
import logging.handlers



class LoggingServcie():

    def __init__(self) :
        self._load_file()

    def  _load_file(self):
        # 创建一个流处理器handler并设置其日志级别为DEBUG
        handler = logging.StreamHandler(sys.stdout)
        handler.setLevel(logging.DEBUG)

        # 创建一个格式器formatter并将其添加到处理器handler
        formatter = logging.Formatter("%(asctime)s - %(name)s - %(levelname)s - %(message)s")
        handler.setFormatter(formatter)

        # 为日志器logger添加上面创建的处理器handler
        logging.root.addHandler(handler)
        if os.path.exists("./logs") == False:
            os.mkdir("./logs")  
        rf_handler = logging.handlers.TimedRotatingFileHandler('./logs/atrom-python-runtime.log', when='midnight', interval=1, backupCount=7, atTime=datetime.time(0, 0, 0, 0))
        #rf_handler.setFormatter(logging.Formatter("%(asctime)s - %(levelname)s - %(filename)s[:%(lineno)d] - %(message)s"))
        rf_handler.setFormatter(formatter)
        rf_handler.setLevel(logging.NOTSET)
        logging.root.addHandler(rf_handler)

