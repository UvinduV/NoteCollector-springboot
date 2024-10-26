package lk.ijse.NoteCollector.V2.dto.Impl;

import lk.ijse.NoteCollector.V2.dto.UserStatus;

import lk.ijse.NoteCollector.V2.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO implements UserStatus {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private String profilePic;
    private List<NoteDTO>notes;
}
