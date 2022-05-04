
# Smaato Rest Task Application

This rest application accepts 10K request per second on the endpoint /api/smaato/accept.
This application can run behind the load balancer while maintaining the integrity of request 
id as query param. The application also accept the second optional param which is uri. When 
provided it calls the uri with count of requestid. This application can also post the count every minute
on kafka topic.




## API Reference

#### Accept requestId 

```http
  GET /api/smaato/accept
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `string` | **Required**. Request Id |
| `uri` | `string` | **Optional**. uri of another end point |


#### Post count : This is a sample end point for uri param for testing

```http
  POST /api/smaato/count
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `count`      | `string` | **Required**. Count of requestId received per min |




## How to run the application

1. The prerequisite for projects are internet,jdk 11, maven,docker

2. Clone the project in the local folder

`git clone https://github.com/anuragmnl/rest_app.git`


3. In the docker-compose I have kept following file
common.yml , kafka_cluster.yml and redis.yml

Point the terminal to docker-compose folder. Than you can run following command

`docker-compose -f common.yml -f kafka_cluster.yml -f redis.yml up -d`

This command will make kafka cluster and redis up on docker. 


4. The application can be run in following modes

| Redis     | Kafka                |Description
| :------- | :------------------------- |:------------------------- |
| `N` | `N` |Local cache mode with  and writing count to logs|
| `Y` | `N` |Distributed cache mode and writing count to logs |
| `N` | `Y` |Local cache mode and writing count to kafka topic |
| `Y` | `Y` |Distributed cache mode and writing count to kafka topic |




5. The control of flag can be done either command line or application.yml

| Redis     | Property                |Allowed Property Value
| :------- | :------------------------- |:------------------------- |
| `Redis` | `smaato.application.redis.required` |true/false|
| `Kafka` | `smaato.application.kafka.required` |true/false |

6. The application can be built through following command. The pom.xml has the capabilty to 
build jar as well docker image.

    `mvn clean install -DskipTests`

Result should be following

| Artifact type                |Allowed Property Value
| :------------------------- |:------------------------- |
| `jar` |smaato-rest-task.jar|
| `image` | smaato.rest.task.service:latest|

Once done you can run following command

`docker-compose -f common.yml -f kafka_cluster.yml -f redis.yml -f service.yml up -d`

**Please note the application can also be build with redis and kafka required flag as false in case redis and kafka not present**







## Deployment

To deploy this project run

```bash
  `docker-compose -f common.yml -f kafka_cluster.yml -f redis.yml -f service.yml up -d`
```


You can also run the application by below command. By default it will run on port **8078**

`java -jar smaato-rest-task.jar`

if you want to run on any other port you can run following command

`java -jar smaato-rest-task.jar -Dserver.port=<desired port>`
## 🚀 Git hub

`https://github.com/anuragmnl/rest_app`




