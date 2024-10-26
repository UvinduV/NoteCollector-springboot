package lk.ijse.NoteCollector.V2.service.Impl;

import lk.ijse.NoteCollector.V2.dao.UserDao;
import lk.ijse.NoteCollector.V2.dto.Impl.UserDTO;
import lk.ijse.NoteCollector.V2.entity.Impl.UserEntity;
import lk.ijse.NoteCollector.V2.secure.JWTAuthResponse;
import lk.ijse.NoteCollector.V2.secure.SignIn;
import lk.ijse.NoteCollector.V2.service.AuthService;
import lk.ijse.NoteCollector.V2.service.JWTService;
import lk.ijse.NoteCollector.V2.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDao userDao;
    private final Mapping mapping;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(),signIn.getPassword()));
        var user=userDao.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var generateToken=jwtService.generateToken(user);
        return JWTAuthResponse.builder().token(generateToken).build();
    }

    @Override
    public JWTAuthResponse signUp(UserDTO userDTO) {
        //save user
       UserEntity saveUser= userDao.save(mapping.toUserEntity(userDTO));
       //generate token and return
       var generateToken= jwtService.generateToken(saveUser);
       return JWTAuthResponse.builder().token(generateToken).build();
    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        //extract user name
       var userName = jwtService.extractUserName(accessToken);
       //check the user availability
        var findUser = userDao.findByEmail(userName)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        var refreshToken = jwtService.refreshToken(findUser);
        return JWTAuthResponse.builder().token(refreshToken).build();
    }
}
