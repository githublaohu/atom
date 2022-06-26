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
from atom_runtime.connect.connnect import StreamAPI
import pymysql
import pymysql.cursors

class MySQLConnect(Connect):
    

    def initialization(self):
        self.connection = pymysql.connect(host=self.connect_to.connect_addr, 
                        port=self.connect_to.connect_port, 
                        user=self.connect_to.connect_account, 
                        password=self.connect_to.connect_password, 
                        db=self.connect_to.connect_space, 
                        charset='utf8mb4', 
                        cursorclass=pymysql.cursors.DictCursor)

    #def stream(self, content, func):
    def stream(self, content, data,stream:StreamAPI):
        pass

    def  read_whole(self , content:str ,data ):
        cursor = self.connection.cursor()
        cursor.execute(content,data)
        return cursor.fetchall()

    def  read(self , content:str ,data, limit:int , quantity:int ):
        cursor = self.connection.cursor()
        content = content + " limit  " + limit + " " + quantity
        cursor.execute(content)
        return cursor.fetchall()


    def write(self , conntent:str , data):
        cursor = self.connection.cursor()
        try:
            path = 'insert into ' + 'train_result(space,scene,experiment,data) ' \
                + 'values(\''+ \
                self.source_to.space_name + '\',\'' + \
                self.source_to.scene_name + '\',\'' + \
                self.source_to.operate_execute + '\',%s)'
            cursor.execute(conntent,data)
            self.connection.commit()
        except BaseException as err:
            """
                异常抛出
            """
            self.connection.rollback()
            raise err