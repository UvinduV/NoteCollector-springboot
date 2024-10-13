package lk.ijse.NoteCollector.V2.service;

import lk.ijse.NoteCollector.V2.dto.Impl.NoteDTO;
import lk.ijse.NoteCollector.V2.dto.NoteStatus;

import java.util.List;

public interface NoteService {
    void saveNote(NoteDTO noteDTO);
    List<NoteDTO>getAllNotes();
    NoteStatus getNote(String noteId);
    void deleteNote(String noteId);
    void updateNote(String noteId, NoteDTO noteDTO);

}

