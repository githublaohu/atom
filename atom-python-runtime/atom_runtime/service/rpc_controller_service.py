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
from flask import Flask
from atom_runtime.utils.environment import name_atom


from atom_runtime.atom_config import AtomConfig


class RpcControllerServcie():
    atom_config:AtomConfig

    def __init__(self, atom_config:AtomConfig,app:Flask):
        self.atom_config = atom_config
        self.app = app
    
    def __init_http_service__(self):
        pass 

    def register_controller(self , controller):

        pass

    def run(self):
        self.app.run(host="0.0.0.0", port=self.atom_config.rpc_controller_port)