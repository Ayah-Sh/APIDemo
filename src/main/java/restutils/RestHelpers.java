package restutils;


import constants.Endpoints;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestHelpers {


    public static Response restGet(String URL, Endpoints endpoint, int expectedStatusCode) {
        return given()
                .when()
                .get(URL.concat(Endpoints.GET_POSTAL_CODE))
                .then().statusCode(expectedStatusCode)
                .extract()
                .response();
    }

    public static Response getPostalCodeInfo(String endpoint ,String country ,String postalCode,int expectedStatusCode) {
        return given()
                .baseUri(Endpoints.BASE_URL)
                .pathParam("country", country)
                .pathParam("postalCode", postalCode)
                .when()
                .get(endpoint)
                .then().statusCode(expectedStatusCode)
                .extract()
                .response();
    }
}
