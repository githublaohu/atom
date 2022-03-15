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
FROM nvidia/cuda:10.0-cudnn7-devel-ubuntu18.04

RUN pip3 install atom \
  &&  pip3 install tensorflow-gpu==1.14 -i https://mirrors.aliyun.com/pypi/simple/  \
  &&  pip3 install sklearn -i https://mirrors.aliyun.com/pypi/simple/

COPY  /atom /atom
CMD atom 