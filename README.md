# thin_es_wrapper
A simple app for demonstration purposes, that returns documents stored in elasticsearch for HTTP GET requests.

## Prerequisities
- gradle build tool >= 2.6 http://gradle.org/
- access to an elasticsearch cluster

## Clone, build and start the API
In a work directory do the steps below

```bash
git clone https://github.com/droptableusers/thin_es_wrapper.git
cd thin_es_wrapper/
gradle clean build shadowJar
```

The config file comes with sane defaults, but you may need to change it to reflect local configuration.
(e.g. change the name of the elasticsearch cluster and index name to be used, etc.)
```
vim src/main/resources/config.yaml
```

Finally, start the API
```bash
java -jar build/libs/thin_es_wrapper-1.0-all.jar server src/main/resources/config.yaml
```

## A quick test
Save some data to the configured index and type, e.g. with logstash. Make sure that the stored data is 
sortable by a field @timestamp, then execute the following:

To get the first page of the results:
```bash
curl -XGET 'http://localhost:9976/messages'
``` 
Or customize the from and pagesize parameters:
```bash
curl -XGET 'http://localhost:9976/messages?pageSize=5&from=15'
``` 

Get a specific entry:
```bash
curl -XGET http://localhost:9976/messages/5
```
