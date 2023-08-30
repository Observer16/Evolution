package utils.service;

import io.restassured.http.Header;
import io.restassured.http.Headers;

public class HeaderUtils {

    public static Headers createHeaders(String authToken) {
        return new Headers(
                new Header("Authorization", "Bearer " + authToken),
                new Header("accept", "application/json")
        );
    }
}
