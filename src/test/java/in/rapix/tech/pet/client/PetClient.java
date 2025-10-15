package in.rapix.tech.pet.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.rapix.tech.core.client.LoggingRestClient;
import in.rapix.tech.core.utils.PropertyFileReader;
import in.rapix.tech.pet.entity.Pet;
import in.rapix.tech.pet.entity.PetErrorResponse;
import in.rapix.tech.pet.entity.PetResponse;
import in.rapix.tech.pet.tests.PetLifecycleTest;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.Map;


public class PetClient {
    private static Logger LOG = LogManager.getLogger(PetLifecycleTest.class);
    LoggingRestClient client = new LoggingRestClient();
    private static final String BASE_URL = PropertyFileReader.getProperty("PET_BASE_URL");
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 3000;
    private ObjectMapper objectMapper = new ObjectMapper();

    public PetResponse createPet(Pet body) {
        Header header = new Header("Content-Type", "application/json");

        String url = PropertyFileReader.getProperty("CREATE_PET_ENDPOINT").replace("{baseUrl}", BASE_URL);
        JSONObject bodyJson = new JSONObject(objectMapper.convertValue(body, Map.class));
        Response response = client.post(url, header, bodyJson.toString());
        LOG.info("Create Pet Response: " + response.asString());
        return new PetResponse(response, body);
    }

    public PetResponse getPetById(Long id) {
        String url = PropertyFileReader.getProperty("GET_PET_ENDPOINT").replace("{baseUrl}", BASE_URL).replace("{petId}", String.valueOf(id));
        Response response = client.get(url);
        return new PetResponse(response, null);
    }

    public PetResponse getPetByIdWithRetrial(Long id) {
        int attempt = 0;
        String url = PropertyFileReader.getProperty("GET_PET_ENDPOINT").replace("{baseUrl}", BASE_URL).replace("{petId}", String.valueOf(id));
        while (attempt < MAX_RETRIES) {
            attempt++;
            Response response = client.get(url);
            String responseBody = response.getBody().asString();

            try {
                // Try parsing as success response
                Pet success = objectMapper.readValue(responseBody, Pet.class);
                if (success.getId() != null) {
                    LOG.info("✅ Success on attempt " + attempt);
                    return new PetResponse(response, success);
                }
            } catch (Exception e) {
                // Not a success response — possibly error
                try {
                    PetErrorResponse error = objectMapper.readValue(responseBody, PetErrorResponse.class);
                    LOG.error("⚠ Attempt " + attempt + " failed: " + error.getMessage());
                } catch (Exception ex) {
                    LOG.error("Invalid response format: " + responseBody);
                }
            }

            if (attempt < MAX_RETRIES) {
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        throw new RuntimeException("Failed to get valid Pet response after " + MAX_RETRIES + " attempts.");
    }

    public PetResponse updatePet(Pet body) {
        String url = PropertyFileReader.getProperty("UPDATE_PET_ENDPOINT").replace("{baseUrl}", BASE_URL);
        Header header = new Header("Content-Type", "application/json");
        JSONObject bodyJson = new JSONObject(objectMapper.convertValue(body, Map.class));
        Response response = client.put(url, header, bodyJson.toString());
        LOG.info("Updated Pet Response: " + response.asString());
        return new PetResponse(response, body);
    }

    public PetResponse deletePet(Long id) {
        String url = PropertyFileReader.getProperty("DELETE_PET_ENDPOINT").replace("{baseUrl}", BASE_URL).replace("{petId}", String.valueOf(id));
        Header header = new Header("Content-Type", "application/json");
        Response response = client.delete(url, header);
        LOG.info("Delete Pet Response: " + response.asString());
        return new PetResponse(response, null);
    }

    public PetResponse findByStatusPet(String status) {
        String url = PropertyFileReader.getProperty("FIND_BY_STATUS_PET_ENDPOINT").replace("{baseUrl}", BASE_URL).replace("{status}", status);
        Response response = client.get(url);
        LOG.info("Find By Status Pet Response: " + response.asString());
        return new PetResponse(response, null);
    }
}
