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
import os
import subprocess
import platform
from pathlib import Path
from git import Repo
from atom_runtime.implement_code.code_load import CodeLoad

def __init__git__():
     # 调用命令pip 其实不用判断pip,如果不存在，那么runtime无法安装
    ret = subprocess.run("which git",shell=True,stdout=subprocess.PIPE,stderr=subprocess.PIPE,encoding="utf-8",timeout=1)
    # 判断pip是否存在
    if ret.returncode != 1:
        return
    # 判断操作系统类型
    value = platform.linux_distribution()
    #  ubuntu agt，centos yum，window   
    if value=="debian" :
        install_pip="yum install -y git"
    else :
        install_pip="apt-get install -y git"
            # 下载pip
    subprocess.run(install_pip,shell=True,stdout=subprocess.PIPE,stderr=subprocess.PIPE,encoding="utf-8",timeout=1)   


__init__git__()

rely_info_dict = {}


class GitCodeLoad(CodeLoad):


    def _load_code(self):
        # 需要判断本地是否存放存在
        # 创建邮箱
        # 创建用户
        # https://gitpython.readthedocs.io
        # https://username:password@github.com/username/private-projec.git
        # branch ='master'
        url = self.operator_to.code_address
        if url.rfind(".git") == -1 :
            pass
        self.__get_url__()
        self.__get_code_directory__()
        folder = Path(self.code_load_directory)
        if folder.exists() :
            # 如果存在怎么做？
            # 删除？
            # 如果需要更新代码怎么办?
            # 如果需要共存怎么办?
            #          共存出现路径冲突怎么办?
            #          如何识别共存
            #          如何识别已经共存
                        # 切换分之
            self.clone =Repo( self.code_load_directory )
        else:
            self.clone = Repo.clone_from(self.code_path,self.code_load_directory)
        self.__check_branck__()
            

    def __check_branck__(self):
        self.clone.remote().pull()
        if self.operator_to.code_version != None:
            before = self.clone.git.branch("-r")
            branch = self.operator_to.code_version
            if before.find(branch) == -1:
                    # 分支不存在
                    return
            self.clone.git.checkout(branch)

    def __get_url__(self):
        newurl = self.operator_to.code_address
        if self.source_account_to.account != None:
            newurl = "https://"+self.source_account_to.account+":"+self.source_account_to.password+"@"+newurl[8:len(newurl)]
        self.code_path = newurl
    
    def __get_code_directory__(self):
        self.code_file_name = self.code_path[self.code_path.rfind("/"):len(self.code_path)-4]
        self.code_load_directory =  self.code_directory+self.code_file_name
        

    def clear(self):
        if self.code_load_directory == None:
            return
        sys.path.remove(self.code_load_directory)
        os.rmdir(self.code_load_directory)
        