package orm.acme.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import orm.acme.entity.Pet;
import orm.acme.service.PetService;
import orm.acme.status.PetStatus;
import orm.acme.updatemodel.PetUpdateModel;
import orm.acme.updatemodel.PhotoUpdateModel;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("api/v1/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Pet", description = "Everything about your Pets")
public class PetController  {
    private final PetService petService = new PetService();

    @POST
    @Transactional
    @Operation(
            operationId = "uploadImage",
            summary = "Uploads an image",
            description = "uploads an image"
    )
    @APIResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("/pet/{petId}/uploadImage")
    public Response uploadImage(
            @Parameter(description = "ID of pet to update", required = true)
            @PathParam("petId") long petId,
            @Parameter(description = "file to upload")
            @QueryParam("file") String url)
            throws JsonProcessingException {
        Optional<Pet> findPet = petService.findPetById(petId);
        if(findPet.isPresent()){
            Pet pet = findPet.get();
            petService.uploadPicture(pet, url);
            petService.savePet(pet);
            PhotoUpdateModel photoInfo = new PhotoUpdateModel(pet.getId(), petService.getPictureListFromPet(pet));
            return Response.status(Response.Status.OK).entity(photoInfo).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).entity("Pet not found").build();
        }
    }

    @POST
    @Transactional
    @Operation(
            operationId = "createPet",
            summary = "Add a new pet to the store",
            description = "add a new pet to the store"
    )
    @APIResponse(
            responseCode = "201",
            description = "successful operation",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("/pet")
    public Response createPet(PetUpdateModel petUpdateModel){
        Optional<Pet> findPet= petService.findPetById(petUpdateModel.getId());

        //check if status is true format
        boolean trueStatus = petService.checkEnumState(petUpdateModel);

        try {
            //if not exist else conflict
            if(findPet.isEmpty() && trueStatus){
                Pet pet = petService.getNewFormatPet(petUpdateModel);
                petService.savePet(pet);
                return Response.status(Response.Status.CREATED).entity(petService.getPetDTO(pet)).build();
            }
            else if(findPet.isPresent() && trueStatus){
                return Response.status(Response.Status.CONFLICT).build();
            }
            else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Wrong Format").build();
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(405).entity("Validation exception").build();
        }
    }

    @PUT
    @Operation(
            operationId = "updatePet",
            summary = "Updated an existing pet",
            description = "update an existing pet"
    )
    @Path("/pet")
    @Transactional
    public Response updatePet(PetUpdateModel petUpdateModel) {
        Optional<Pet> findPet= petService.findPetById(petUpdateModel.getId());

        //check if status is true format
        boolean trueStatus = petService.checkEnumState(petUpdateModel);

        try {
            //if not exist else conflict
            if(findPet.isPresent() && trueStatus){
                Pet pet = petService.getNewFormatPet(petUpdateModel);
                petService.updatePet(pet);
                return Response.status(Response.Status.CREATED).entity(petService.getPetDTO(pet)).build();
            }
            else if(findPet.isEmpty()){
                return Response.status(Response.Status.NOT_FOUND).entity("Pet not found").build();
            }
            else{
                return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Input").build();
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return Response.status(405).entity("Validation exception").build();
        }
    }

    @GET
    @Operation(
            operationId = "getPetListByStatus",
            summary = "Finds pets by status",
            description = "Multiple status values can be provided with comma separated strings"
    )
    @APIResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid status supplied",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("/pet/findByStatus")
    public Response getPetListByStatus(
            @Parameter(description = "Status values that need to be considered for filter\n" +
                    "\n" +
                    "Available values : available, pending, sold", required = true)
            @QueryParam("status") PetStatus petStatus){
        List<Pet> pets = petService.getPetListFromStatus(petStatus);
        return Response.status(Response.Status.OK).entity(petService.getPetsDTO(pets)).build();
    }

    @GET
    @Operation(
            operationId = "getPetById",
            summary = "Find pet by ID",
            description = "Returns a single pet"
    )
    @APIResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid ID supplied",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "Pet not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("/pet/{petId}")
    public Response getPetByID(
            @Parameter(description = "ID of pet to return", required = true)
            @PathParam("petId") long petId){
        Optional<Pet> findPet = petService.findPetById(petId);
        if(findPet.isPresent()){
            Pet pet = findPet.get();
            return Response.ok(petService.getPetDTO(pet)).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).entity("Pet not found").build();
        }
    }

    @POST
    @Transactional
    @Operation(
            operationId = "updatePetByForm",
            summary = "Updates a pet in the store with form data",
            description = "updates a pet in the store with form data"
    )
    @APIResponse(
            responseCode = "405",
            description = "Invalid input",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("/pet/{petId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePetByForm(
            @Parameter(description = "ID of pet that needs to be updated", required = true)
            @PathParam("petId") long petId,
            @Parameter(description = "Updated name of the pet")
            @QueryParam("name") String name,
            @Parameter(description = "Updated status of the pet")
            @QueryParam("status") PetStatus status){
        Optional<Pet> findPet = petService.findPetById(petId);
        if(findPet.isPresent()){
            Pet dbPet = findPet.get();
            petService.updatePetByForm(dbPet, name, status);
            return Response.created(URI.create("/pet/" + petId)).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).entity("Pet not found").build();
        }
    }

    @DELETE
    @Operation(
            operationId = "deletePet",
            summary = "Deletes a pet",
            description = "deletes a pet"
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid ID supplied",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "Pet not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("/pet/{petId}")
    @Transactional
    public Response deletePetById(
            @HeaderParam("api_key") String api_key,
            @Parameter(description = "Pet id to delete", required = true)
            @PathParam("petId") long petId) {
        Optional<Pet> findPet = petService.findPetById(petId);
        if (findPet.isPresent()) {
            petService.deletePetById(petId);
            return Response.noContent().entity("Delete Pet").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Pet not found").build();
        }
    }
}
