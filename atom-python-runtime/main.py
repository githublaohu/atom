import logging
from runtime.container import Container,DEEP_RUN_MODE_SIMPLE
from utils.utils import get_env


if __name__ == "__main__":
    mode = get_env("deep-mode")
    container = Container(mode)
    if mode == DEEP_RUN_MODE_SIMPLE:
        train_manage = container.get_train_manage()
        train_manage.simple_run_train()
        logging.info("训练已经运行完成")
    else:
        logging.info("deep-runtime 启动成功")