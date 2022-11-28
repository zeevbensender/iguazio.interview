# WEB Crawler application
## Lightricks interview home assignment

This application is crawling over the root web page (provided as a CLI parameter)
and recursively into the links on the page. The depth of the recursion is provided as a second CLI parameter.
The application calculates rank of each page using the ratio of same-domain links that it contains. The higher 
percentage of the same-domain links, the higher it's rank.

The result of the calculation is TSV formatted and printed either to the output file or standard output.
The name of the output file should be provided as third parameter, otherwise the results
will be printed to the standard output.

### Prerequisites
1. JDK 8 or higher
2. Maven

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

The main class is ```com.lightricks.homework.crawler.crawler```.
Upon start, it runs the application services. Main services are listed below:
1. ```PageReader``` reads links from the provided URL
2. ```CrawlingPlugin``` fetches the links from the reader and sends them to ```PageProcessor```.
3. ```PageProcessor``` polls the links sent by ```CrawlingPlugin```. This component watches the pages depth and fires 
the last link event.
4. ```CachingService``` serves as a links cache
5. ```AggregationPlugin``` calculates page score and prints the output data
