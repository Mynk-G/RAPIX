package in.rapix.tech.pet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Pet {
    private Long id;
    private String name;
    private String status;
    private List<String> photoUrls;
    private List<NamedEntity> tags;
    private NamedEntity category;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    public static class NamedEntity {
        private Long id;
        private String name;
    }
}
