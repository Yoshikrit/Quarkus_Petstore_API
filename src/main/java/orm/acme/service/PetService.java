package orm.acme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hibernate.service.spi.ServiceException;
import orm.acme.dto.PetDTO;
import orm.acme.entity.Pet;
import orm.acme.entity.Photo;
import orm.acme.status.PetStatus;
import orm.acme.updatemodel.PetUpdateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PetService {
    public Optional<Pet> findPetById(Long id) {
        return Pet.find("id", id)
                .singleResultOptional();
    }

    public boolean checkEnumState(PetUpdateModel petUpdateModel) {
        boolean trueStatus = false;
        PetStatus enumValue = PetStatus.valueOf(petUpdateModel.getStatus().toString());
        if (enumValue == PetStatus.AVAILABLE || enumValue == PetStatus.PENDING || enumValue == PetStatus.SOLD) {
            trueStatus = true;
        }
        return trueStatus;
    }

    public PetDTO getPetDTO(Pet pet) {
        return new PetDTO(pet);
    }

    public List<PetDTO> getPetsDTO(List<Pet> pets) {
        List<PetDTO> petsDTO = new ArrayList<>();
        for (Pet pet : pets) {
            petsDTO.add(getPetDTO(pet));
        }
        return new ArrayList<>(petsDTO);
    }

    public List<Pet> getPetListFromStatus(PetStatus status) {
        String pet_status = switch (status) {
            case AVAILABLE -> "AVAILABLE";
            case PENDING -> "PENDING";
            case SOLD -> "SOLD";
        };
        return Pet.list("SELECT c FROM Pet c WHERE c.status  = ?1 ORDER BY id " + "ASC", status);
    }

    public Pet getNewFormatPet(PetUpdateModel petUpdateModel) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String ctgyString = objectMapper.writeValueAsString(petUpdateModel.getCategory());
        String photoString = objectMapper.writeValueAsString(petUpdateModel.getPhotoUrls());
        String tagString = objectMapper.writeValueAsString(petUpdateModel.getTags());

        return new Pet(petUpdateModel.getId(), ctgyString, petUpdateModel.getName(), photoString, tagString, petUpdateModel.getStatus());
    }

    @Transactional
    public void updatePetByForm(Pet pet, String name, PetStatus status) {
        pet.setName(name);
        pet.setStatus(status);
        savePet(pet);
    }

    @Transactional
    public void updatePet(Pet pet) {
        //check if there is that username in database
        Optional<Pet> petDB = findPetById(pet.getId());
        if (petDB.isEmpty()) {
            throw new ServiceException(String.format("No Pet found for Id[%s]", pet.getId()));
        }
        Pet petUpdate = petDB.get();
        petUpdate.setCategory(pet.getCategory());
        petUpdate.setName(pet.getName());
        petUpdate.setPhotoUrl(pet.getPhotoUrl());
        petUpdate.setTag(pet.getTag());
        petUpdate.setStatus(pet.getStatus());
        savePet(petUpdate);
    }

    @Transactional
    public void uploadPicture(Pet pet, String url) throws JsonProcessingException {
        //convert photolist json back to list object
        ObjectMapper objectMapper = new ObjectMapper();
        String photoJSON = pet.getPhotoUrl();

        List<Photo> photoList = objectMapper.readValue(photoJSON, new TypeReference<List<Photo>>() {
        });

        //add new photo
        Photo new_photo = new Photo(url);
        photoList.add(new_photo);

        //convert list object back to json string
        String photoString = objectMapper.writeValueAsString(photoList);

        //change and add to database
        pet.setPhotoUrl(photoString);
    }

    //get photo list
    @Transactional

    public List<Photo> getPictureListFromPet(Pet pet)
            throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String photoJSON = pet.getPhotoUrl();

        return objectMapper.readValue(photoJSON, new TypeReference<List<Photo>>() {
        });
    }

    @Transactional
    public void savePet(Pet pet) {
        Pet.persist(pet);
    }

    @Transactional
    public void deletePetById(Long id) {
        Pet.delete("id", id);
    }
}
