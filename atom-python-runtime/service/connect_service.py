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
from connect.cloud.aliyun_oss_contect import AliyunOSSConnect
from connect.connnect import Connect
from connect.file_connect import FileConnect
from connect.mysql_contect import MySQLConnect
from connect.s3_contect import S3MQConnect
from transfer_object.operator.connect_to import ConnectTo


class ConnectService():

    connect_id_to_connect:map = {}

    connect_type_to_connect:map ={}

    def __init__(self) :
        self.connect_type_to_connect["file"] = FileConnect
        self.connect_type_to_connect["MySQL"] = MySQLConnect
        self.connect_type_to_connect["S3"] = S3MQConnect
        self.connect_type_to_connect["aliyunOSS"] = AliyunOSSConnect
        pass
    
    def get_connect(self , connect_to:ConnectTo) :
        connect = self.connect_id_to_connect.get(connect_to.connect_id)
        if connect == None:
            connect_type = self.connect_type_to_connect[connect_to.connect_type]
            connect:Connect = connect_type()
            connect.connect_to = connect_to
            self.connect_id_to_connect[connect_to.connect_id] = connect
        return connect
