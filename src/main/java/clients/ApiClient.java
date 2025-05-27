package clients;

import io.restassured.specification.RequestSpecification;

public interface ApiClient
{
    RequestSpecification getClient();
}
