package lk.ijse.NoteCollector.V2.service;

import lk.ijse.NoteCollector.V2.dto.Impl.UserDTO;
import lk.ijse.NoteCollector.V2.dto.UserStatus;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserStatus getUser(String userId);
    void deleteUser(String userId);
    void updateUser(String userId,UserDTO userDTO);

    UserDetailsService userDetailsService();
}
