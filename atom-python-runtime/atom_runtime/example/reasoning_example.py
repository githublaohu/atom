import pandas as pd
from atom_runtime.atom_runtime_api.operator.reasoning_api import ReasoningOperatorApi
import tensorflow as tf
import numpy as np
from urllib3.packages.six import BytesIO

from tensorflow.keras.models import load_model
from tensorflow.python.keras.preprocessing.sequence import pad_sequences

from deepctr.layers import custom_objects



def get_feature_name_list(feature):
    feature_name_list = []
    feature_name_list.extend(feature.get('num_cols', []))
    feature_name_list.extend(feature.get('cate_cols', []))
    feature_name_list.extend(feature.get('multicate_cols', dict()).keys())
    return feature_name_list

def deepctr_feature_encode(data: pd.DataFrame, cols_type: dict, enc_dict: dict):
    data_result_dict = {}

    def _mutilcat_encode(x, col_enc_dict):
        key_ans = x.split("|")
        return [col_enc_dict.get([i], col_enc_dict['isnan']) for i in key_ans]

    def _cat_encode(x, col_enc_dict):
        return col_enc_dict.get(x, col_enc_dict['isnan'])

    def _normalizer(x, col_enc_dict):
        if x > col_enc_dict['max']:
            return 1
        elif x < col_enc_dict['min']:
            return 0
        else:
            return (x - col_enc_dict['min'])/(col_enc_dict['max'] - col_enc_dict['min'])

    for col in data.columns:
        if col in cols_type['num_cols']:
            col_data = data[col].astype(float)
            # col_data = col_data.map(lambda x: _normalizer(x, enc_dict[col]))
            data_result_dict[col] = col_data
        elif col in cols_type['cate_cols']:
            col_data = data[col].astype(str)
            col_data = col_data.map(lambda x: _cat_encode(x, enc_dict[col]))  # 把里面的值用编码代替
            data_result_dict[col] = col_data
        elif col in cols_type['multicate_cols'].keys():
            col_data = list(map(lambda x: _mutilcat_encode(x, enc_dict[col]), data[col].values))
            col_data = pad_sequences(col_data, maxlen=cols_type['multicate_cols'][col], padding='post', )
            data_result_dict[col] = col_data
        else:
            continue

    if 'label' in data.columns:
        label = data['label'].tolist()
        data_result_dict['label'] = label
    return data_result_dict

def deepctr_get_cols_order(config):
    """
    获取请求的输入
    """
    item_feature = config['item_feature']
    user_feature = config['user_feature']
    user_feature_name = get_feature_name_list(user_feature)
    item_feature_name = get_feature_name_list(item_feature)
    cols_order = user_feature_name + item_feature_name + ['label']
    return cols_order


def deepctr_get_cols_type(config):
    item_feature = config['item_feature']
    user_feature = config['user_feature']
    num_cols = item_feature.get('num_cols', []) + user_feature.get('num_cols', [])
    cate_cols = item_feature.get('cate_cols', []) + user_feature.get('cate_cols', [])
    multicate_cols = {**item_feature.get('multicate_cols', dict()), **user_feature.get('multicate_cols', dict())}
    cols_type = {'num_cols': num_cols, 'cate_cols': cate_cols, 'multicate_cols': multicate_cols}
    return cols_type

class ReasoningExample(ReasoningOperatorApi):
    
    def initialization(self):  
        """
            主要流程调用完成，调用本办法
        """
        self.cols_order = deepctr_get_cols_order(self.inference_config)[:-1]
        self.cols_type = deepctr_get_cols_type(self.inference_config)
        self.session = tf.Session()
        self.graph = tf.get_default_graph()
        with self.graph.as_default():
            with self.session.as_default():
                path = self.get_mode_path()
                self.model = load_model(path, custom_objects)
    
    
    def __encode(self, request_info):
        data = pd.DataFrame(request_info)
        data.columns = self.cols_order
        input_data = deepctr_feature_encode(data, self.cols_type, self.dict_data)
        return input_data

    def predict(self, request_info):
        request_info = self.__encode(request_info)
        with self.graph.as_default():
            with self.session.as_default():
                predictions = self.model.predict(request_info)
        return  np.squeeze(predictions).tolist()



