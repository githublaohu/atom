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

from atom_runtime.service.atom_service import AtomService
from flask import Flask,request


class RuntimeController():

        atom_service:AtomService

        def __init__(self,app:Flask):
            app.add_url_rule('/runtime/closeRuntime', "/runtime/closeRuntime",self.closeRuntime,methods=["POST","GET"])

        def finish(self):
            pass

        def closeRuntime(self):
            self.atom_service.closeRuntime();
