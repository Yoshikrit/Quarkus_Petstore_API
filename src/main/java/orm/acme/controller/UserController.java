package orm.acme.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import orm.acme.entity.User;
import orm.acme.service.UserService;
import orm.acme.updatemodel.UserUpdateModel;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Path("api/v1/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "User", description = "Operation about user")
public class UserController {
    private final UserService userService = new UserService();

    @POST
    @Transactional
    @Operation(
            operationId = "createUserWithList",
            summary = "Create list if users with given input array",
            description = "create list if users with given input array"
    )
    @APIResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("/user/createUserWithList")
    public Response createUserWithList(List<User> users) {
        boolean checkUserNotSame = userService.checkListUserNotSame(users);
        if(checkUserNotSame){
            userService.saveUsers(users);
            return Response.status(Response.Status.CREATED).entity(userService.getUsersDTO(users)).build();
        }
        else{
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Operation(
            operationId = "getByUserName",
            summary = "Get user by user name",
            description = "get user by username"
    )
    @APIResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("{username}")
    public Response getUserByUserName(
            @Parameter(description = "The name that needs to be fetched. Use user1 for testing.", required = true)
            @PathParam("username") String username){
        Optional<User> getUserByUsername = userService.findUserByUserName(username);
        if(getUserByUsername.isPresent()){
            User dbUser = getUserByUsername.get();
            return Response.ok(dbUser).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
        }
    }

    @PUT
    @Operation(
            operationId = "updateUserByUsername",
            summary = "Updated user",
            description = "update user from username"
    )
    @Path("/{username}")
    @Transactional
    public Response updateUserByUserName(
            @Parameter(description = "name that need to be updated"
                    , required = true)
            @PathParam("username") String username,
            UserUpdateModel userUpdateModel) {
        userService.updateUserByUserName(username, userUpdateModel);
        return Response.created(URI.create("/user/" + username)).build();

    }

    @DELETE
    @Operation(
            operationId = "deleteUser",
            summary = "Delete user",
            description = "delete user"
    )
    @APIResponse(
            responseCode = "204",
            description = "User deleted",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid username supplied",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("{username}")
    @Transactional
    public Response deleteUserByUserName(
            @Parameter(description = "The name that needs to be deleted", required = true)
            @PathParam("username") String username){
        Optional<User> findUser = userService.findUserByUserName(username);
        if(findUser.isPresent()){
            userService.deleteUserByUserName(username);
            return Response.noContent()
                    .entity("Delete user : " + username).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found").build();
        }
    }

    @GET
    @Operation(
            operationId = "loginUser",
            summary = "Logs user into the system",
            description = "log user into system"
    )
    @APIResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid username/password supplied",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("user/login")
    public Response loginUser(
            @Parameter(description = "The user name for login", required = true)
            @QueryParam("username") String username,
            @Parameter(description = "The password for login in clear text", required = true)
            @QueryParam("password") String password) {

        User user = new User();
        Optional<User> userDB = userService.findUserByUserName(username);
        //check if user exist
        if(userDB.isPresent()){
            user = userDB.get();
        }
        else{
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid username supplied").build();
        }
        //check if password true or not
        if(user.getPassword().equals(password)){
            return Response.status(Response.Status.OK)
                    .entity("\"" + user.getUsername() + "\" is log in").build();
        }
        else{
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid password supplied").build();
        }
    }

    @GET
    @Operation(
            operationId = "logoutUser",
            summary = "Logs out current logged in user session",
            description = "Logs out current logged in user session"
    )
    @APIResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("user/logout")
    public Response logoutUser() {
        return Response.status(Response.Status.OK)
                .entity("Log out").build();
    }

    @POST
    @Transactional
    @Operation(
            operationId = "createUser",
            summary = "Create user",
            description = "Create user object"
    )
    @APIResponse(
            responseCode = "200",
            description = "successful operation",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Path("/user")
    public Response createUser(User user){
        //check userId and username
        boolean checkUser = userService.checkUserNotSame(user);

        if(checkUser){
            userService.saveUser(user);
            return Response.status(Response.Status.CREATED).entity(userService.getUserDTO(user)).build();
        }
        else{
            return Response.status(Response.Status.CONFLICT).build();
        }
    }


}
