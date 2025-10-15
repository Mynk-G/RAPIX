package in.rapix.tech.pet.tests;

import in.rapix.tech.pet.client.PetClient;
import in.rapix.tech.pet.dataprovider.PetTestDataProvider;
import in.rapix.tech.pet.entity.Pet;
import in.rapix.tech.pet.entity.PetErrorResponse;
import in.rapix.tech.pet.entity.PetResponse;
import in.rapix.tech.pet.helper.PetHelper;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.Collections;

public class PetApiNegativeTests {

    private PetClient petClient;

    @BeforeClass
    public void setup() {
        petClient = new PetClient();
    }

    @Test(dataProvider = "petCreationNegativeDP", dataProviderClass = PetTestDataProvider.class)
    public void testCreatePetNegative(String testName, Long id, String name, String photoURL, String status) {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(testName));

        Pet.NamedEntity category = PetHelper.getRandomDataFromJsonFile("category-data.json");
        Assert.assertNotNull(category);
        Pet.NamedEntity tag = PetHelper.getRandomDataFromJsonFile("tag-data.json");
        Assert.assertNotNull(tag);

        Pet pet = Pet.builder()
                .id(id)
                .name(name)
                .status(status)
                .photoUrls(Collections.singletonList(photoURL))
                .tags(Collections.singletonList(Pet.NamedEntity.builder().id(tag.getId()).name(tag.getName()).build()))
                .category(Pet.NamedEntity.builder().id(category.getId()).name(category.getName()).build())
                .build();

        PetResponse petResp = petClient.createPet(pet);

        Assert.assertEquals(petResp.getResponse().getStatusCode(), 400, "Status code should be 400 for invalid pet creation");
    }

    @Test(dataProvider = "getPetNegativeDP", dataProviderClass = PetTestDataProvider.class)
    public void testGetPetByIdNegative(String testName, Long id, int expectedStatusCode, int code, String type, String message) {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(testName));

        SoftAssert softAssert = new SoftAssert();

        PetResponse getPetResp = petClient.getPetById(id);

        Assert.assertEquals(getPetResp.getResponse().getStatusCode(), expectedStatusCode, "Status code should be "+expectedStatusCode);
        PetErrorResponse getRespErr = getPetResp.getResponse().as(PetErrorResponse.class);
        softAssert.assertEquals(getRespErr.getCode(), code, "Response code should match");
        softAssert.assertEquals(getRespErr.getType(), type, "Response type should match");
        softAssert.assertEquals(getRespErr.getMessage(), message, "Response message should match");
        softAssert.assertAll();
    }

    @Test(dataProvider = "updatePetNegativeDP", dataProviderClass = PetTestDataProvider.class)
    public void testUpdatePetNegative(String testName, Long id, String name, String photoURL, String status, int expectedStatusCode) throws Exception {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(testName));

        Pet.NamedEntity category = PetHelper.getRandomDataFromJsonFile("category-data.json");
        Assert.assertNotNull(category);
        Pet.NamedEntity tag = PetHelper.getRandomDataFromJsonFile("tag-data.json");
        Assert.assertNotNull(tag);

        Pet pet = Pet.builder()
                .id(id)
                .name(name)
                .status(status)
                .photoUrls(Collections.singletonList(photoURL))
                .tags(Collections.singletonList(Pet.NamedEntity.builder().id(tag.getId()).name(tag.getName()).build()))
                .category(Pet.NamedEntity.builder().id(category.getId()).name(category.getName()).build())
                .build();

        PetResponse updatePetResp = petClient.updatePet(pet);

        Assert.assertEquals(updatePetResp.getResponse().getStatusCode(), expectedStatusCode, "Status code should be "+expectedStatusCode);
    }

    @Test(dataProvider = "deletePetNegativeDP", dataProviderClass = PetTestDataProvider.class)
    public void testDeletePetNegative(String testName, Long id, int expectedStatusCode) {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(testName));
        PetResponse getPetResp = petClient.deletePet(id);
        Assert.assertEquals(getPetResp.getResponse().getStatusCode(), expectedStatusCode, "Status code should be "+expectedStatusCode);
    }

    @Test(dataProvider = "findByStatusNegativeDP", dataProviderClass = PetTestDataProvider.class)
    public void testFindPetsByStatusNegative(String testName, String status, int expectedStatusCode) {
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(testName));
        PetResponse getPetResp = petClient.findByStatusPet(status);
        Assert.assertEquals(getPetResp.getResponse().getStatusCode(), expectedStatusCode, "Status code should be "+expectedStatusCode);
    }
}
