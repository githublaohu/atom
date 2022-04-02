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

class ConnectTo():
    connect_id:int
    space_id:int
    operation_type:str

    connect_type:str
    connect_name:str
    connect_addr:str
    connect_account:str
    connect_password:str
    connect_space:str

    mode:str
    colony_type:str
    connect_conf:map
    connect_route:str
    connect_size:str #++
    connect_count:str #++

    def __init__(self, data):
        if len(data) == 0:
            return
        
        self.connect_id = data['id']
        self.space_id = data['space_id']
        self.operation_type = data['operation_type']
        self.connect_type = data['source_type']
        self.connect_name = data['source_name']
        self.connect_addr = data['source_type']
        self.connect_account = data['source_account']
        self.connect_password = data['source_password']
        self.source_space = data['source_space']

        self.mode = data['mode']
        self.colony_type = data['colony_type']
        self.connect_conf = data['source_conf']
        self.connect_route = data['source_route']

