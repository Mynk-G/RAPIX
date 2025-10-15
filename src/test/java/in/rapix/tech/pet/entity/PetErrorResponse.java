package in.rapix.tech.pet.entity;

import lombok.Data;

@Data
public class PetErrorResponse {

    private int code;
    private String message;
    private String type;
}
