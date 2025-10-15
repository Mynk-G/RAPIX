package in.rapix.tech.pet.helper;

import in.rapix.tech.pet.entity.Pet;
import in.rapix.tech.pet.entity.PetErrorResponse;
import in.rapix.tech.pet.entity.PetResponse;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class PetAssertHelper {

    public void assertPetCreation(PetResponse petResp, SoftAssert softAssert) {
        softAssert.assertEquals(petResp.getResponse().getStatusCode(), 200, "Created Pet Status code should be 200");
        Pet petRespObj = petResp.getResponse().as(Pet.class);
        Pet petRequest = petResp.getRequest();
        softAssert.assertEquals(petRespObj.getId(), petRequest.getId(), "Pet ID should match");
        softAssert.assertEquals(petRespObj.getName(), petRequest.getName(), "Pet Name should match");
        softAssert.assertEquals(petRespObj.getStatus(), petRequest.getStatus(), "Pet Status should match");
        softAssert.assertTrue(
                PetHelper.arePhotoUrlsEqual(petRespObj.getPhotoUrls(), petRequest.getPhotoUrls()),
                "Photo URLs should match"
        );
        softAssert.assertTrue(
                PetHelper.areTagsEqual(petRespObj.getTags(), petRequest.getTags()),
                "Tags should match"
        );
        softAssert.assertTrue(
                PetHelper.areCategoryEqual(petRespObj.getCategory(), petRequest.getCategory()),
                "Category should match"
        );
    }

    public void assertPetDeletion(PetResponse deleteResp, String petId, SoftAssert softAssert) {
        Assert.assertEquals(deleteResp.getResponse().getStatusCode(), 200, "Deletion Status code should be 200");
        PetErrorResponse deletedPetResp = deleteResp.getResponse().as(PetErrorResponse.class);
        softAssert.assertEquals(deletedPetResp.getCode(), "200", "Deletion Response code should be 200");
        softAssert.assertEquals(deletedPetResp.getType(), "unknown", "Deletion Response type should be 'unknown'");
        softAssert.assertEquals(deletedPetResp.getMessage(), String.valueOf(petId), "Deletion Response message should match Pet ID");
    }

    public void assertGetPetNotFound(PetResponse getResp, Long petId, SoftAssert softAssert) {
        Assert.assertEquals(getResp.getResponse().getStatusCode(), 404, "Get Status code should be 404");
        PetErrorResponse getPetResp = getResp.getResponse().as(PetErrorResponse.class);
        softAssert.assertEquals(getPetResp.getCode(), 1, "Get Pet Response code should be 404");
    }
}
