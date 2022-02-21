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
from connect.connnect import Connect
from transfer_object.operator.connect_to import ConnectTo
from transfer_object.operator.model_to import ModelTo
from transfer_object.operator.operator_to import OperatorTo
from transfer_object.operator.source_account_to import SourceAccountTo
from transfer_object.operator.source_to import SourceTo

class SourceAndConnect():
    # 推理之外，都是必须
    source_to:SourceTo
    connect_to:ConnectTo


class OperatorCreateTo():
    source_and_connects:List[SourceAndConnect]
    model_to:ModelTo= None
    source_account:SourceAccountTo = None
    operator_to:OperatorTo= None
    # 训练 产生模型，推理使用模型
    model_connect:Connect = None


