package services;

import constants.Endpoints;
import io.restassured.response.Response;
import restutils.RestHelpers;



public class ZipCodeLocationService {


    public static Response getZipCodeLocation(String country, String postalCode, int expectedStatusCode) {


        return RestHelpers.getPostalCodeInfo(Endpoints.GET_POSTAL_CODE,country,postalCode, expectedStatusCode);
    }

}
