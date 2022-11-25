package domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class People {

    private Long id;
    private String name;
    private String surname;

}
