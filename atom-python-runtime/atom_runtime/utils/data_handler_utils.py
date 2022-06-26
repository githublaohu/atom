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
import re

class DataHandler():
 
    # 驼峰命名法改为下划线命名法，只对dict或list的key做更改
    def humpToUnderline(self, data):
        if type(data) == dict:
            res:dict = {}
            for i in data:
                key = re.sub('(?<=[a-z])[A-Z]|(?<!^)[A-Z](?=[a-z])', '_\g<0>', i).lower()
                if type(data[i]) == dict or type(data[i]) == list:
                    res[key] = self.humpToUnderline(data[i])
                else:
                    res[key] = data[i]
            return res
        elif type(data) == list:
            res:list = list()
            for i in data:
                res.append(self.humpToUnderline(i))
            return res
        else:
            return data
        