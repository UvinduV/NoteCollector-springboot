package lk.ijse.NoteCollector.V2.customStatusCode;

import lk.ijse.NoteCollector.V2.dto.NoteStatus;
import lk.ijse.NoteCollector.V2.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedUserAndNoteErrorStatus implements UserStatus, NoteStatus {
    private int statusCode;
    private String statusMassage;
}
