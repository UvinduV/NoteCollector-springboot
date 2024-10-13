package lk.ijse.NoteCollector.V2.controller;

import lk.ijse.NoteCollector.V2.customStatusCode.SelectedUserAndNoteErrorStatus;
import lk.ijse.NoteCollector.V2.dto.Impl.UserDTO;
import lk.ijse.NoteCollector.V2.dto.UserStatus;
import lk.ijse.NoteCollector.V2.exeption.DataPersistExeption;
import lk.ijse.NoteCollector.V2.exeption.UserNotFoundExeption;
import lk.ijse.NoteCollector.V2.service.UserService;
import lk.ijse.NoteCollector.V2.util.AppUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUser(
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePic") MultipartFile profilePic
    ){
        System.out.println("RAW profile Pic "+profilePic);

        //generate user id
        String userId = AppUtil.generateUserId();
        //profile pic convert to base 64
        String picToBase64="";
        try {
            byte[] bytesProPic = profilePic.getBytes();
            picToBase64=AppUtil.profilePicToBase64(bytesProPic);

            //build the object
            var buildUserDTO=new UserDTO();
            buildUserDTO.setUserId(userId);
            buildUserDTO.setFirstName(firstName);
            buildUserDTO.setLastName(lastName);
            buildUserDTO.setEmail(email);
            buildUserDTO.setPassword(password);
            buildUserDTO.setProfilePic(picToBase64);

            userService.saveUser(buildUserDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (DataPersistExeption e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //build the object
       /* var buildUserDTO=new UserDTO();
        buildUserDTO.setUserId(userId);
        buildUserDTO.setFirstName(firstName);
        buildUserDTO.setLastName(lastName);
        buildUserDTO.setEmail(email);
        buildUserDTO.setPassword(password);
        buildUserDTO.setProfilePic(picToBase64);

        return userService.saveUser(buildUserDTO);*/
        /*return buildUserDTO;*/
    }
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserStatus getSelectedUser(@PathVariable ("userId") String userId){
        /*if(userId.isEmpty() || userId ==null){
            return new SelectedUserErrorStatus(1,"User ID is not valid");
        }*/
        String regexForUserID = "^USER[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(userId);

        if(!regexMatcher.matches()){
            return new SelectedUserAndNoteErrorStatus(1,"User ID is not valid");
        }
        return userService.getUser(userId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId){
        String regexForUserID = "^USER[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPattern = Pattern.compile(regexForUserID);
        var regexMatcher = regexPattern.matcher(userId);
        try {
            if(!regexMatcher.matches()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundExeption e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

        /*if(!regexMatcher.matches()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(userId);*/
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }
    @PutMapping(value = "/{userId}" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void UpdateUser(
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePic") MultipartFile profilePic,
            @PathVariable("userId") String userId){

        String picToBase64="";
        try {
            byte[] bytesProPic = profilePic.getBytes();
            picToBase64=AppUtil.profilePicToBase64(bytesProPic);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var buildUserDTO=new UserDTO();
        buildUserDTO.setUserId(userId);
        buildUserDTO.setFirstName(firstName);
        buildUserDTO.setLastName(lastName);
        buildUserDTO.setEmail(email);
        buildUserDTO.setPassword(password);
        buildUserDTO.setProfilePic(picToBase64);

        userService.updateUser(userId,buildUserDTO);

    }
}
