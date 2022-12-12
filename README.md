# WEB Crawler application
## Lightricks interview home assignment

### Used examples
1. [Java Asynchronous HttpClient Overview and Tutorial – sendAsync()](https://crunchify.com/java-asynchronous-httpclient-overview-and-tutorial-sendasync/)
### Prerequisites
1. JDK (I'm using ```Java(TM) SE Runtime Environment (build 18.0.2.1+1-1)```, but I think, that earlier versions, starting from jdk 8, should work as well)
2. Maven (I'm using ```apache-maven-3.8.6```)

### Build
```shell
$ cd <project root>
$ mvn clean package
```

### Usage
```shell
$ cd <project root>
$ java -jar ./target/crawler-0.0.1-SNAPSHOT.jar <URL> <level> [output file]
```

### Architecture
The application is based on the Spring Boot framework. This framework allows developers to freely 
link and decouple modules. It supports easy and various configuration methods.

Some components are implemented as plugins to provide an ability to add more features to the 
application as one of the assignment requirements.

The main class is ```crawler```.
Upon start, it runs the application services. Main services are listed below:
1. ```PageReader``` reads links from the provided URL
2. ```CrawlingPlugin``` fetches the links from the reader and sends them to ```PageProcessor```.
3. ```PageProcessor``` polls the links sent by ```CrawlingPlugin```. This component watches the pages depth and fires 
the last link event.
4. ```CachingService``` serves as a links cache
5. ```AggregationPlugin``` calculates page score and prints the output data

In case you don't have jdk installed, but you have docker on your local machine, you can run the app within a container:
1. Build the image: ```$ docker build . -t lightricks```
2. Run the container: ```docker run -v $(pwd)/tmp:/output lightricks https://en.wikipedia.org 2 /output/en.wikipedia.org.tsv```
3. Find the results in ```<project root>/tmp``` directory