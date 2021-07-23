package io.opelt.restaurantsearch.adapter.input;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RestaurantControllerTest {

  @LocalServerPort
  int port;

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @Test
  void givenNoFilterWhenSearchThenReturnMappedRestaurant() {
    when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("", hasSize(5))
        .body("[0].name", equalTo("Deliciousgenix"))
        .body("[0].distance", equalTo(1.0F))
        .body("[0].price", equalTo(10))
        .body("[0].customerRating", equalTo(4))
        .body("[0].cuisine", equalTo("Spanish"))
        .body(matchesJsonSchemaInClasspath("restaurantSchema.json"));
  }

  @Test
  void givenNoFilterWhenSearchThenReturnSortedList() {
    when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("", hasSize(5))
        .body("[0].name", equalTo("Deliciousgenix"))
        .body("[1].name", equalTo("Deliciouszilla"))
        .body("[2].name", equalTo("Fodder Table"))
        .body("[3].name", equalTo("Dished Grill"))
        .body("[4].name", equalTo("Sizzle Yummy"));
  }

  @Test
  void givenNameFilterWhenSearchThenReturnSortedList() {
    given()
        .queryParam("name", "Fodder")
        .when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("", hasSize(2))
        .body("[0].name", equalTo("Fodder Table"))
        .body("[1].name", equalTo("Fodder Tasty"));
  }

  @Test
  void givenDistanceFilterWhenSearchThenReturnSortedList() {
    given()
        .queryParam("distance", 1)
        .when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("", hasSize(5))
        .body("[0].name", equalTo("Deliciousgenix"))
        .body("[1].name", equalTo("Deliciouszilla"))
        .body("[2].name", equalTo("Fodder Table"))
        .body("[3].name", equalTo("Dished Grill"))
        .body("[4].name", equalTo("Sizzle Yummy"));
  }

  @Test
  void givenDistanceGreaterThanMaxFilterWhenSearchThenReturnError() {
    given()
        .queryParam("distance", 11)
        .when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("status", equalTo(400))
        .body("code", equalTo("InvalidFilterException"))
        .body("description", equalTo("Invalid filter on field distance due to constraint: value must be between 1 and 10"))
        .body(matchesJsonSchemaInClasspath("errorSchema.json"));
  }

  @Test
  void givenDistanceLesserThanMinFilterWhenSearchThenReturnError() {
    given()
        .queryParam("distance", 0)
        .when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("status", equalTo(400))
        .body("code", equalTo("InvalidFilterException"))
        .body("description", equalTo("Invalid filter on field distance due to constraint: value must be between 1 and 10"))
        .body(matchesJsonSchemaInClasspath("errorSchema.json"));
  }

  @Test
  void givenPriceFilterWhenSearchThenReturnSortedList() {
    given()
        .queryParam("price", 10)
        .when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("", hasSize(5))
        .body("[0].name", equalTo("Deliciousgenix"))
        .body("[1].name", equalTo("Dished Grill"))
        .body("[2].name", equalTo("Kitchenster"))
        .body("[3].name", equalTo("Chow Table"))
        .body("[4].name", equalTo("Grove Table"));
  }

  @Test
  void givenPriceLesserThanMinFilterWhenSearchThenReturnError() {
    given()
        .queryParam("price", 9)
        .when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("status", equalTo(400))
        .body("code", equalTo("InvalidFilterException"))
        .body("description", equalTo("Invalid filter on field price due to constraint: value must be between 10 and 50"))
        .body(matchesJsonSchemaInClasspath("errorSchema.json"));
  }

  @Test
  void givenPriceGreaterThanMaxFilterWhenSearchThenReturnError() {
    given()
        .queryParam("price", 51)
        .when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("status", equalTo(400))
        .body("code", equalTo("InvalidFilterException"))
        .body("description", equalTo("Invalid filter on field price due to constraint: value must be between 10 and 50"))
        .body(matchesJsonSchemaInClasspath("errorSchema.json"));
  }

  @Test
  void givenCustomerRatingFilterWhenSearchThenReturnSortedList() {
    given()
        .queryParam("customerRating", 5)
        .when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("", hasSize(5))
        .body("[0].name", equalTo("Grove Table"))
        .body("[1].name", equalTo("Bang Delicious"))
        .body("[2].name", equalTo("Traditional Chow"))
        .body("[3].name", equalTo("Tableadora"))
        .body("[4].name", equalTo("Place Palace"));
  }

  @Test
  void givenCustomerRatingGreaterThanMaxFilterWhenSearchThenReturnError() {
    given()
        .queryParam("customerRating", 6)
        .when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("status", equalTo(400))
        .body("code", equalTo("InvalidFilterException"))
        .body("description", equalTo("Invalid filter on field customerRating due to constraint: value must be between 0 and 5"))
        .body(matchesJsonSchemaInClasspath("errorSchema.json"));
  }

  @Test
  void givenCustomerRatingLesserThanMinFilterWhenSearchThenReturnError() {
    given()
        .queryParam("customerRating", -1)
        .when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("status", equalTo(400))
        .body("code", equalTo("InvalidFilterException"))
        .body("description", equalTo("Invalid filter on field customerRating due to constraint: value must be between 0 and 5"))
        .body(matchesJsonSchemaInClasspath("errorSchema.json"));
  }

  @Test
  void givenCuisineFilterWhenSearchThenReturnSortedList() {
    given()
        .queryParam("cuisine", "Thai")
        .when()
        .get("/restaurants")
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("", hasSize(5))
        .body("[0].name", equalTo("Perfection Palace"))
        .body("[1].name", equalTo("Hut Chow"))
        .body("[2].name", equalTo("Hotspot Palace"))
        .body("[3].name", equalTo("Tastylia"))
        .body("[4].name", equalTo("Chopped Table"));
  }
}