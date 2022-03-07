#githublaohu  Lampup@123456
# build
#sudo docker build --platform=linux/amd64 . -t githublaohu/atom-base-cpu:tf1.15-cpu  -f ./Dockerfile_tf1.15_cpu
#sudo docker build --platform=linux/amd64 . -t githublaohu/atom-base-cpu:tf2.4-cpu  -f ./Dockerfile_tf2.4_cpu
#sudo docker build --platform=linux/amd64 . -t githublaohu/atom-base-gpu:tf1.15-gpu  -f ./Dockerfile_tf1.15_gpu
#sudo docker build --platform=linux/amd64 . -t githublaohu/atom-base-gpu:tf2.4-gpu  -f ./Dockerfile_tf2.4_gpu
sudo docker build --platform=linux/amd64 . -t githublaohu/atom-base-cpu:torch1.8-cpu  -f ./Dockerfile_torch1.8_cpu
sudo docker build --platform=linux/amd64 . -t githublaohu/atom-base-gpu:torch1.8-gpu  -f ./Dockerfile_torch1.8_gpu


# upload
#sudo docker push githublaohu/atom-base-cpu:tf1.15-cpu
#sudo docker push githublaohu/atom-base-cpu:tf2.4-cpu
#sudo docker push githublaohu/atom-base-gpu:tf1.15-gpu
#sudo docker push githublaohu/atom-base-gpu:tf2.4-gpu
sudo docker push githublaohu/atom-base-cpu:torch1.8-cpu
sudo docker push githublaohu/atom-base-gpu:torch1.8-gpu