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
from atom_runtime.connect.connnect import Connect,StreamAPI
from boto3.session import Session


class S3MQConnect(Connect):
    def initialization(self):
        session = Session(
                    aws_access_key_id=self.connect_to.connect_account,
                    aws_secret_access_key=self.connect_to.connect_password
                )

        self.s3 = session.resource('s3')
            

    def stream(self, content, data,stream:StreamAPI):
        self.s3.download_file(Filename = self.__file_path__() ,Key =   content, Bucket = data.get("bucket"))
        with open(self.__file_path__(), 'rb') as f:
            stream.batch(f.read())

    def  read(self , content:str ,data, limit:int , quantity:int ):
        pass

    def  read_whole(self , content:str ,data ):
        pass

    def download(self,connect:str,path:str,data):
        self.s3.download_file(Filename = path ,Key =   connect, Bucket = data.get("bucket"))

    def write(self , conntent:str , data):
        pass