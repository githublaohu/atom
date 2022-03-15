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
from atom_runtime.atom_config import AtomConfig
from atom_runtime.implement_code.code_load import CodeLoad
from atom_runtime.implement_code.code_git import GitCodeLoad
from atom_runtime.transfer_object.operator.operator_to import OperatorTo
from atom_runtime.transfer_object.operator.source_account_to import SourceAccountTo


class CodeService():
    
    atom_config:AtomConfig

    operator_id_to_path:map = {}

    code_type_model:map = {}

    def __init__(self, atom_config:AtomConfig):
        self.atom_config = atom_config
        self.code_type_model["git"]=  GitCodeLoad
        
    
    def get_object(self, operator_to:OperatorTo,source_account:SourceAccountTo):
        code_load_class = self.code_type_model.get(operator_to.code_mode)
        if code_load_class == None:
            code_load_class = CodeLoad
        code_load:CodeLoad = code_load_class()
        code_load.operator_to = operator_to
        code_load.source_account_to = source_account
        code_load.code_directory = self.atom_config.code_directory
        return code_load.get_object()

