package lk.ijse.NoteCollector.V2.service.Impl;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lk.ijse.NoteCollector.V2.customStatusCode.SelectedUserAndNoteErrorStatus;
import lk.ijse.NoteCollector.V2.dao.UserDao;
import lk.ijse.NoteCollector.V2.dto.Impl.UserDTO;
import lk.ijse.NoteCollector.V2.dto.UserStatus;
import lk.ijse.NoteCollector.V2.entity.Impl.UserEntity;
import lk.ijse.NoteCollector.V2.exeption.DataPersistExeption;
import lk.ijse.NoteCollector.V2.exeption.UserNotFoundExeption;
import lk.ijse.NoteCollector.V2.service.UserService;
import lk.ijse.NoteCollector.V2.util.Mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveUser(UserDTO userDTO) {
        /*mapping.toUserDTO(userDao.save(mapping.toUserEntity(userDTO)));*/
        UserEntity savedUser =
                userDao.save(mapping.toUserEntity(userDTO));
        if (savedUser == null) {
            throw new DataPersistExeption("User not saved");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity>allUsers=userDao.findAll();
        return mapping.asUserDtoLIst(allUsers);
    }

    @Override
    public UserStatus getUser(String userId) {
        /*UserEntity selectedUser = userDao.getReferenceById(userId);
        return mapping.toUserDTO(selectedUser);*/
        if(userDao.existsById(userId)){
            UserEntity selectedUser = userDao.getReferenceById(userId);
            return mapping.toUserDTO(selectedUser);
        }else {
            return new SelectedUserAndNoteErrorStatus(2, "User with id " + userId + " not found");
        }
    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> existedUser=userDao.findById(userId); //
        if (!existedUser.isPresent()) { //user is not present
            throw new UserNotFoundExeption("User with id " + userId + " not found");
        }else {
            userDao.deleteById(userId);
        }
        /*userDao.deleteById(userId);*/
    }

    @Override
    public void updateUser(String userId, UserDTO userDTO) {
        Optional<UserEntity>tempUser=userDao.findById(userId);
        if (tempUser.isPresent()) {
            tempUser.get().setFirstName(userDTO.getFirstName());
            tempUser.get().setLastName(userDTO.getLastName());
            tempUser.get().setEmail(userDTO.getEmail());
            tempUser.get().setPassword(userDTO.getPassword());
            tempUser.get().setProfilePic(userDTO.getProfilePic());
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return userName ->
                userDao.findByEmail(userName)
                        .orElseThrow(()-> new UserNotFoundExeption("User Not Found"));
    }




}
