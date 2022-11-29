# WEB Crawler application
## Lightricks interview home assignment

This application is crawling over the root web page (provided as a CLI parameter)
and recursively into the links on the page. The depth of the recursion is provided as a second CLI parameter.
The application calculates rank of each page using the ratio of same-domain links that it contains. The higher 
percentage of the same-domain links, the higher it's rank.

The result of the calculation is printed either to the output file or 
to the standard output. The name of the output file should be provided as third parameter, otherwise the results
will be printed to the standard output. The output is TSV formatted.

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
link and decouple modules and easily test them. It supports easy and various configuration methods.

Some components are implemented as plugins to provide an ability to add more features to the 
application. This is one of the assignment requirements.

The main class is ```com.lightricks.homework.crawler.crawler```.
Upon start, it runs the following services:
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