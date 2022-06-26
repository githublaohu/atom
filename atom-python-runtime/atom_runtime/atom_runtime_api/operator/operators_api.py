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
from atom_runtime.atom_runtime_api.operator.model_info import ModelInfo

class OperatorApi():
    operators_config:object
    model_config:object
    init_data: object
    model: ModelInfo


    def set_mode_config(self,mode_config):
        self.mode_config = mode_config
    
    def set_operators_config(self,operators_config):
        self.operators_config = operators_config

    def set_init_data(self, init_data):
        self.init_data = init_data
    
    def set_model(self, model):
        self.model = model
    
    def initialization(self):
        pass
    
    def do_initialization(self):
        self.initialization()