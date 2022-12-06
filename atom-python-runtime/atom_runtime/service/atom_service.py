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

import os

from atom_runtime.atom_config import  AtomConfig
from atom_runtime.transfer_object.operator.operator_to import OperatorTo


class AtomService():

    atom_config:AtomConfig ;

    def __init__(self,atom_config:AtomConfig):
        self.atom_config = atom_config;

    def create_operators(self, operator_create_to:OperatorTo):
        pass

    def start_operators(self, operator_to:OperatorTo):
        pass

    def suspend_operators(self, operator_to:OperatorTo):
        pass

    def uninstall_operators(self, operator_to:OperatorTo):
        if self.atom_config.is_session():
            os._exit(0)
    
    def abnormal_operators(self ,operator_to:OperatorTo ):
        if self.atom_config.is_session():
           os._exit(0)
    
    def closeRuntime(self):
        os._exit(0)
            
    

    def complete_operators(self , operator_to:OperatorTo):
        pass