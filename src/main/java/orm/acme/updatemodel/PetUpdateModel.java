package orm.acme.updatemodel;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Id;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import orm.acme.entity.Category;
import orm.acme.entity.Photo;
import orm.acme.entity.Tag;
import orm.acme.status.PetStatus;

import java.util.List;

public class PetUpdateModel {
    @Id
    @NotNull
    @Schema(required=true,example="0")
    private Long id;

    @NotNull
    private Category category;

    @NotNull
    @Schema(required=true,example="doggie")
    private String name;

    @NotNull
    private List<Photo> photoUrls;

    @NotNull
    private List<Tag> tags;

    @NotNull
    @Schema(required=true,example="AVAILABLE")
    private PetStatus status;

    public PetUpdateModel(){}

    public PetUpdateModel(Long id, Category category, String name, List<Photo> photoUrls, List<Tag> tags, PetStatus status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<Photo> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public PetStatus getStatus() {
        return status;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }
}
