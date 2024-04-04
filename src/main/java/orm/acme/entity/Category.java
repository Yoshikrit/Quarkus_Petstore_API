package orm.acme.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Id;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class Category {
    @Id
    @JsonProperty("id")
    @NotNull
    @Schema(required=true,example="0")
    private Long id;

    @JsonProperty("name")
    @NotNull
    @Schema(required=true,example="dog")
    private String name;

    public Category(){}

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
