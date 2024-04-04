package orm.acme.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class Photo {
    @NotNull
    @Schema(required=true,example="AAA")
    private String name;

    public Photo(){}

    public Photo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
