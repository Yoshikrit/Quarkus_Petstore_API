package orm.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;
import orm.acme.status.PetStatus;

@Entity
@Table(name = "Pet")
public class Pet extends PanacheEntityBase {
    @Id
    @NotNull
    @Column(name = "Pet_Code")
    private Long id;

    @NotNull
    @Column(name = "Pet_Category")
    private String category;

    @NotNull
    @Column(name = "Pet_Name")
    private String name;

    @NotNull
    @Column(name = "Pet_Photo")
    private String photoUrl;

    @NotNull
    @Column(name = "Pet_Tag")
    private String tag;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "Pet_Status")
    private PetStatus status;

    public Pet(){}

    public Pet(Long id, String category, String name, String photoUrl, String tag, PetStatus status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrl = photoUrl;
        this.tag = tag;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public PetStatus getStatus() {
        return status;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }
}
