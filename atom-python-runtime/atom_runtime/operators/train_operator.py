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
from atom_runtime.atom_runtime_api.operator.train_api import TrainOperatorApi
from atom_runtime.operators.operator import OperatorRuntime
from atom_runtime.source.source import Source


class TrainOperatorRuntime(OperatorRuntime):
    """
    训练加速
        1. 分布式训练  
            horovod
        2. 混合精度训练
    模型压缩与加速
        1. 量化
        2. 剪枝
        3. 知识蒸馏
    
    ARM ，边缘计算  https://www.zhihu.com/question/20031861/answer/996675177
    CPU/GPU/NPU/TPU 异构，不就是部署吗？


    """
    def run(self):
        train_operator:TrainOperatorApi = self.operator_object
        self.train(self.source ,train_operator.execute)
       # 无此属性
        if self.test_source != None:
            self.train(self.source , train_operator.comparision_execute)         
        # 得到训练结果
        train_result = train_operator.result()
        # 保存模型 path= /{root}/{模型名}/{毫秒数}.
        self.sink.train_upload(train_result)
        


    def train(self ,source:Source , func ):
        source.run(self.operator_object,func,self.set_operator_object)

    def  set_operator_object(self , labels):
        self.operator_object.labels = labels