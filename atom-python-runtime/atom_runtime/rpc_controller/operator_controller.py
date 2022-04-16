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
import json
from flask import Flask,request
from atom_runtime.utils.data_handler_utils import DataHandler
from atom_runtime.service.operator_service import  OperatorService
from atom_runtime.transfer_object.operator.operator_create_to import OperatorCreateTo
from atom_runtime.transfer_object.operator.operator_to import OperatorTo


class OperatorController():

    operator_service:OperatorService

    def __init__(self,operator_service:OperatorService,app:Flask):
        self.operator_service = operator_service
        app.add_url_rule('/operator/create_operators', "/operator/create_operators",self.create_operators,methods=["POST"])
        app.add_url_rule('/operator/start_operators','/operator/start_operators',self.start_operators,methods=["POST"])
        app.add_url_rule('/operator/suspend_operators','/operator/suspend_operators',self.suspend_operators,methods=["POST"])
        app.add_url_rule('/operator/uninstall_operators','/operator/uninstall_operators',self.uninstall_operators,methods=["POST"])

    def create_operators(self):
        request_data = request.get_data(as_text=True)
        data = json.loads(request_data)
        data_handler:DataHandler = DataHandler()
        data = data_handler.humpToUnderline(data)
        operator_create_to = OperatorCreateTo(data)
        self.operator_service.create_operators(operator_create_to)
        
    def start_operators(self):
        data = json.loads(request.get_data(as_text=True))
        self.operator_service.start_operators(OperatorTo(**data))

    def suspend_operators(self):
        data = json.loads(request.get_data(as_text=True))
        self.operator_service.suspend_operators(OperatorTo(**data))

    def uninstall_operators(self):
        data = json.loads(request.get_data(as_text=True))
        self.operator_service.uninstall_operators(OperatorTo(**data))