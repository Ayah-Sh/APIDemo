package restutils;


import config.ConfigReader;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestHelpers {


    public static Response restGet(String URL, String endpoint ){
        return given()
                .when()
                .get(URL.concat(endpoint))
                .then()
                .extract()
                .response();
    }

    public static Response getPostalCodeInfo(String endpoint ,String country ,String postalCode) {
        return given()
                .baseUri(ConfigReader.getBaseUrl())
                .pathParam("country", country)
                .pathParam("postalCode", postalCode)
                .when()
                .get(endpoint)
                .then().log().all()
                .extract()
                .response();
    }
}
