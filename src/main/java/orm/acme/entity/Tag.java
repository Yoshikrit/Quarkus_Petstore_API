package orm.acme.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Id;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class Tag {
    @Id
    @JsonProperty("tag")
    @NotNull
    @Schema(required=true,example="0")
    private int id;
    @JsonProperty("name")
    @NotNull
    @Schema(required=true,example="yak")
    private String name;

    public Tag(){}

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
