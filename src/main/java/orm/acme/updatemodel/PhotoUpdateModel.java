package orm.acme.updatemodel;

import orm.acme.entity.Photo;

import java.util.List;

public class PhotoUpdateModel {
    private Long id;
    private List<Photo> url;

    public PhotoUpdateModel() {
    }

    public PhotoUpdateModel(Long id, List<Photo> url) {
        this.id = id;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Photo> getUrl() {
        return url;
    }

    public void setUrl(List<Photo> url) {
        this.url = url;
    }
}
