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

import traceback
import logging
from atom_runtime.atom_runtime_api.operator.operators_api import OperatorApi
from atom_runtime.rpc_service.rpc_operator_service import RpcOperatorServcie
from atom_runtime.source.source import  Source
from atom_runtime.source.sink import Sink
from atom_runtime.transfer_object.operator.operator_to import OperatorTo


class OperatorRuntime():
    
    rpc_operator_service:RpcOperatorServcie
    operator_to:OperatorTo
    operator_object:OperatorApi
    source:Source = None
    test_source:Source = None
    sink:Source = None
    def set_source(self , source:Source):

        self.source = source

    def set_test_source(self , test_source:Source):
        self.test_source = test_source

    def set_sink(self , sink:Sink):
        self.sink = sink
        
    def set_operator_object(self,operator_object):
        self.operator_object = operator_object

    def do_run(self):
        try:
            self.rpc_operator_service.start_operators(self.operator_to)
            self.run()
            self.rpc_operator_service.complete_operators(self.operator_to)
        except Exception as e:
            logging.exception(e)
            try:
                self.rpc_operator_service.abnormal_operators(self.operator_to)
            except Exception as ee:
                logging.exception(e)

    def run(self):
        pass
    def _out_source(self):
        pass