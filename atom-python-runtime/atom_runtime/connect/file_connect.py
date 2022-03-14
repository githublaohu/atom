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


class FileConnect(Connect):

    def stream(self, content, data,stream:StreamAPI):
         with open(content, 'rb') as f:
            stream.batch(f.read())


    def  read_whole(self , content:str ,data ):
        with open(content, 'rb') as f:
             return f.read()

    def  read(self , content:str ,data, limit:int , quantity:int ):
         with open(content, 'rb') as f:
             return f.read()

    def write(self , conntent:str , data):
        file = open( conntent, 'wb')
        file.write(data)