package zippopotam;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import services.ZipCodeLocationService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class ZipCodeTest {


    // TC01: Valid country + valid postal code returns 200 with the correct country


    @Test
    public void testValidPostalCode_Expect200() {


     //------------------- Positive ---------------------------//

        // Positive Test Case: Expects 200 OK
        Response response = ZipCodeLocationService.getZipCodeLocation("de", "01067");

        // Assert the HTTP status code before validating the response body
        assertThat(response.statusCode(), equalTo(200));

        assertThat(response.jsonPath().getString("country"), equalTo("Germany"));
        assertThat(response.jsonPath().getString("'country abbreviation'"), equalTo("DE"));
    }


    // TC2 & 3: Postal code with multiple places -Valid request returns correct place-level details (city/state)
    @Test
    public void testValidPostalCode_VerifyPlaceDetails() {
        Response response = ZipCodeLocationService.getZipCodeLocation("de", "01067");

        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("places[0].'place name'"), equalTo("Dresden"));
        assertThat(response.jsonPath().getString("places[0].state"), equalTo("Sachsen"));
    }



    //------------------- Negative ---------------------------//

    // TC4: Valid country but non-existent postal code returns 404

    @Test
    public void testNonExistentPostalCode_Expect404() {
        Response response = ZipCodeLocationService.getZipCodeLocation("us", "00000");

        assertThat(response.statusCode(), equalTo(404));
    }

    // TC5: Invalid/non-existent country code returns 404
    @Test
    public void testInvalidCountryCode_Expect404() {
        Response response = ZipCodeLocationService.getZipCodeLocation("xx", "12345");
        assertThat(response.statusCode(), equalTo(404));
    }


    //------------------- Edge Cases & Business Logic ---------------------------//



    // TC6:Special Characters in Path
    @Test
    public void testSpecialCharactersInPath_Expect404() {
        Response response = ZipCodeLocationService.getZipCodeLocation("DE", "?01067");
        assertThat(response.statusCode(), equalTo(404));
    }

    // TC7:Country/Postal Code Mismatch
    @Test
    public void testCountryPostalMisMatCh_Expect404() {
        Response response = ZipCodeLocationService.getZipCodeLocation("CA", "01067");
        assertThat(response.statusCode(), equalTo(404));
    }

    // TC: Country code lookup is case-insensitive form API call
    @Test
    public void testCountryCode_IsCaseInsensitive() {
        Response response = ZipCodeLocationService.getZipCodeLocation("jp", "100-0001");
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("country"), equalTo("Japan"));
    }



    // TC9: Response body conforms to the expected JSON schema
    @Test
    public void testValidPostalCode_MatchesJsonSchema() {
        Response response = ZipCodeLocationService.getZipCodeLocation("de", "01067");
        assertThat(response.statusCode(), equalTo(200));

        response.then().body(matchesJsonSchemaInClasspath("schema/response-schema.json"));
    }


}

