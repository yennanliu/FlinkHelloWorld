# FlinkHelloWorld


## Install
- https://ci.apache.org/projects/flink/flink-docs-stable/getting-started/tutorials/local_setup.html
- https://github.com/yennanliu/utility_shell/tree/master/flink

```bash
# install  (Mac OSX)
$ brew install apache-flink
...
$ flink --version
Version: 1.2.0, Commit ID: 1c659cf

# start a local flink cluster
bash script/start-cluster.sh   # Start Flink

bash script/stop-cluster.sh    # Stop flink
```

## Setup (maven)

```bash
# https://ci.apache.org/projects/flink/flink-docs-release-1.11/dev/project-configuration.html

```

## Quick start (manually)

```bash
# compile the scala script 

# to the project
cd flink-1.10.0
# start the flink local server 
./bin/start-cluster.sh local

# stop the flink local server
./bin/stop-cluster.sh local

# start flink scala console
.bin/start-scala-shell.sh local

# visit UI via 
# http://localhost:8081

# open a terminal send msg to flink (port 9000)
nc -l 9000 
# run the stream word count job (SocketWindowWordCount)
./bin/flink run examples/streaming/SocketWindowWordCount.jar --port 9000

```

## Quick start (docker)

```bash 
# V1

FLINK_PROPERTIES="jobmanager.rpc.address: jobmanager"
docker network create flink-network

docker run \
       -d \
       --rm \
       --name=jobmanager \
       --network flink-network \
       -p 8081:8081 \
       --env FLINK_PROPERTIES="${FLINK_PROPERTIES}" \
       flink:1.11.1 jobmanager

docker run \
      -d \
      --rm \
      --name=taskmanager \
      --network flink-network \
      --env FLINK_PROPERTIES="${FLINK_PROPERTIES}" \
      flink:1.11.1 taskmanager
```

```bash
# V2
# pull the dokcer image
docker pull flink

# Method 1) run a JobManager (master)
docker run --name flink_jobmanager -d -t flink jobmanager
docker run -it flink bash
flink run examples/batch/WordCount.jar
flink run examples/batch/KMeans.jar 
flink run examples/streaming/SocketWindowWordCount.jar  --port 9000

# Method 2) run a TaskManager (worker). 
# Notice that workers need to register with the JobManager directly or via ZooKeeper so the master starts to send them tasks to execute.
docker run --name flink_taskmanager -d -t flink taskmanager

# Method 3) Running a cluster using Docker Compose
docker-compose up
```

## Ref 

<details>
<summary>Ref</summary>

- Project config
	- https://ci.apache.org/projects/flink/flink-docs-release-1.11/dev/project-configuration.html

- Start Flink with SBT Scala
	- https://ci.apache.org/projects/flink/flink-docs-master/dev/project-configuration.html

- Flink Scala
	- https://ci.apache.org/projects/flink/flink-docs-release-1.10/dev/projectsetup/scala_api_quickstart.html

- Flink train
	- https://training.ververica.com/

- Flink example
	- https://ci.apache.org/projects/flink/flink-docs-release-1.10/getting-started/examples/
	- https://www.elastic.co/blog/building-real-time-dashboard-applications-with-apache-flink-elasticsearch-and-kibana?fbclid=IwAR0EzGMB-P_gazMyG2yG4GgmTjwxwz_aXE4vpbV51nY29e55jcMqezp_pvw

- Flink load json
	- https://flink.sojb.cn/dev/table/connect.html#json-format
	- https://flink-docs-cn.gitbook.io/project/05-ying-yong-kai-fa/04-table-api-and-sql/lian-jie-wai-bu-xi-tong
	- Example
		- https://gousios.gr/courses/bigdata/2017/assignment-streaming.html
		- https://gousios.org/courses/bigdata/2017/assignment-streaming-solutions.pdf

- json4s intro	
	- https://www.cnblogs.com/yyy-blog/p/11819302.html
	- https://blog.csdn.net/leehbing/article/details/74391308
	- https://code5.cn/so/scala/1794442

- Import Scala into an IDE
	- https://ci.apache.org/projects/flink/flink-docs-stable/flinkDev/ide_setup.html

</details>

### Infra Ref (Docker, k8s)

<details>
<summary>Ref</summary>

- Flink with docker
	- https://flink.apache.org/news/2020/08/20/flink-docker.html
	- https://ci.apache.org/projects/flink/flink-docs-stable/ops/deployment/docker.html

- Flink with K8S
	- https://ci.apache.org/projects/flink/flink-docs-stable/ops/deployment/kubernetes.html

</details>

## Dockerfile
- https://hub.docker.com/_/flink?tab=description