# Restaurant search

Sample RESTFul API to search through a list of restaurants and return the ones that matched the given
user criteria.

- [Restaurant search](#restaurant-search)
    * [API usage](#api-usage)
        + [Filters](#filters)
            - [Price](#price)
            - [Distance](#distance)
            - [Name](#name)
            - [CustomerRating](#customerrating)
            - [Cuisine](#cuisine)
    * [Executing](#executing)
        + [Build](#build)
        + [Running](#running)
    * [Project structure](#project-structure)
    * [Environment](#environment)

## API usage

```
    GET /restaurants
```

There is a Postman [collection](/Restaurant%20search.postman_collection.json) on this repository with the needed configuration to use the API.

### Filters

There are 5 possible query param filters. All of them can be used together but none are required.

#### Price

```
    GET /restaurants?price
```

Filters the restaurants that have an average price equal to or lesser than the filter's value

#### Distance

```
    GET /restaurants?distance
```

Filters the restaurants that have a distance equal to or lesser than the filter's value

#### Name

```
    GET /restaurants?name
```

Filters the restaurants that contains in the name the filter's value

#### CustomerRating

```
    GET /restaurants?customerRating
```

Filters the restaurants that have a customerRating equal to or greater than the filter's value

#### Cuisine

```
    GET /restaurants?cuisine
```

Filters the restaurants that contains in the cuisine name the filter's value


## Executing 

### Build

The build is made using maven.
```
    mvn clean install
```

### Running

The project can be run through the java cli via:
```
    java -jar target/*.jar
```
or via docker using the docker-compose file
```
    docker-compose up
```

## Project structure

The package structure is divided into a domain, use case and adapter strategy.

*domain*: Contains the domain entities

*usecase*: The use cases classes, every use case represents a complete workflow and must only communicate 
with other classes through port interfaces.

*adapter*: Input and output adapters. Input adapters are classes that receives data from outside the application,
for example, the RestController. Output adapters are used to search through data outside the application, 
for example, the file readers.

## Environment

There are a number of environment variable to customize the application

*RESTAURANTS_SEARCH_LIMIT*: How many records the API should return;

*RESTAURANTS_FILE*: The path to the restaurant .csv file. It follows the spring boot resources [convention](https://docs.spring.io/spring-framework/docs/3.0.0.M3/reference/html/ch05s04.html),
so for example to load a file from the classpath you need to pass the path with the prefix "classpath",
example, "classpath:restaurants.csv", for a file on the filesystem, "file:/restaurants.csv", and for an
online resource, "http:http://server.com/restaurants.csv"

*CUISINES_FILE*: The path to the cuisines .csv file. Same as the restaurants file variable.
