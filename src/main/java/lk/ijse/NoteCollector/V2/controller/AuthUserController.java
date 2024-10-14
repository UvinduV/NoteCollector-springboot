package lk.ijse.NoteCollector.V2.controller;

import lk.ijse.NoteCollector.V2.dto.Impl.UserDTO;
import lk.ijse.NoteCollector.V2.exeption.DataPersistExeption;
import lk.ijse.NoteCollector.V2.secure.JWTAuthResponse;
import lk.ijse.NoteCollector.V2.secure.SignIn;
import lk.ijse.NoteCollector.V2.service.UserService;
import lk.ijse.NoteCollector.V2.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("api/v1/auth/")
@RestController
@RequiredArgsConstructor
public class AuthUserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> saveUser(
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePic") MultipartFile profilePic
    ){

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
            buildUserDTO.setPassword(passwordEncoder.encode(password));
            buildUserDTO.setProfilePic(picToBase64);

            userService.saveUser(buildUserDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (DataPersistExeption e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    /*@PostMapping("signin")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignIn signIn){

    }
    @PostMapping("refresh")
    public ResponseEntity<JWTAuthResponse> signIn(@RequestParam ("refreshToken") String refreshToken) {

    }*/
}
