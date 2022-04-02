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
import sys

import logging
from pip._internal import main
from pathlib import Path

from atom_runtime.transfer_object.operator.operator_to import OperatorTo
from atom_runtime.transfer_object.operator.source_account_to import SourceAccountTo

class CodeLoad():
    
    code_directory:str = None
    code_file_name:str = None
    code_load_directory:str = None
    operator_to:OperatorTo= None
    source_account_to:SourceAccountTo= None

    def _load_code(self):
        pass

    def __requirements_loading__(self):
        if self.code_file_name == None:
            return
        requirements = self.code_directory+"/"+self.code_file_name+"/requirements.txt"
        folder = Path(requirements)
        if folder.exists() == False:
            return
        args = ["install","-r",requirements]
        try:
            main.main(args)
        except SystemExit as e:
            # 如果异常 main放里面会执行sys.exit(1)，必须except  SystemExit
            logging.error(e)

    def __load__object__(self):
        self.import_object=__import__(self.operator_to.module_name,globals(),locals(),["*"],0)
        self.executeObject = self.import_object.__dict__[self.operator_to.execute_object]
        self.object = self.executeObject
        

    def get_object(self):
        self._load_code()
        self.__requirements_loading__()
        if self.code_load_directory != None:
            if  sys.path.count(self.code_load_directory) == 0:
                # 把项目加入python引用路径
                sys.path.append(self.code_load_directory)
        self.__load__object__()
        return self.object