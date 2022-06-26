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
from atom_runtime.connect.connnect import Connect
import oss2

class AliyunOSSConnect(Connect):
    """
        1. 要支持流式下载
        2. 要支持断点续传下载
    """

    bucket_dict:map={}

    def initialization(self):
        super().initialization()
        self.auth = oss2.Auth(self.connect_to.connect_account, self.connect_to.connect_password)
    
    def __get__bucket(self, data):
        bucket_name = data.get("bucket")
        bucket = self.bucket_dict.get(bucket_name)
        if bucket == None:
            bucket = oss2.Bucket(self.auth, self.connect_to.connect_space, data.get("bucket"))
            self.bucket_dict[bucket_name] = bucket
        return bucket

    def stream(self, content, func):
            pass

    def read_whole(self , connent:str,data  ):
        self.__get__bucket(data).get_object_to_file(connent, self.__file_path__(connent,data))
        with open(self.config.source_addr, 'rb') as f:
             return f

    def  read(self , connent:str ,data, limit:int , quantity:int ):
        self.__get__bucket(data).get_object_to_file(connent, self.__file_path__(connent,data))
        with open(self.config.source_addr, 'rb') as f:
             return f

    def download(self,connent:str,path:str,data):
        self.__get__bucket(data).get_object_to_file(connent, path)


    def write(self , conntent:str , data):
        return  self.__get__bucket(data).put_object(conntent, data)