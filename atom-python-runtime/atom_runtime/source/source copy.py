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

from data_set.convert.csv_convert import CSVConvert
from transfer_object.operator.source_to import SourceTo
from connect.connnect import Connect,StreamAPI
from multiprocessing import Queue   
import pickle
from urllib3.packages.six import BytesIO
from data_set.convert.convert import Convert
from data_set.convert.csv_convert import type_get_convert


class SourceStreamAPI(StreamAPI):

    convert:Convert
    labels: object
    func:object

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

        self.__write_data_()
    
    def download(self,url,path):
        self.connect.download(url,path,None)

    def __write_data_(self):
        if self.source_to.connect_read_num == -1 :
            self.data = self.connect.read_whole(self.source_to.operate_execute,self.source_to.source_conf )
            self.data = CSVConvert().convert(self.data)
            self.connect_finish_reading = True
        else:
            self.data = self.connect.read(self.source_to.operate_execute,self.source_to.source_conf , self.connect_read_num,self.source_to.connect_read_num)
            self.connect_read_num = self.connect_read_num + self.source_to.connect_read_num
            if len(self.data) < self.source_to.connect_read_num:
                self.connect_finish_reading = True


    def hash_next(self):
        return self.connect_finish_reading
    
    def  next(self):
        if self.operator_read_num == -1:
            self._not_next()
            return self.data
        
        batch = self.data[self.offset_read:(self.offset_read + self.operator_read_num)]
        self.offset_read = self.offset_read+self.operator_read_num
        if self.fetch_num >= self.data_length:
            self._not_next()
        return batch

    def run(self, operator,labels):
        convert_class = type_get_convert(self.source_to.data_format)
        convert = convert_class()
        source_stream = SourceStreamAPI()
        source_stream.convert = convert
        source_stream.func = operator
        source_stream.labels = labels
        self.connect.stream(self.source_to.operate_execute,self.source_to.source_conf,source_stream)


    def _not_next(self):
        if self.connect_finish_reading == False:
            if self.source_to.async_load:
                # 查看一个状态
                pass
            else:
                # 调动连接查询
                # 如果返回是空
                pass
        self.operator_finish_data = True


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
