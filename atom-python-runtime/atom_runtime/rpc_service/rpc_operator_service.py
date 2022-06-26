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


from atom_runtime.rpc_service.rpc_service import RpcService
from atom_runtime.service.atom_service import AtomService
from atom_runtime.transfer_object.operator.operator_create_to import OperatorCreateTo, SourceAndConnect
from atom_runtime.transfer_object.operator.connect_to import ConnectTo
from atom_runtime.transfer_object.operator.operator_to import OperatorTo
from atom_runtime.transfer_object.operator.source_to import SourceTo

class RpcOperatorServcie(RpcService):
    
    atom_service:AtomService

    def create_operators(self, operator_create_to:OperatorTo):
        pass

    def start_operators(self, operator_to:OperatorTo):
        pass

    def suspend_operators(self, operator_to:OperatorTo):
        pass

    def uninstall_operators(self, operator_to:OperatorTo):
        self.atom_service.uninstall_operators(operator_to);
    
    def abnormal_operators(self ,operator_to:OperatorTo ):
        self.atom_service.abnormal_operators(operator_to);

    def complete_operators(self , operator_to:OperatorTo):
        pass

    def get_all_operators(self, runtime_info):
        """
         1.  主要用于启动的时候，读取所有的需要运行的推理算子
        """
        pass

    def query_operator_by_id(self , operator_id):
        if operator_id == "-1":
            return self.create_train_operator()
        elif operator_id == "-2":
            return self.create_reasoning_operator()



    def create_reasoning_operator(self):
        pass

    def  create_train_operator(self):
        operator_create_to = OperatorCreateTo({})
        operator_to = OperatorTo({})
        operator_create_to.operator_to = operator_to
        operator_to.operator_runtime_type = "TRAIN"
        operator_to.module_name="atom_runtime.example.train_example"
        operator_to.execute_object="TrainExample"
        operator_to.environment_conf="{}"
        operator_to.operator_conf="{}"
        operator_to.model_conf = "{}"
        operator_to.operator_epoch = 2

        source_and_connects = []
        operator_create_to.source_and_connects = source_and_connects
        source_and_connect = SourceAndConnect()
        source_and_connects.append(source_and_connect)
        source_to = SourceTo({})
        source_and_connect.source_to = source_to
        source_to.source_type = "source"
        source_to.operate_execute = "./atom_runtime/example/train.pkl"
        source_to.data_format = "labelsCSV"
        connect_to = ConnectTo({})
        source_and_connect.connect_to = connect_to
        connect_to.connect_id = -1
        connect_to.connect_type = "FILE"

        source_and_connect = SourceAndConnect()
        source_and_connects.append(source_and_connect)
        source_to = SourceTo({})
        source_and_connect.source_to = source_to
        source_to.data_format = "labelsCSV"
        source_to.source_type = "test_source"
        source_to.operate_execute = "./atom_runtime/example/val.pkl"
        connect_to = ConnectTo({})
        source_and_connect.connect_to = connect_to
        connect_to.connect_id = -1
        connect_to.connect_type = "FILE"

        source_and_connect = SourceAndConnect()
        source_and_connects.append(source_and_connect)
        source_to = SourceTo({})
        source_to.space_name = "space"
        source_to.scene_name = "scene"
        source_to.experiment_name = "experiment"
        source_and_connect.source_to = source_to
        source_to.source_type = "sink"
        source_to.operate_execute = "./atom_runtime/example/"
        connect_to = ConnectTo({})
        source_and_connect.connect_to = connect_to
        connect_to.connect_id = -1
        connect_to.connect_type = "FILE"

        return operator_create_to
