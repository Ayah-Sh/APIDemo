package zippopotam;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import services.ZipCodeLocationService;

public class ZipCodeTest {


    @Test
    public void testValidPostalCode_Expect200() {
        // Positive Test Case: Expects 200 OK
        Response response = ZipCodeLocationService.getZipCodeLocation(
                "de",
                "01067",
                200
        );

        // Safe to assert on body because status code was already checked
        System.out.println("Country is: " + response.jsonPath().getString("country"));
    }
}
