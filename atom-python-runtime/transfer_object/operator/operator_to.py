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


class OperatorTo(BaseModel):
    operator_id:int= -1
    operator_templet_id:int = -1
    operator_name:str = None
    operator_source:str = None
    operator_source_id:int = -1
    operator_runtime_type:str = None
    operator_model:str = None
    space_id:int= -1
    space_name:str = None
    scene_id:int= -1
    scene_name:str = None
    experiment_id:int = -1
    experiment_name:str = None
    resources_account_id:str = None
    code_mode:str = None
    code_address:str = None
    code_version:str = None
    code_newest:bool = None
    module_name:str = None
    execute_object:str = None
    environment_conf:str = None
    operator_conf:str = None
    model_conf:str = None
    operator_epoch:int = 1
    operator_plan_runtimes:str = None
    operator_status:str
    operator_priority:str = None
    deploy_type:str = None

