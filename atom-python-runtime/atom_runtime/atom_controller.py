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
import signal
import logging
from flask import Flask

from atom_runtime.utils.environment import get_env
from atom_runtime.rpc_service.rpc_runtime_service import RpcRuntimeService
from atom_runtime.service.logging_service import LoggingServcie
from atom_runtime.transfer_object.operator.operator_create_to import OperatorCreateTo

from atom_runtime.atom_config import  AtomConfigServier , AtomConfig
from atom_runtime.rpc_controller.operator_controller import OperatorController
from atom_runtime.rpc_controller.reasoning_controller import ReasoningController
from atom_runtime.rpc_service.rpc_operator_service import RpcOperatorServcie
from atom_runtime.service.code_service import CodeService
from atom_runtime.service.connect_service import  ConnectService
from atom_runtime.service.register_service import RegisterService
from atom_runtime.service.operator_service import OperatorService
from atom_runtime.service.rpc_controller_service import RpcControllerServcie
from atom_runtime.service.rpc_servcie_servcie import HttpClient, RpcServcieServcie
from atom_runtime.service.source_service import SourceService

app = Flask(__name__)

class AtomController():
    atom_config:AtomConfig
    code_service:CodeService
    register_service: RegisterService = None
    source_service:SourceService
    connect_service:ConnectService
    service_service:RpcServcieServcie
    

    operator_service: OperatorService

    rpc_controller_servcie:RpcControllerServcie

    def __init__(self) :
        self.__init_config__()
        self.__init_logging__()
        self.__init_code__()
        self.__init_source_servcie__()
        self.__init_connect_service__()
        self.__init_register_service__()
        self.__init_rpc_servcie__()
        self.__init_operator_service__()
        # 注意 rpc_controller 会启动http servier
        self.__init_rpc_controller__()
        pass

    def __init_config__(self):
        atom_config_service:AtomConfigServier = AtomConfigServier()
        self.atom_config = atom_config_service.get_atrom_config()
    
    def __init_logging__(self):
        self.logging_servcie = LoggingServcie()
        
    def __init_code__(self):
        self.code_service = CodeService(self.atom_config)

    def __init_register_service__(self):
        self.register_service = RegisterService(self.atom_config)
        pass
        

    def __init_connect_service__(self):
        self.connect_service:ConnectService = ConnectService()

    def __init_source_servcie__(self):
        self.source_service = SourceService()



    def __init_rpc_servcie__(self):
        if  self.atom_config.is_local():
            return
        self.service_service = RpcServcieServcie(self.register_service)
        http_client:HttpClient = self.service_service.get_http_client()
        http_client.register_servcie = self.register_service

        self.rpc_operator_servcie:RpcOperatorServcie = RpcOperatorServcie()
        self.rpc_operator_servcie.client = http_client
        self.rpcRuntimeService:RpcRuntimeService = RpcRuntimeService()
        self.rpcRuntimeService.client = http_client;
        if self.atom_config.docker_model :
           self.atom_config.rpc_controller_port = self.rpcRuntimeService.get_internet_protocol_address()

    def __init_operator_service__(self):
        self.operator_service = OperatorService()
        self.operator_service.atom_config = self.atom_config
        self.operator_service.code_service = self.code_service
        self.operator_service.connect_service = self.connect_service
        self.operator_service.source_service = self.source_service
        self.operator_service.rpc_operator_service = self.rpc_operator_servcie

    def __init_rpc_controller__(self):
        operator_controller = OperatorController(self.operator_service,app)
        reasoning_controller = ReasoningController(self.operator_service)
        self.rpc_controller:RpcControllerServcie = RpcControllerServcie(self.atom_config,app)
        self.rpc_controller.register_controller(operator_controller)
        self.rpc_controller.register_controller(reasoning_controller)
    
    def __pattern_discern_(self):
        operator_id = get_env("operator-id")
        if operator_id != None:
            operator_create_to = self.rpc_operator_servcie.query_operator_by_id(operator_id)
            self.operator_service.create_operators(operator_create_to)
        else:
            operator_create_string = get_env("operator-data")
            if operator_create_string != None:
                operator_create_to = OperatorCreateTo(json.loads(operator_create_string))
                self.operator_service.create_operators(operator_create_to)

    def local_load_remote_operator(self , operator_id):
        operator_create_to = self.rpc_operator_servcie.query_operator_by_id(operator_id)
        operator_create_to.operator_to.code_mode = None
        self.operator_service.create_operators(operator_create_to)

    def local_operator(self , operator_create_to:OperatorCreateTo):
        self.operator_service.create_operators(operator_create_to)


    def run(self):
        self.__pattern_discern_()
        self.rpc_controller.run()
    
    def __shutdown_hook__(self):
        signal.signal(signal.SIGTERM, self.__shutdown_hndler_)
    
    def __shutdown_hndler_(self):
        pass


def main():
    atom_controller = AtomController()
    logging.info("AtomController create success")
    atom_controller.run()