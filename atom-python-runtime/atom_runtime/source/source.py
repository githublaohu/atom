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
from urllib3.packages.six import BytesIO
from multiprocessing import Queue   

from atom_runtime.transfer_object.operator.source_to import SourceTo
from atom_runtime.connect.connnect import Connect,StreamAPI
from atom_runtime.data_set.convert.convert import Convert
from atom_runtime.data_set.convert.csv_convert import type_get_convert


class SourceStreamAPI(StreamAPI):

    convert:Convert
    labels: object
    func:object
    objects:object

    def batch(self,data):
        cvs_data = self.convert.converts(data)
        if self.convert.labels != None:
            self.labels(self.convert.labels)
        self.func(cvs_data)
        

    def stream(self ,data):
        cvs_data = self.convert.converts(data)

        self.datas.appand(cvs_data)
        self.count = self.count+len(data)
        if self.count  >= 5000:
            self.func(self.datas)
            self.datas = []
            self.count = 0
    
    def close(self):
            pass



class SourceBase():
    source_to:SourceTo
    connect :Connect
    queue:Queue()
    
    def initialization(self):
        pass

'''
读取模式
1. 下载模式[ 数据量计算 ]
    1. s3
    2. hdfs，支持断点读取
2. 读取模式
    1. 主动读取
        1. 关系型数据库
        2. hdfs
        3. hbase
        4. s3
        5. file
    2. 流读取
        1. RocketMQ
        2. Kafka
3. 数据格式
    1. 训练全是cvs
    2. 特征与数据算子
'''
class Source(SourceBase):
    # 表示数据已经读完
    connect_finish_reading : bool = False
    # 表达缓存数据已经拉去完
    operator_finish_data:bool = False
    # 从连接一次读取的数量
    connect_read_num: int = 0
    # 算子每次读取的数量
    operator_read_num: int = 0
    # 最后一个块的坐标
    initial_offset: int = 0
    # 读取数量的坐标
    offset_read:int = 0


    # 一次性拉去所有数据
    disposable:bool = True
    # 数据可读取次数
    fetch_num:int = 0
    data_length:int = 0


    def initialization(self):
        super().initialization()
        self.operator_read_num = self.source_to.operator_read_num
        self.connect_read_num = self.source_to.connect_read_num
        self.hash_next_value= True
        if self.source_to.disposable == False:
            self.connect_finish_reading = True
    
    def download(self,url,path):
        self.connect.download(url,path,self.source_to.source_conf)

    def run(self, operator_object , operator,labels):
        convert_class = type_get_convert(self.source_to.data_format)
        convert = convert_class()
        source_stream = SourceStreamAPI()
        source_stream.convert = convert
        source_stream.func = operator
        source_stream.labels = labels
        source_stream.objects = operator_object
        self.connect.stream(self.source_to.operate_execute,self.source_to.source_conf,source_stream)
    
    def train_upload(self, train):
        path = self.source_to.operate_execute + self.source_to.space_name + "-" +self.source_to.scene_name +"-" + self.source_to.experiment_name +"-"
        
        if isinstance(train,str):
             with open(train, 'rb') as f:
                train = f.read()
        self.connect.write(path , train)





class DataHandler():
    
    def handler(self , data):
        pass

class CSVFileHandler(DataHandler):

    def  handler(self , data):
        if isinstance(data,bytes):
                self.data = pickle.load(BytesIO(data))
        else:
                self.data = pickle.load(data)

CSV_file_handler = CSVFileHandler()
