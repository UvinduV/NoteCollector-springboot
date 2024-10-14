package lk.ijse.NoteCollector.V2.service.Impl;

import lk.ijse.NoteCollector.V2.dto.Impl.UserDTO;
import lk.ijse.NoteCollector.V2.secure.JWTAuthResponse;
import lk.ijse.NoteCollector.V2.secure.SignIn;
import lk.ijse.NoteCollector.V2.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        return null;
    }

    @Override
    public JWTAuthResponse signUp(UserDTO userDTO) {
        return null;
    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        return null;
    }
}
