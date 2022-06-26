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
from multiprocessing import pool,cpu_count
from multiprocessing.pool import ThreadPool
from pathlib import Path
import yaml
from atom_runtime.atom_config import AtomConfig
from atom_runtime.source.source import Source
from atom_runtime.transfer_object.operator.source_to import SourceTo

from atom_runtime.connect.connnect import Connect
from atom_runtime.operators.reasoning_operator import ReasoningOperatorRuntime
from atom_runtime.operators.train_operator import TrainOperatorRuntime

from atom_runtime.atom_runtime_api.operator.operators_api import OperatorApi
from atom_runtime.operators.operator import OperatorRuntime
from atom_runtime.rpc_service.rpc_operator_service import RpcOperatorServcie
from atom_runtime.service.code_service import CodeService
from atom_runtime.service.connect_service import ConnectService

from atom_runtime.service.source_service import SourceService
from atom_runtime.transfer_object.operator.operator_create_to import OperatorCreateTo, SourceAndConnect
from atom_runtime.transfer_object.operator.operator_to import OperatorTo


class OperatorService():
    atom_config:AtomConfig
    connect_service:ConnectService
    source_service:SourceService
    code_service:CodeService
    rpc_operator_service:RpcOperatorServcie

    experiment_id_to_operator:map = {}
    operator_type_to_operator_runtime : map = {}

    source_tread_pool:ThreadPool = ThreadPool(cpu_count())
    runtime_tread_pool:ThreadPool = ThreadPool(cpu_count())

    def __init__(self):
        atom_config = AtomConfig()
        connect_service = ConnectService()
        source_service = SourceService()
        code_service = CodeService(atom_config)
        rpc_operator_service = RpcOperatorServcie()

        self.operator_type_to_operator_runtime["TRAIN"] = TrainOperatorRuntime
        self.operator_type_to_operator_runtime["REASONING"] = ReasoningOperatorRuntime
        self.operator_type_to_operator_runtime["features"] = TrainOperatorRuntime
        self.operator_type_to_operator_runtime["python-data"] = TrainOperatorRuntime

    def create_operators(self, operator_create_to:OperatorCreateTo):
        create_operator = CreateOperator(self,operator_create_to)
        operator_runtime = create_operator.get_operator_runtime()
        #todo 场景是否通过节点来传入
        #self.__close_runtime__(operator_create_to.operator_to)
        #self.experiment_id_to_operator[operator_create_to.operator_to.experiment_id] = operator_runtime
        self.rpc_operator_service.create_operators(operator_create_to.operator_to)
        self.source_tread_pool.apply_async(operator_runtime.do_run)

    def start_operators(self, operator_to:OperatorTo):
        pass

    def suspend_operators(self, operator_to:OperatorTo):
        pass

    def uninstall_operators(self, operator_to:OperatorTo):
        self.__close_runtime__(operator_to)

    def predict(self):
            return None
    
    def __close_runtime__(self , operator_to:OperatorTo):
        old_runtime = self.experiment_id_to_operator.get(operator_to.experiment_id)
        if old_runtime == None:
            pass

class CreateOperator():
    operator_create_to:OperatorCreateTo
    operator_to:OperatorTo
    operator_service:OperatorService
    operator_api:OperatorApi
    operator_runtime:OperatorRuntime

    def __init__(self,operator_service:OperatorService ,operator_create_to:OperatorCreateTo) :
        self.operator_create_to = operator_create_to
        self.operator_to = operator_create_to.operator_to
        self.operator_service = operator_service
        self.__create_object__()
        self.__operator_config__()
        self.__create_operator_runtime__()
        self.__create_connect_and_source__()
    
    def __create_object__(self):
        self.operator_api : OperatorApi= self.operator_service.code_service.get_object(self.operator_create_to.operator_to,
        self.operator_create_to.source_account)()
        self.operator_api.do_initialization()

    def __operator_config__(self):
        self.__config_handler__( self.operator_to.model_conf,self.operator_api.set_mode_config )
        self.__config_handler__(self.operator_to.operator_conf,self.operator_api.set_operators_config)
    
    def __config_handler__(self, config_data:str, func):
        '''
            支持两种格式
            json
            yaml
        '''
        if config_data == None or len(config_data) == 0:
            return
        if config_data[0] != '{'  and config_data[0] != '[':
                config_data =  yaml.load(config_data)
        if  hasattr(func , "__annotations__") and len(func.__annotations__) == 1:
                #config_data = reflection_object(  func,config_data)
                pass
        func(config_data)

    def __create_connect_and_source__(self):
        if self.operator_create_to.model_connect != None:
            mode_path = self.operator_service.atom_config.model_directory+self.operator_create_to.model_to.model_address
            folder = Path(mode_path)
            if folder.exists()  == False:
                connect:Connect = self.operator_service.connect_service.get_connect(self.operator_create_to.model_connect.connect_to)
                connect.download(self.operator_create_to.model_to.model_address,mode_path,None)

        for source_and_connect in self.operator_create_to.source_and_connects:
            source_to:SourceTo = source_and_connect.source_to
            source:Source = self.__create_source__(source_and_connect)
            if source_to.source_type == "source":
                self.operator_runtime.source = source
            elif source_to.source_type == "test_source":
                self.operator_runtime.test_source = source
            elif source_to.source_type == "init_data":
                self.operator_api.init_data = source
            else:
                self.operator_runtime.sink = source
        

    def __create_source__(self, source_and_connect:SourceAndConnect , isInit=True):
            connect:Connect = self.operator_service.connect_service.get_connect(source_and_connect.connect_to)
            source:Source = self.operator_service.source_service.get_source(source_and_connect.source_to,connect)
            source.source_to.source_conf["file_path"] = self.operator_service.atom_config.download_catalogue
            source.connect = connect

            if isInit :
                source.initialization()
            return source

    def __create_operator_runtime__(self):
        operator_runtime_class  = self.operator_service.operator_type_to_operator_runtime[self.operator_to.operator_runtime_type]
        self.operator_runtime:OperatorRuntime = operator_runtime_class()
        self.operator_runtime.operator_object = self.operator_api
        self.operator_runtime.rpc_operator_service = self.operator_service.rpc_operator_service
        self.operator_runtime.operator_to = self.operator_to
        

    
    def get_operator_runtime(self):
        return self.operator_runtime
