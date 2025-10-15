package in.rapix.tech.pet.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class PetResponse {

    private Response response;
    private Pet request;

    public PetResponse(Response response, Pet request) {
        this.response = response;
        this.request = request;
    }
}
