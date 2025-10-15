package in.rapix.tech.pet.tests;

import in.rapix.tech.pet.client.PetClient;
import in.rapix.tech.pet.entity.Pet;
import in.rapix.tech.pet.entity.PetResponse;
import in.rapix.tech.pet.helper.PetAssertHelper;
import in.rapix.tech.pet.helper.PetHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Collections;

public class PetLifecycleTest {
    private static Logger LOG = LogManager.getLogger(PetLifecycleTest.class);

    private PetClient petClient;
    private PetAssertHelper petAssertHelper;

    @BeforeClass
    public void setup() {
        petClient = new PetClient();
        petAssertHelper = new PetAssertHelper();
    }

    @Test(description = "End-to-End Pet Lifecycle Test")
    public void testPetLifecycle() {
        SoftAssert softAssert = new SoftAssert();
        //Build Pet Request Object
        Long id = System.currentTimeMillis();
        String name = "Buddy";
        String status = "available";
        Pet.NamedEntity category = PetHelper.getRandomDataFromJsonFile("category-data.json");
        Assert.assertNotNull(category);
        Pet.NamedEntity tag = PetHelper.getRandomDataFromJsonFile("tag-data.json");
        Assert.assertNotNull(tag);

        Pet pet = Pet.builder()
                .id(id)
                .name(name)
                .status(status)
                .photoUrls(Collections.singletonList("http://example.com/photo.jpg"))
                .tags(Collections.singletonList(Pet.NamedEntity.builder().id(tag.getId()).name(tag.getName()).build()))
                .category(Pet.NamedEntity.builder().id(category.getId()).name(category.getName()).build())
                .build();

        //Trigger Create Pet API and Validate Response
        PetResponse petResp = petClient.createPet(pet);
        petAssertHelper.assertPetCreation(petResp, softAssert);

        //Trigger Get Pet API and Validate Response
        Pet createdPet = petResp.getResponse().as(Pet.class);
        PetResponse getPetResp = petClient.getPetByIdWithRetrial(createdPet.getId());
        petAssertHelper.assertPetCreation(getPetResp.toBuilder().request(createdPet).build(), softAssert);

        //Trigger Update Pet API and Validate Response
        Pet updatedPet = pet.toBuilder().status("sold").build();
        PetResponse updatedPetResp = petClient.updatePet(updatedPet);
        petAssertHelper.assertPetCreation(updatedPetResp, softAssert);

        //Trigger Delete Pet API and Validate Response
        PetResponse deletePetResp = petClient.deletePet(updatedPet.getId());
        petAssertHelper.assertPetDeletion(deletePetResp, updatedPet.getId().toString(), softAssert);

        //Trigger Get Pet API for Deleted Pet and Validate 404 Response
        PetResponse getPetResp1 = petClient.getPetById(updatedPet.getId());
        petAssertHelper.assertGetPetNotFound(getPetResp1, updatedPet.getId(), softAssert);

        softAssert.assertAll();
    }
}
