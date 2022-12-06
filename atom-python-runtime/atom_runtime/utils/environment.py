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

try:
    import pynvml
    pynvml.nvmlInit()  # 必须先初始化
    #1.获取显卡驱动版本
    Driver_Version = pynvml.nvmlSystemGetDriverVersion()
    #2.获取gpu数量
    deviceCount =pynvml.nvmlDeviceGetCount()
    #3.获取第i个显卡的具体性信息:型号
    handle = pynvml.nvmlDeviceGetHandleByIndex(1)
    #4.获取第i个显卡的显存大小,已使用,剩余
    meminfo = pynvml.nvmlDeviceGetMemoryInfo(handle)
    print(meminfo.total) #第二块显卡总的显存大小
    print(meminfo.used)#这里是字节bytes，所以要想得到以兆M为单位就需要除以1024**2
    print(meminfo.free) #第二块显卡剩余显存大小
except Exception as e:
   #traceback.print_exc()
    #logging.error(e)
    print("pynvml 初始化失败")


name_atom = "atom"

def check_os_rely():
    pass

atrom_catalogue = os.environ['HOME']+"/atom"

def get_env(envName):
    argvName= envName+"="
    envValue = None
    for argValue in  sys.argv:
        if argValue.startswith(argvName) != False :
          envValue = argValue[len(argvName):len(argValue)]
    if envValue == None :
        envValue = os.getenv(envName)
    return envValue

def get_env_default(envName , defaultValue):
    envValue = get_env(envName)
    if envValue == None:
        return defaultValue
    else:
        return envValue

