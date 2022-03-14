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
import pickle
import json
from atom_runtime.data_set.convert.convert import Convert
from urllib3.packages.six import BytesIO

class CSVConvert(Convert):
    
    def  convert(self , data):
        if self.convert_mapper == None:
            return self.__complete_csv_format__(data)
        elif self.convert_mapper.field == None or self.convert_mapper.mapper_field != None:
            result = self.__complete_csv_format__(data)
        
    
    def __complete_csv_format__(self , data):
        if isinstance(data,bytes):
            return pickle.load(BytesIO(data))
        else:
            return pickle.load(data)

class LabelsCSVConvert(Convert):
    
    def  converts(self , data):
        if isinstance(data,bytes):
            cvs_data = pickle.load(BytesIO(data))
        else:
            cvs_data = pickle.load(data)
        datas, labels = self.split_sample(cvs_data)
        self.labels = labels
        return datas
    
    def split_sample(self, li):
        datas, labels = [], []
        for data, label in li:
            datas.append(data)
            labels.append(label)
        return datas, labels
        
    
    def __complete_csv_format__(self , data):
        if isinstance(data,bytes):
            return pickle.load(BytesIO(data))
        else:
            return pickle.load(data)

class StringCVSToCVSConvert(Convert):

    def  convert(self , data):
        list = data.split(" ")
        return list

class JsonToCVSConvert(Convert):
    
    def  convert(self , data):
        list_data = []
        for mapper in self.convert_mapper.mapper_field:
            field_value = data.get(mapper.field_name)
            list_data.append(field_value)
        return list_data

class JsonStringToCVSConvert(JsonToCVSConvert):

    def  convert(self , data):
        dict_data = json.loads(data)
        return super().convert(dict_data)


type_convert = {}
type_convert["labelsCSV"] = LabelsCSVConvert
type_convert["stringCVS"] = StringCVSToCVSConvert
type_convert["Json"] = JsonToCVSConvert
type_convert["JsonString"] = JsonStringToCVSConvert

def  type_get_convert(type):
    return type_convert[type]