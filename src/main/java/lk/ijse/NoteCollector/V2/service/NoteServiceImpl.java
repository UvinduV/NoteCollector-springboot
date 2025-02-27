package lk.ijse.NoteCollector.V2.service;

import jakarta.transaction.Transactional;
import lk.ijse.NoteCollector.V2.customStatusCode.SelectedUserAndNoteErrorStatus;
import lk.ijse.NoteCollector.V2.dao.NoteDao;
import lk.ijse.NoteCollector.V2.dto.Impl.NoteDTO;
import lk.ijse.NoteCollector.V2.dto.NoteStatus;
import lk.ijse.NoteCollector.V2.entity.Impl.NoteEntity;
import lk.ijse.NoteCollector.V2.exeption.DataPersistExeption;
import lk.ijse.NoteCollector.V2.exeption.NoteNotFoundExeption;
import lk.ijse.NoteCollector.V2.util.AppUtil;
import lk.ijse.NoteCollector.V2.util.Mapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService{
    @Autowired
    private NoteDao noteDao;
    @Autowired
    private Mapping noteMapping;
    private static List<NoteDTO>noteDTOList=new ArrayList<>();

    @Override
    public void saveNote(NoteDTO noteDTO) {
        noteDTO.setNoteId(AppUtil.generateNoteId());
        NoteEntity savedNote =
                noteDao.save(noteMapping.toNoteEntity(noteDTO));
        if(savedNote == null){
            throw new DataPersistExeption("Note not saved");
        }


    }

    @Override
    public List<NoteDTO> getAllNotes() {
        return noteMapping.asNoteDTOList( noteDao.findAll());
    }

    @Override
    public NoteStatus getNote(String noteId) {
        if(noteDao.existsById(noteId)){
            var selectedUser = noteDao.getReferenceById(noteId);
            return noteMapping.toNoteDTO(selectedUser);
        }else {
            return new SelectedUserAndNoteErrorStatus(2,"Selected note not found");
        }
    }

    @Override
    public void deleteNote(String noteId) {
        Optional<NoteEntity>foundNote =noteDao.findById(noteId);
        if (!foundNote.isPresent()) {
            throw new NoteNotFoundExeption("Note with id " + noteId + " not found");
        }else {
            noteDao.deleteById(noteId);
        }
    }

    @Override
    public void updateNote(String noteId,NoteDTO noteDTO) {
        Optional<NoteEntity> findNote=noteDao.findById(noteId);
        if (!findNote.isPresent()){
            throw new NoteNotFoundExeption("Note with id " + noteId + " not found");
        }else {
            findNote.get().setNoteTitle(noteDTO.getNoteTitle());
            findNote.get().setNoteDesc(noteDTO.getNoteDesc());
            findNote.get().setCreatedDate(noteDTO.getCreatedDate());
            findNote.get().setPriorityLevel(noteDTO.getPriorityLevel());
        }
    }
}
