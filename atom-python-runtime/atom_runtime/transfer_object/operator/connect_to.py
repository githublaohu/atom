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
    connect_port:int
    connect_account:str
    connect_password:str
    connect_space:str

    mode:str
    colony_type:str
    source_conf:map
    source_route:str
    source_size:str
    source_count:str

    def __init__(self, data):
        if len(data) == 0:
            return
        
        self.connect_id = data['id']
        self.space_id = data['space_id']
        self.operation_type = data['operation_type']
        self.connect_type = data['connect_type']
        self.connect_name = data['connect_name']
        self.connect_addr = data['connect_addr']
        self.connect_port = data['connect_port']
        self.connect_account = data['connect_account']
        self.connect_password = data['connect_password']
        self.connect_space = data['connect_space']

        self.mode = data['mode']
        self.colony_type = data['colony_type']
        self.source_conf = data['source_conf']
        self.source_route = data['source_route']
        self.source_size = data['source_size']
        self.source_count = data['source_count']
