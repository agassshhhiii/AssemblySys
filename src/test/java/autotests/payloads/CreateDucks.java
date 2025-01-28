package autotests.payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(fluent = true)

public class CreateDucks {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("color")
    private String color;
    @JsonProperty("height")
    private Double height;
    @JsonProperty("material")
    private String material;
    @JsonProperty("sound")
    private String sound;
    @JsonProperty("wingsState")
    private WingState wingsState;
}
