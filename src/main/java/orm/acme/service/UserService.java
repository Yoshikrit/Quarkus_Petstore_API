package orm.acme.service;

import jakarta.transaction.Transactional;
import org.hibernate.service.spi.ServiceException;
import orm.acme.dto.UserDTO;
import orm.acme.entity.User;
import orm.acme.updatemodel.UserUpdateModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    public Optional<User> findUserByUserName(String username){
        return User.find("username", username)
                .singleResultOptional();
    }

    public Optional<User> findUserById(long id){
        return User.find("id", id)
                .singleResultOptional();
    }

    public List<UserDTO> getUsersDTO(List<User> users){
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User user : users){
            usersDTO.add(getUserDTO(user));
        }
        return new ArrayList<>(usersDTO);
    }

    public UserDTO getUserDTO(User user){
        return new UserDTO(user);
    }

    public Boolean checkListUserNotSame(List<User> users){
        for(User user : users){
            //check userId and username not same with user in database
            boolean notSameId, notSameUsername;
            Optional<User> findUserId = findUserById(user.getId());
            notSameId = findUserId.isEmpty();

            Optional<User> findUsername = findUserByUserName(user.getUsername());
            notSameUsername = findUsername.isEmpty();

            if(!(notSameId && notSameUsername)){
                return false;
            }
        }
        return true;
    }

    public Boolean checkUserNotSame(User user){
        boolean notSameId, notSameUsername;
        Optional<User> findUserId = findUserById(user.getId());notSameId = findUserId.isEmpty();

        Optional<User> findUsername = findUserByUserName(user.getUsername());
        notSameUsername = findUsername.isEmpty();

        return notSameId && notSameUsername;
    }

    @Transactional
    public void saveUsers(List<User> users){
        for(User user : users){
            User.persist(user);
        }
    }
    @Transactional
    public void saveUser(User user){
        User.persist(user);
    }

    @Transactional
    public void deleteUserByUserName(String username){
        User.delete("username", username);
    }

    //update

    @Transactional
    public void updateUserByUserName(String username, UserUpdateModel user){
        //check if there is that username in database
        Optional <User> userDB = findUserByUserName(username);
        if(userDB.isEmpty()){
            throw new ServiceException(String.format("No User found for username[%s]", username));
        }
        //check if new username is the same with other username
        Optional <User> usernameCheck = findUserByUserName(user.getUsername());
        if(usernameCheck.isPresent()){
            throw new ServiceException(String.format("Another User already has username[%s]", user.getUsername()));
        }
        User userUpdate = userDB.get();
        userUpdate.setUsername(user.getUsername());
        userUpdate.setFirstname(user.getFirstName());
        userUpdate.setLastname(user.getLastName());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setPassword(user.getPassword());
        userUpdate.setTelNo(user.getTelNo());
        userUpdate.setStatus(user.getStatus());
        User.persist(userUpdate);
    }
}
