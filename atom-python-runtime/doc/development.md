
### 打包
```shell
python setup.py bdist_wheel
```
### 发布
```shell
twine upload -r pypi ./dist/*
```