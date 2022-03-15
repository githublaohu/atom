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

from flask import Flask
from atom_runtime.utils.environment import name_atom

from atom_runtime.service.operator_service import OperatorService

app = Flask(name_atom)

class ReasoningController():

    operator_service:OperatorService

    def __init__(self,operator_service: OperatorService):
        self.operator_service = operator_service

    def predict(self):
        return self.operator_service.predict()