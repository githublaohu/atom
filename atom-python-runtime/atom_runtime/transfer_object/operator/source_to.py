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
    space_id:int= -1
    space_name:str = None
    scene_id:int= -1
    scene_name:str = None
    experiment_id:int = -1
    experiment_name:str = None
    connect_id:int
    connect_name:int
    source_type:str
    source_space:str
    source_conf:map = {}
    data_format:str
    # 从连接中一次读取数据量
    connect_read_num:int = -1
    # 算子从source 一次读取数量
    operator_read_num:int = -1
    # source从connect是否一次行读取
    disposable:bool = False
    # 是否异步从connect读取数据
    async_load:bool
    # 排序
    order:int
    # 
    task_init_execute:str
    operate_exectute_before:str
    operate_exectute:str
    operate_exectute_after:str
    reload_file:bool = True

    
    