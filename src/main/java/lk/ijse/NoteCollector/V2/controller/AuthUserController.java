package lk.ijse.NoteCollector.V2.controller;

import lk.ijse.NoteCollector.V2.dto.Impl.UserDTO;
import lk.ijse.NoteCollector.V2.entity.Role;
import lk.ijse.NoteCollector.V2.exeption.DataPersistExeption;
import lk.ijse.NoteCollector.V2.secure.JWTAuthResponse;
import lk.ijse.NoteCollector.V2.secure.SignIn;
import lk.ijse.NoteCollector.V2.service.AuthService;
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
    private final AuthService authService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> saveUser(
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("role")String role,
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
            buildUserDTO.setRole(Role.valueOf(role));
            buildUserDTO.setProfilePic(picToBase64);

            //userService.saveUser(buildUserDTO);
            /*authService.signUp(buildUserDTO);*/
            /*return new ResponseEntity<>(HttpStatus.CREATED);*/
            return ResponseEntity.ok(authService.signUp(buildUserDTO));

        }catch (DataPersistExeption e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping(value = "signin",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignIn signIn){
        return ResponseEntity.ok(authService.signIn(signIn));
    }
    @PostMapping("refresh")
    public ResponseEntity<JWTAuthResponse> RefreshToken(@RequestParam ("existingToken") String existingToken) {
        return ResponseEntity.ok(authService.refreshToken(existingToken));
    }
}
