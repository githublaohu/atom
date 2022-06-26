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

from typing import List
from atom_runtime.connect.connnect import Connect
from atom_runtime.transfer_object.operator.connect_to import ConnectTo
from atom_runtime.transfer_object.operator.model_to import ModelTo
from atom_runtime.transfer_object.operator.operator_to import OperatorTo
from atom_runtime.transfer_object.operator.source_account_to import SourceAccountTo
from atom_runtime.transfer_object.operator.source_to import SourceTo

class SourceAndConnect():
    # 推理之外，都是必须
    source_to:SourceTo
    connect_to:ConnectTo

    def __init__(self, data = {}):
        if len(data) == 0:
            return
        self.source_to = SourceTo(data['source_to'])
        self.connect_to = ConnectTo(data['connect_to'])


class OperatorCreateTo():
    source_and_connects:List[SourceAndConnect] = list
    model_to:ModelTo= None
    source_account:SourceAccountTo = None
    operator_to:OperatorTo= None
    # 训练 产生模型，推理使用模型
    model_connect:Connect = None



    def __init__(self, data:dict):
        if len(data) == 0:
            return
        source_and_connect_list = list(data['source_and_connects'])
        self.source_and_connects = list()
        for item in source_and_connect_list:
            source_and_connect = SourceAndConnect(item)
            self.source_and_connects.append(source_and_connect)
        self.model_to = ModelTo(data['model_to'])
        # self.source_account = SourceAccountTo(data['source_account'])
        self.operator_to = OperatorTo(data['operator_to'])
        self.model_connect = Connect(data['model_connect'])