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


class SourceTo():
    # 数据源ID
    source_id:int = -1
    # 空间ID
    space_id:int= -1

    space_name:str = 'space1'
    scene_id:int= -1
    scene_name:str = 'scene1'
    experiment_id:int = -1
    experiment_name:str = 'experiment1'
    # 连接ID
    connect_id:int
    # 连接名
    connect_name:int
    # 数据源类型
    source_type:str
    # 数据源空间
    source_space:str
    # 数据源配置
    source_conf:map = {}

    # 初始化
    task_init_execute:str
    # 任务开始
    operate_execute_before:str
    # 数据执行之前
    data_execute_before:str
    # 任务执行
    operate_execute:str
    # 数据执行之后
    data_execute_after:str
    # 任务结束
    operate_execute_after:str

    # 数据格式
    data_format:str
    # 算子从source 一次读取数量
    operator_read_num:int = -1
    # 从连接中一次读取数据量
    connect_read_num:int = -1
    # 每次分页读取的数据量
    paginate_read_num:int = -1
    # source从connect是否一次行读取
    disposable:bool = False
    # 是否异步从connect读取数据
    async_load:bool
    # 排序
    order:int
    
    reload_file:bool = True

    def __init__(self, data:dict):
        if len(data) == 0:
            return
        
        self.source_id = data['id']
        self.space_id = data['space_id']
        self.connect_id = data['connection_id']
        self.connect_name = data['connection_name']
        self.source_type = data['source_type']
        self.source_space = data['source_space']
        self.source_conf = data['source_conf']#todo json转map

        self.task_init_execute = data['task_init_execute']
        self.operate_execute_before = data['operate_execute_before']
        self.data_execute_before = data['data_execute_before']
        self.operate_execute = data['operate_execute']
        self.data_execute_after = data['data_execute_after']
        self.operate_execute_after = data['operate_execute_after']

        self.data_format = data['data_format']
        self.operator_read_num = data['operator_read_num']
        self.connect_read_num = data['connect_read_num']
        self.paginate_read_num = data['paginate_read_num']
        self.disposable = data['disposable']
        self.async_load = data['async_load']
        self.order = data['order']

    
    