package in.rapix.tech.pet.dataprovider;

import org.testng.annotations.DataProvider;
import java.util.Iterator;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PetTestDataProvider {
    @DataProvider(name = "petCreationNegativeDP")
    public Object[][] petCreationNegativeDP() {
        return new Object[][]{
            {"Test Create Pet with invalid parameter should fail", null, "", "", ""},
            {"Test Create Pet with invalid id(negative) should fail", -System.currentTimeMillis(), "PetName1", "PhotoURL1", "available"},
            {"Test Create Pet with Empty Name should fail", System.currentTimeMillis(), "", "PhotoURL2", "available"},
            {"Test Create Pet with Empty Photo Url should fail", System.currentTimeMillis(), "PetName3", "", "available"},
            {"Test Create Pet without Name should fail, as Name is mandatory field", System.currentTimeMillis(), null, "PhotoURL4", "available"},
            {"Test Create Pet without Photo Url should fail, as Photo Url is mandatory field", -System.currentTimeMillis(), "PetName5", null, "available"},
            {"Test Create Pet with unknow status should fail", -System.currentTimeMillis(), "PetName5", null, "unknown"}
        };
    }

    @DataProvider(name = "getPetNegativeDP")
    public Object[][] getPetNegativeDP() {
        return new Object[][]{
                {"Test Get Pet with invalid id - non-existent id should fail", new Random().nextLong(), 404, 1, "error","Pet not found"},
                {"Test Get Pet with invalid id - negative should fail", -1L, 404, 1, "error","Pet not found"},
                {"Test Get Pet with invalid id - very large number should fail", Long.MAX_VALUE, 404, 1, "error","Pet not found"},
                {"Test Get Pet with invalid id - already deleted should fail", 1760541887026L, 404, 1, "error","Pet not found"}
        };
    }

    @DataProvider(name = "deletePetNegativeDP")
    public Object[][] deletePetNegativeDP() {
        return new Object[][]{
                {"Test Delete Pet with invalid/non-existing id - should fail", new Random().nextLong(), 404},
                {"Test Delete Pet with invalid id - negative should fail", -1L, 404},
                {"Test Delete Pet with id already deleted - string should fail", 1760541887026L, 404}
        };
    }

    @DataProvider(name = "updatePetNegativeDP")
    public Object[][] updatePetNegativeDP() {
        return new Object[][]{
                {"Test Update Pet with invalid id - should fail", new Random().nextLong(), "PetName1", "PhotoURL1", "sold", 404},
                {"Test Update Pet with invalid id - negative should fail", -1L, "PetName2", "PhotoURL2", "sold", 404},
                {"Test Update Pet with id already deleted - string should fail", 1760541887026L, "PetName3", "PhotoURL3", "sold", 404},
                {"Test Update Pet with exiting id but missing name filed", 1760541887026L, null, "PhotoURL4", "sold", 400},
                {"Test Update Pet with exiting id but missing photo url field", 1760541887026L, "PetName5", null, "sold", 400}
        };
    }

    @DataProvider(name = "findByStatusNegativeDP")
    public Object[][] findByStatusNegativeDP() {
        return new Object[][]{
                {"Test Find Status of Pet with invalid status - should fail", "InvalidStatus", 400},
                {"Test Find Status of Pet with omit status", null, 400},
                {"Test Find Status of Pet with Special Character status", "@#$@!%", 400}
        };
    }

    @DataProvider(name = "statusProvider")
    public Iterator<Object[]> statusProvider() {
        List<Object[]> data = Arrays.asList(new Object[][]{
            {"available"},
            {"pending"},
            {"sold"},
            {"invalid"} // Negative/edge case
        });
        return data.iterator();
    }
}

