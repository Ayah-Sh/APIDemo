package constants;

public final class Endpoints {

    // Prevent instantiation
    private Endpoints() {
    }


    // User Endpoints
    // Parameterized Endpoint
    //both country and postalCode are path parameters, so we can use placeholders in the endpoint string
    public static final String GET_POSTAL_CODE = "/{country}/{postalCode}";


}
