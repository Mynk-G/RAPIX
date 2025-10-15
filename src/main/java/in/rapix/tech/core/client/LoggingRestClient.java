package in.rapix.tech.core.client;

import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import in.rapix.tech.core.utils.CommonUtilities;
import in.rapix.tech.core.utils.PropertyFileReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class LoggingRestClient implements RestClient{

    private final List<Integer> errorCodes = CommonUtilities.getErrorCodes();

    private static Logger LOG = LogManager.getLogger(LoggingRestClient.class);
    RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig();

    @Override
    public Response get(String serverURL, Header headerMap) {

        RequestSpecification request = given().config(config);
        if (headerMap != null) {
            request.header(headerMap);
        }
        CommonUtilities.logRequest(Method.GET.name(), serverURL, "");
        request.relaxedHTTPSValidation().when();
        Response response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).get(serverURL);
        LOG.info(response.asPrettyString());

        int retryCount = Integer.parseInt(Objects.requireNonNull(PropertyFileReader.getProperty("RETRY_COUNT")));
        while (errorCodes.contains(response.getStatusCode()) && retryCount > 0) {
            LOG.warn("Retrying request due to error code: " + response.getStatusCode());
            response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).get(serverURL);
            retryCount--;
        }
        return response;
    }

    @Override
    public Response get(String serverURL) {
        RequestSpecification request = given().config(config);

        CommonUtilities.logRequest(Method.GET.name(), serverURL, "");
        request.relaxedHTTPSValidation().when();
        Response response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).get(serverURL);
        LOG.info(response.asPrettyString());

        int retryCount = Integer.parseInt(Objects.requireNonNull(PropertyFileReader.getProperty("RETRY_COUNT")));
        while (errorCodes.contains(response.getStatusCode()) && retryCount > 0) {
            LOG.warn("Retrying request due to error code: " + response.getStatusCode());
            response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).get(serverURL);
            retryCount--;
        }
        return response;
    }

    @Override
    public Response get(String serverURL, Header headerMap, String filePath) {
        return null;
    }

    @Override
    public Response get(String serverURL, Header headerMap, String... body) {
        return null;
    }

    @Override
    public Response get(String serverURL, Header headerMap, Map<String, Object> parameters) {
        return null;
    }

    @Override
    public Response post(String serverURL, Header header, String... body) {
        Response response = null;
        // Request Object
        RequestSpecification request = given().config(config).contentType(ContentType.JSON);
        if (Objects.nonNull(header)) {
            request.header(header);
        }
        if (Objects.nonNull(body) && body.length > 0) {
            request.body(body[0]);
        }
        CommonUtilities.logRequest(Method.POST.name(), serverURL, body.length > 0 ? body[0] : "");
        request.relaxedHTTPSValidation().when();
        response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).post(serverURL);

        int retryCount = Integer.parseInt(Objects.requireNonNull(PropertyFileReader.getProperty("RETRY_COUNT")));
        while (errorCodes.contains(response.getStatusCode()) && retryCount > 0) {
            LOG.warn("Retrying request due to error code: " + response.getStatusCode());
            response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).post(serverURL);
            retryCount--;
        }
        return response;
    }

    @Override
    public Response post(String serverURL, Headers headers, String... body) {
        return null;
    }

    @Override
    public Response post(String serverURL, String... body) {
        RequestSpecification request = given().config(config).contentType(ContentType.JSON);

        if (Objects.nonNull(body) && body.length > 0) {
            request.body(body[0]);
        }

        CommonUtilities.logRequest(Method.POST.name(), serverURL, body.length > 0 ? body[0] : "");
        request.relaxedHTTPSValidation().when();
        Response response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).post(serverURL);
        LOG.info(response.asPrettyString());

        int retryCount = Integer.parseInt(Objects.requireNonNull(PropertyFileReader.getProperty("RETRY_COUNT")));
        while (errorCodes.contains(response.getStatusCode()) && retryCount > 0) {
            LOG.warn("Retrying request due to error code: " + response.getStatusCode());
            response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).post(serverURL);
            retryCount--;
        }
        return response;
    }

    @Override
    public Response post(String serverURL, Header header) {
        return null;
    }

    @Override
    public Response post(String serverURL, Headers headers, Map<String, Object> formBody) {
        return null;
    }

    @SneakyThrows
    public Response put(String serverURL, Header header, String body) {
        Response response = null;
        // Request Object
        RequestSpecification request = given().config(config).urlEncodingEnabled(false).contentType(ContentType.JSON).body(body);
        if (header != null) {
            request.header(header);
        }

        CommonUtilities.logRequest(Method.PUT.name(), serverURL, body);
        request.relaxedHTTPSValidation().when();
        response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).put(serverURL);
        LOG.info(response.asPrettyString());

        int retryCount = Integer.parseInt(Objects.requireNonNull(PropertyFileReader.getProperty("RETRY_COUNT")));
        while (errorCodes.contains(response.getStatusCode()) && retryCount > 0) {
            LOG.warn("Retrying request due to error code: " + response.getStatusCode());
            response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).put(serverURL);
            retryCount--;
        }
        return response;
    }

    @Override
    public Response put(String serverURL, Header header) {
        // Request Object
        RequestSpecification request = given()
                .config(config)
                .contentType(ContentType.JSON);
        if (Objects.nonNull(header)) request.header(header);
        // Execution
        Response response = request.relaxedHTTPSValidation().log().all().when()
                .filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).put(serverURL);
        response.then().log().all();
        return response;
    }

    @SneakyThrows
    public synchronized Response delete(String serverURL, Header headerMap) {
        Response response = null;
        RequestSpecification request = given().config(config);
        if (headerMap != null) {
            request.header(headerMap);
        }

        CommonUtilities.logRequest(Method.DELETE.name(), serverURL, "");
        request.relaxedHTTPSValidation().when();
        response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).delete(serverURL);

        int retryCount = Integer.parseInt(Objects.requireNonNull(PropertyFileReader.getProperty("RETRY_COUNT")));
        while (errorCodes.contains(response.getStatusCode()) && retryCount > 0) {
            LOG.warn("Retrying request due to error code: " + response.getStatusCode());
            response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).delete(serverURL);
            retryCount--;
        }
        return response;
    }

    @SneakyThrows
    public synchronized Response delete(String serverURL, Header header, String... body) {

        Response response = null;
        // Request Object
        RequestSpecification request = given().config(config).contentType(ContentType.JSON);
        if (Objects.nonNull(header)) {
            request.header(header);
        }
        if (Objects.nonNull(body) && body.length > 0) {
            request.body(body[0]);
        }

        CommonUtilities.logRequest(Method.DELETE.name(), serverURL, body.length > 0 ? body[0] : "");
        request.relaxedHTTPSValidation().when();
        response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).delete(serverURL);

        int retryCount = Integer.parseInt(Objects.requireNonNull(PropertyFileReader.getProperty("RETRY_COUNT")));
        while (errorCodes.contains(response.getStatusCode()) && retryCount > 0) {
            LOG.warn("Retrying request due to error code: " + response.getStatusCode());
            response = request.filter(new AllureRestAssured().setRequestAttachmentName(serverURL)).delete(serverURL);
            retryCount--;
        }
        return response;
    }

    @Override
    public Response patch(String serverURL, Header header, String body) {
        return null;
    }

    @Override
    public Response patch(String serverURL, Header header) {
        return null;
    }

    @Override
    public Response get(String serverURL, String username, String password) {
        return null;
    }

    @Override
    public Response post(String serverURL, String username, String password, Map<String, Object> formBody) {
        return null;
    }

    @Override
    public Response post(String serverURL, Header headerMap, List<File> files) {
        return null;
    }

    @Override
    public Response post(String serverURL, Header header, String body, Map<String, Object> parameters) {
        return null;
    }
}

