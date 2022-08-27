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
import requests
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
        # 回调"算子运行自动完成"接口
        response = self.running_auto_finish(operator_create_to)
        
    def start_operators(self):
        data = json.loads(request.get_data(as_text=True))
        self.operator_service.start_operators(OperatorTo(**data))

    def suspend_operators(self):
        data = json.loads(request.get_data(as_text=True))
        self.operator_service.suspend_operators(OperatorTo(**data))

    def uninstall_operators(self):
        data = json.loads(request.get_data(as_text=True))
        self.operator_service.uninstall_operators(OperatorTo(**data))

    def running_auto_finish(self, operator_create_to:OperatorCreateTo):
        # nacos发现算子服务
        # nacos_client = nacos.NacosClient(server_addresses="124.223.198.143:8848", namespace="atom-dev")
        # server_list = nacos_client.list_naming_instance("atom-service-operator-consumer", namespace_id="atom-dev", group_name="DEFAULT_GROUP", healthy_only=True)
        # for server in server_list:
            # print(server)
            # request_param = {"taskId": "", "operatorRuntimeType": "", "runParameter":{}, "envs":{}}
            # requests.post("/lamp/atom/service/operator/taskEvent/startNodeTask", request_param)

        request_param = {"taskId": operator_create_to.task_id,
                        "operatorRuntimeType": operator_create_to.operator_to.operator_runtime_type, 
                        "runParameter":{}, 
                        "envs":{}}
        headers={'content-type':'application/json','charset':'UTF-8'}
        # todo dynamic url
        response = requests.post(url = "http://127.0.0.1:9002/lamp/atom/service/operator/taskEvent/runningAutoFinish",
                                 headers = headers,
                                 data = json.dumps(request_param))
        return response