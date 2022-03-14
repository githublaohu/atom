### 案例

Model.py
```python
from deep_runtime_api.train_runtime import TrainFactory,TrainRuntime,TrainResult
from tensorflow.python.framework.graph_util import convert_variables_to_constants
import tensorflow as tf


class Model(TrainRuntime):
    def __init__(self,trainData,modeData):
        TrainRuntime.__init__(self,trainData,modeData)
        self.avg_cost = trainData["avg_cost"]
        self.total_batch = 0
        self.learning_rate = 0.01
        self.training_epochs = 25
        self.batch_size = 1024
        self.display_step = 1
        self.training_epochs = 100
        self.build_model()
        self.display_step = 10
        self._init_session()

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

    def execute(self , data):
        self.total_batch += + len(data)
        batch_xs, batch_ys = self.split_sample(data)
        # Run optimization op (backprop) and cost op (to get loss value)
        _, c = self.sess.run([self.optimizer, self.cost], feed_dict={self.x: batch_xs, self.y: batch_ys})
        # Compute average loss
        self.avg_cost += c
        pass

    def result(self):
        output_graph_def = convert_variables_to_constants(self.sess, self.sess.graph_def, output_node_names=['output'])
        self.train_result.mode_str = output_graph_def.SerializeToString()
        self.train_result.mode_name = "mode-aaaa.xad"
        return  self.train_result
    
    def comparision_execute(self,data):
        self.total_batch += + len(data)
        batch_xs, batch_ys = self.split_sample(data)
        _, c = self.sess.run([self.optimizer, self.cost], feed_dict={self.x: batch_xs, self.y: batch_ys})
        self.avg_cost += c


    def reset(self,num):
        self.total_batch  = 0
        self.avg_cost = 0
        pass

    def data_type(self):
        return "list"
    
    def data_heads(self):
        return []

    def execute_limit(self):
        return 10000

    def split_sample(self, li):
        datas, labels = [], []
        for data, label in li:
            datas.append(data)
            labels.append(label)
        return datas, labels


class ModetrainFactory(TrainFactory):

    def get_train_runtime(self):
        return Model

    def get_train_result_handle(self):
        pass

```

main.py
```python
def main():
    with open('.train.pkl', 'rb') as f:
        train_data = pickle.load(f)
    with open('.val.pkl', 'rb') as f:
        val_data = pickle.load(f)
    trainData = {}
    trainData["avg_cost"] = 1
    model = Model(trainData,None)
    model.execute(train_data)
    model.comparision_execute(val_data)

if __name__ == '__main__':
    main()
```

```shell
python setup.py sdist bdist_wheel

twine upload -r deep dist/deep_r*

twine upload -r {私服配置名} dist/deep_r*

```