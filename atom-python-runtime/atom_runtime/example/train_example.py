# !/usr/bin/env python
# -*-coding:utf-8 -*-
from atom_runtime.atom_runtime_api.operator.train_api import TrainOperatorApi
import pickle

from tensorflow.python.framework.graph_util import convert_variables_to_constants
# import tensorflow as tf
import tensorflow.compat.v1 as tf
tf.disable_v2_behavior()

class TrainExample(TrainOperatorApi):

    def __init__(self) :
        super().__init__()
    
    def _init_session(self):
        self.saver = tf.train.Saver(max_to_keep=1)
        config = tf.ConfigProto()
        config.gpu_options.allow_growth = True
        self.sess = tf.Session(config=config)
        self.sess.run(tf.global_variables_initializer())


    def build_model(self):
        # tf Graph Input
        self.x = tf.placeholder(tf.float32, [None, 784])  # mnist data image of shape 28*28=784
        self.y = tf.placeholder(tf.float32, [None, 10])  # 0-9 digits recognition => 10 classes

        # Set model weights
        self.W = tf.Variable(tf.zeros([784, 10]))
        self.b = tf.Variable(tf.zeros([10]))

        # Construct model
        self.pred = tf.nn.softmax(tf.matmul(self.x, self.W) + self.b, name='output')  # Softmax

        # Minimize error using cross entropy
        self.cost = tf.reduce_mean(-tf.reduce_sum(self.y * tf.log(self.pred), reduction_indices=1))
        # Gradient Descent
        self.optimizer = tf.train.GradientDescentOptimizer(self.learning_rate).minimize(self.cost)

    def initialization(self):
        super().initialization()
        self.avg_cost = 0
        self.total_batch = 0
        self.learning_rate = 0.01
        self.training_epochs = 25
        self.batch_size = 1024
        self.display_step = 1
        self.training_epochs = 100
        self.build_model()
        self.display_step = 10
        self._init_session()

    def execute(self , data):
        self.total_batch += + len(data)
        # Run optimization op (backprop) and cost op (to get loss value)
        _, c = self.sess.run([self.optimizer, self.cost], feed_dict={self.x: data, self.y: self.labels})
        # Compute average loss
        self.avg_cost += c
        pass

    def result(self):
        return  convert_variables_to_constants(self.sess, self.sess.graph_def, output_node_names=['output']).SerializeToString()
    
    def comparision_execute(self,data):
        self.total_batch += + len(data)
        _, c = self.sess.run([self.optimizer, self.cost], feed_dict={self.x: data, self.y: self.labels})
        self.avg_cost += c


    def reset(self,num):
        self.total_batch  = 0
        self.avg_cost = 0
        pass


def split_sample(self, li):
    datas, labels = [], []
    for data, label in li:
            datas.append(data)
            labels.append(label)
    return datas, labels



def main():
    with open('example/train.pkl', 'rb') as f:
        train_data = pickle.load(f)
    trainData = {}
    trainData["avg_cost"] = 1
    train_example = TrainExample()
    train_example.operators_config = trainData
    train_example.execute(train_data)

    with open('example/val.pkl', 'rb') as f:
        val_data = pickle.load(f)
    train_example.comparision_execute(val_data)


if __name__ == '__main__':
    main()