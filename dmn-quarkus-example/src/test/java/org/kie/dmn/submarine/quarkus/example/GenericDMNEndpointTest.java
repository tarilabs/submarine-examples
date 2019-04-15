package org.kie.dmn.submarine.quarkus.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GenericDMNEndpointTest {

    @Test
    public void testHelloEndpoint() {
        given()
               .when().get("/dmn")
          .then()
             .statusCode(200)
               .body("models.size()", is(3));
    }

}