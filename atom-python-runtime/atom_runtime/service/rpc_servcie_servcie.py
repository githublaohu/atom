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
import random
import logging
import traceback
from atom_runtime.service.register_service import RegisterService
 


import http.client
import json

class HttpClient():
    register_servcie:RegisterService
    

    def send(self,url:str , data:map):
        for i in range(0,5):
            instance = self.getAddress()
            try:
                connection = http.client.HTTPConnection(host= instance["ip"], port =instance["port"])
                headers = {'Content-type': 'application/json'}
                json_foo = json.dumps(data)
                connection.request('POST', url, json_foo, headers)
                response = connection.getresponse()
                result = json.loads(response.read().decode())
                return result
            except BaseException as e:
                s = traceback.format_exc()
                logging.error(s)
                logging.exception("请求异常 {}".format(e))
    
    def getAddress(self):
        instances = self.register_servcie.get_instance()
        index = random.randint(0, len(instances) - 1);
        return instances[index]
class RpcServcieServcie():
    register_servcie:RegisterService
    http_client:HttpClient
    
    def __init__(self,register_servcie:RegisterService):
        self.http_client = HttpClient()
        self.http_client.register_servcie = register_servcie;
    
    def get_http_client(self) -> HttpClient:
        return self.http_client


