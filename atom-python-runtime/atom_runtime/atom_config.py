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
import json
import yaml
import nacos
import logging
from atom_runtime.utils.environment import atrom_catalogue,get_env

class AtomConfig():
    # 是否是docker模式
    docker_model:bool = False
    runtime_model:str = "standalone"
    node_ip:str = "127.0.0.1"

    # nacos配置
    nacos_address:str
    nacos_namespace:str
    # 
    config_name:str
    rpc_controller_port:int = None
    is_config:bool
    # 文件下载地址
    download_catalogue:str = None
    # git代码下载地址
    code_directory:str  = None
    # 模型下载地址
    model_directory:str  =None
    # 日志名
    logging_file_directory_and_name:str = "./atom-python-runtime.log"

    def is_local(self):
        return  self.runtime_model == "local"


class  AtomConfigServier():
    atom_config:AtomConfig = AtomConfig()
    def __init__(self) :
        self.__atrom_catalogue = atrom_catalogue
        docker = get_env("docker")
        if docker == None:
            self.__load_atom_config__()
            self.__check_()
        else:
            self.atom_config.docker_model = True
            self.__docker_model()
    
    def __docker_model(self):
            node_ip = get_env("node_ip")
            self.__atrom_catalogue = "~/atom"
            if node_ip  != None:
                self.atom_config.node_ip = node_ip
            runtime_model = get_env("runtime_model")
            if runtime_model != None:
                self.atom_config.runtime_model = runtime_model
            nacos_config = get_env("nacos_config")
            if nacos_config == None:
                return
            nacos_config = json.decoder(nacos_config)
            self.nacso_client = nacos.NacosClient(nacos_config.get("nacos_address"), namespace=nacos_config.get("nacos_namespace"))
            self.nacso_client.get_config(nacos_config.get("config_name"),None)


    def __load_atom_config__(self):
        """
            启动方式
            1. main.py启动，主动读取atom_config.yml
            2. 命令启动
                1. 可以配置nacos启动相关命令
                2. 可以直接传递相关配置
                3. 关注默认目录
            3. 容器启动
                1. 直接 把nacos配置写入环境变量
                2. 需要抢端口
            
        """
        # 读取当前目录配置文件
        atrom_config_file =  os.getcwd() +"/atom_config.yml"
        if os.path.isfile(atrom_config_file)  == False:
            logging.info("读取开发目录配置文件失败")
            atrom_config_file = "~/atom/atom_config.yml"
        else:
             logging.info("读取默认目录配置文件成功")
        if os.path.isfile(atrom_config_file)  == False:
            logging.info("配置文件读取失败，退出运行")
            print("配置文件读取失败，退出运行")
            exit()
        else:
            self.__atrom_catalogue = "~/atom"
            logging.info("读取默认目录下配置文件成功")
        atom_config_json = yaml.load(open(atrom_config_file, "r"), Loader=yaml.SafeLoader)
        logging.info("配置内容%s", atom_config_json)
        self.atom_config.nacos_address = atom_config_json["nacos_address"]
        self.atom_config.nacos_namespace = atom_config_json["nacos_namespace"]
        self.atom_config.rpc_controller_port = atom_config_json["rpc_controller_port"]
        
    
    def __check_(self):
        if self.atom_config.rpc_controller_port == None:
                self.atom_config.rpc_controller_port = 9999
        if self.atom_config.download_catalogue == None:
            self.atom_config.download_catalogue = self.__atrom_catalogue +"/download_catalogue/"

        if self.atom_config.code_directory == None:
            self.atom_config.code_directory = self.__atrom_catalogue +"/code_directory/"
            
        if self.atom_config.model_directory == None:
            self.atom_config.model_directory = self.__atrom_catalogue +"/model_directory/"

    def get_atrom_config(self):
        return self.atom_config