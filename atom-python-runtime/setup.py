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
import io
import os
import sys
from shutil import rmtree

from setuptools import find_packages, setup, Command


#  just run `python setup.py upload`
here = os.path.abspath(os.path.dirname(__file__))

with io.open(os.path.join(here, 'README.md'), encoding='UTF-8') as f:
    long_description = '\n' + f.read()


class UploadCommand(Command):
    """Support setup.py upload."""

    description = 'Build and publish the package.'
    user_options = []

    @staticmethod
    def status(s):
        """Prints things in bold."""
        print('\033[1m{0}\033[0m'.format(s))

    def initialize_options(self):
        pass

    def finalize_options(self):
        pass

    def run(self):
        try:
            self.status('Removing previous builds...')
            rmtree(os.path.join(here, 'dist'))
        except OSError:
            pass

        self.status('Building Source and Wheel (universal) distribution...')
        os.system(
            '{0} setup.py sdist bdist_wheel --universal'.format(sys.executable))

        self.status('Uploading the package to PyPi via Twine...')
        os.system('twine upload dist/*')

        sys.exit()


packages = find_packages()
setup(
    name="atom-runtime",
    packages=packages,
    include_package_data=True,
    zip_safe=False,
    version= "0.0.3",
    entry_points={"console_scripts": ["atom=atom_runtime.atom_controller:main"]},

    url="https://github.com/githublaohu/atom",
    license="Mulan PubL 2",
    classifiers=[
        "Topic :: Software Development :: Libraries :: Python Modules",
        "Operating System :: OS Independent",
        "Programming Language :: Python :: 3.6",
        "Programming Language :: Python :: 3.7"
    ],
    keywords=['lamp', 'lampup','atom'],
    author="laohu",
    author_email="2372554140@qq.com",
    description="atom runtime ",
    long_description=long_description,
    long_description_content_type="text/markdown",
    install_requires=[
        "pandas",
        "pydantic",
        "pyyaml",
        "nacos-sdk-python",
        "gitpython",
        "Flask",
        "nvidia-ml-py",
        "hdfs3",
        "oss2",
        "crcmod",
        "pymysql",
        "happybase"
    ],
    cmdclass={
        'upload': UploadCommand,
    },
    project_urls={
        'Documentation': 'https://github.com/githublaohu/atom',
        'Source': 'https://github.com/githublaohu/atom'
    },
)