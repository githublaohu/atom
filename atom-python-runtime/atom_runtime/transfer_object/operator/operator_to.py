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
from pydantic import BaseModel


class OperatorTo():
    operator_id:int= -1
    space_id:int= -1
    space_name:str = 'space1'
    scene_id:int= -1
    scene_name:str = 'scene1'
    experiment_id:int = -1
    experiment_name:str = 'experiment1'
    operator_template_id:int = -1
    operator_name:str = None
    operator_source_id:int = -1
    operator_source_type:str = -1
    operator_runtime_type:str = None
    operator_model:str = None

    level:int = -1
    resources_account_id:str = None
    code_mode:str = None
    code_address:str = None
    code_version:str = None
    module_name:str = None
    execute_object:str = None
    environment_conf:str = None
    operator_conf:str = None
    model_conf:str = None

    operator_epoch:int = 1
    operator_plan_runtimes:str = None
    operator_priority:str = None
    deploy_type:str = None

    # def __setattr__(self, key, value):
    #     super.__setattr__(self, key, value)

    # def __getattribute__(self, __name: str):
    #     return super().__getattribute__(__name)

    def __init__(self, data:dict):
        if len(data) == 0:
            return

        self.operator_id = data['id']
        self.space_id = data['space_id']
        self.operator_template_id = data['operator_template_id']
        self.operator_name = data['operator_name']
        self.operator_source_id = data['operator_source_id']
        self.operator_source_type = data['operator_source_type']
        self.operator_runtime_type = data['operator_runtime_type']
        self.operator_model = data['operator_model']

        self.level = data['level']
        self.resources_account_id = data['resources_account_id']
        self.code_mode = data['code_mode']
        self.code_address = data['code_address']
        self.code_version = data['code_version']
        self.module_name = data['module_name']
        self.execute_object = data['execute_object']
        self.environment_conf = data['environment_conf']
        self.operator_conf = data['operator_conf']
        self.model_conf = data['model_conf']

        self.operator_epoch = data['operator_epoch']
        self.operator_plan_runtimes = data['operator_plan_runtimes']
        self.operator_priority = data['operator_priority']
        self.deploy_type = data['deploy_type']
