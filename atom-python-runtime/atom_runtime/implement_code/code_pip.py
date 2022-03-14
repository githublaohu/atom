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
from pip._internal import main
import logging
import subprocess
import platform

def __init__pip__():
    # 调用命令pip 其实不用判断pip,如果不存在，那么runtime无法安装
    ret = subprocess.run("which pip",shell=True,stdout=subprocess.PIPE,stderr=subprocess.PIPE,encoding="utf-8",timeout=1)
    # 判断pip是否存在
    if ret.returncode == 1:
        # 判断操作系统类型
        value = platform.linux_distribution()
        #  ubuntu agt，centos yum，window   
        if value=="debian" :
            install_pip="yum install -y pip"
        else :
            install_pip="apt-get install -y pip"
             # 下载pip
            subprocess.run(install_pip,shell=True,stdout=subprocess.PIPE,stderr=subprocess.PIPE,encoding="utf-8",timeout=1)

__init__pip__()