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
from service.register_service import RegisterService


import http.client
import json

class HttpClient():
    register_servcie:RegisterService
    

    def send(url:str , data:map):
        connection = http.client.HTTPSConnection('api.github.com')
        headers = {'Content-type': 'application/json'}
        json_foo = json.dumps(data)
        connection.request('POST', url, json_foo, headers)
        response = connection.getresponse()
        result = json.loads(response.read().decode())
        return result

class RpcServcieServcie():
    register_servcie:RegisterService
    http_client:HttpClient
    
    def __init__(self):
        self.http_client = HttpClient()
    
    def get_http_client(self) -> HttpClient:
        return self.http_client


