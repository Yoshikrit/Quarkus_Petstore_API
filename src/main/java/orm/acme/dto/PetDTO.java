package orm.acme.dto;

import orm.acme.entity.Pet;
import orm.acme.status.PetStatus;


public class PetDTO {
    private final Long id;
    private final String category;
    private final String name;
    private final String photoUrls;
    private final String tags;
    private final PetStatus status;

    public PetDTO(Pet pet) {
        this.id = pet.getId();
        this.category = pet.getCategory();
        this.name = pet.getName();
        this.photoUrls = pet.getPhotoUrl();
        this.tags = pet.getTag();
        this.status = pet.getStatus();
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrls() {
        return photoUrls;
    }

    public String getTags() {
        return tags;
    }

    public PetStatus getStatus() {
        return status;
    }
}
