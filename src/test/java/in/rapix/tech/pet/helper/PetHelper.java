package in.rapix.tech.pet.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.rapix.tech.pet.entity.Pet;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class PetHelper {
    private static ObjectMapper objectMapper= new ObjectMapper();
    public static boolean arePhotoUrlsEqual(List<String> urls1, List<String> urls2) {
        if (urls1 == null && urls2 == null) return true;
        if (urls1 == null || urls2 == null) return false;
        if (urls1.size() != urls2.size()) return false;
        for (int i = 0; i < urls1.size(); i++) {
            if (!Objects.equals(urls1.get(i), urls2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean areTagsEqual(List<Pet.NamedEntity> tags1, List<Pet.NamedEntity> tags2) {
        if (tags1 == null && tags2 == null) return true;
        if (tags1 == null || tags2 == null) return false;
        if (tags1.size() != tags2.size()) return false;
        for (int i = 0; i < tags1.size(); i++) {
            Pet.NamedEntity tag1 = tags1.get(i);
            Pet.NamedEntity tag2 = tags2.get(i);
            if (tag1 == null && tag2 == null) continue;
            if (tag1 == null || tag2 == null) return false;
            try {
                var id1 = tag1.getId();
                var name1 = tag1.getName();
                var id2 = tag2.getId();
                var name2 = tag2.getName();
                if (!Objects.equals(id1, id2) ||
                        !Objects.equals(name1, name2)) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public static boolean areCategoryEqual(Pet.NamedEntity cat1, Pet.NamedEntity cat2) {
        if (cat1 == null && cat2 == null) return true;
        if (cat1 == null || cat2 == null) return false;
        try {
            var id1 = cat1.getId();
            var name1 = cat1.getName();
            var id2 = cat2.getId();
            var name2 = cat2.getName();
            return Objects.equals(id1, id2) &&
                    Objects.equals(name1, name2);
        } catch (Exception e) {
            return false;
        }
    }

    public static Pet.NamedEntity getRandomDataFromJsonFile(String fileName) {
        try {
            String path = "src/test/resources/pet/data/"+fileName;
            String content = Files.readString(Path.of(path));
            ObjectMapper mapper = new ObjectMapper();
            List<?> list = mapper.readValue(content, List.class);
            if (list.isEmpty()) return null;
            int randomIndex = new Random().nextInt(list.size());
            return objectMapper.convertValue(list.get(randomIndex), Pet.NamedEntity.class);
        } catch (Exception e) {
            return null;
        }
    }
}
