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
from atom_runtime.transfer_object.operator.connect_to import ConnectTo


class StreamAPI():
    """
        传递的方法

    """
    count:int
    datas: list

    def batch(self,data):
        pass

    def stream(self ,data):
        pass

    def close(self):
        pass


class Connect():

    connect_to:ConnectTo

    def __init__(self, data):
        if len(data) == 0:
            return
        self.connect_to = ConnectTo(data)

    def __file_path__(self,content:str ,data):
        return data["file"]+content

    def initialization(self):
        pass

    def stream(self, content, data,stream:StreamAPI):
        pass

    def  read(self , content:str ,data, limit:int , quantity:int ):
        pass

    def  read_whole(self , content:str ,data ):
        pass

    def download(self,connect:str,path:str,data):
        pass

    def write(self , conntent:str , data):
        pass



