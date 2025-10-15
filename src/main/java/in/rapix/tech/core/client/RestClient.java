package in.rapix.tech.core.client;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface RestClient {

    Response get(String serverURL, Header headerMap);
    Response get(String serverURL);
    Response get(String serverURL, Header headerMap, String filePath);
    Response get(String serverURL, Header headerMap, String... body);
    Response get(String serverURL, Header headerMap, Map<String, Object> parameters);
    Response post(String serverURL, Header header, String... body);
    Response post(String serverURL, Headers headers, String... body);
    Response post(String serverURL, String... body);
    Response post(String serverURL, Header header);
    Response post(String serverURL, Headers headers, Map<String, Object> formBody);
    Response put(String serverURL, Header header, String body);

    Response put(String serverURL, Header header);

    Response delete(String serverURL, Header header);
    Response delete(String serverURL, Header header, String... body);
    Response patch(String serverURL, Header header, String body);
    Response patch(String serverURL, Header header);
    Response get(String serverURL, String username, String password);
    Response post(String serverURL, String username, String password, Map<String, Object> formBody);
    Response post(String serverURL, Header headerMap, List<File> files);
    Response post(String serverURL, Header header, String body, Map<String, Object> parameters);

}