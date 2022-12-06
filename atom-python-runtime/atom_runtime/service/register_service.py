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
import nacos
import socket
import fcntl
import struct
from threading import Timer

from atom_runtime.utils.environment  import get_env
from atom_runtime.atom_config import AtomConfig
           
class RegisterService():
    """
        注册operator
        1. 通过rpc_service。 最大的问题是runtime挂了，怎么处理
        2. 通过register_servcie
    """

    net_address: str
    atom_config:AtomConfig
    nacos_client:nacos.NacosClient
    service_instances:list
    metadata:dict = {}

    def __init__(self, atom_config:AtomConfig) :
        self.atom_config = atom_config
        if  atom_config.is_local():
            return
        if self.atom_config.test_model:
            return
        self.metadata["podId"]= get_env("pod_id");
        self.metadata["nodeInfo"]= get_env("nodeInfo");

        self.__get_net_address__()

        self.nacos_client = nacos.NacosClient(server_addresses=atom_config.nacos_address, namespace=atom_config.nacos_namespace)
        self.nacos_client.add_naming_instance("atom-runtime-python-service-"+self.atom_config.runtime_model,self.net_address, self.atom_config.rpc_controller_port)
        
        self.__register_and_get_instance__()
        

    def __get_net_address__(self):
        if self.atom_config.node_ip != None:
            self.net_address = self.atom_config.node_ip
            return

        """
        实现的是方案一  
            这种方案是完全可靠
        
        重点：
        可以指定获得网卡的网络地址
        __get_ip_address__
        """
        fqdn = socket.getfqdn(socket.gethostname(  ))
        self.net_address = socket.gethostbyname(fqdn)

    def __get_ip_address__(ifname):
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        return socket.inet_ntoa(fcntl.ioctl(
            s.fileno(),
            0x8915,  # SIOCGIFADDR
            struct.pack('256s', ifname[:15])
        )[20:24])

    def __register_and_get_instance__(self):
        """
            区别不同类型的runtime(训练，)
            那么服务名是否可变
            如果不可变是否加入groupid 用于区别
        """

        # 注册服务
        self.nacos_client.send_heartbeat("atom-runtime-python-service-"+self.atom_config.runtime_model,
        self.net_address, self.atom_config.rpc_controller_port);
        
        # 获取算子服务实例
        result = self.nacos_client.list_naming_instance("atom-service-operator", healthy_only=True)
        
        self.service_instances = result["hosts"]
        self.__heartbeat__()

    def __heartbeat__(self):
        t = Timer(5, self.__register_and_get_instance__)
        t.start()
    
    def  get_instance(self):
        return self.service_instances