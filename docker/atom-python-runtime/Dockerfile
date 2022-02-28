#Copyright (c) [Year] [name of copyright holder]
#[Software Name] is licensed under Mulan PSL v2.
#You can use this software according to the terms and conditions of the Mulan PSL v2.
#You may obtain a copy of Mulan PSL v2 at:
#         http://license.coscl.org.cn/MulanPSL2
#THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
#EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
#MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
#See the Mulan PSL v2 for more details.

######################################################################
# PY stage that simply does a pip install on our requirements
######################################################################
ARG PY_VER=3.8.12
FROM python:${PY_VER}

RUN apt-get install -y git\
    && pip install atom  \
    &&  pip3 install sklearn -i https://mirrors.aliyun.com/pypi/simple/
    && mkdir -p ~/atom/{download_catalogue,code_directory,model_directory,public}

CMD atom 