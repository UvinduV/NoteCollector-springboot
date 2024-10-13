package lk.ijse.NoteCollector.V2.util;

import lk.ijse.NoteCollector.V2.dto.Impl.NoteDTO;
import lk.ijse.NoteCollector.V2.dto.Impl.UserDTO;
import lk.ijse.NoteCollector.V2.entity.Impl.NoteEntity;
import lk.ijse.NoteCollector.V2.entity.Impl.UserEntity;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    //for user mapping
    public UserEntity toUserEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, UserEntity.class);
    }
    public UserDTO toUserDTO(UserEntity userEntity){
        return modelMapper.map(userEntity, UserDTO.class);
    }
    public List<UserDTO> asUserDtoLIst(List<UserEntity> userEntityList){
        return modelMapper.map(userEntityList, new TypeToken<List<UserDTO>>() {}.getType());
        //mehi list.class eka damuwoth ek object ekak pamanak labe, ea nisa type token ekak yatathe mulu DTO list eka laba gani.
    }
    public NoteEntity toNoteEntity(NoteDTO noteDTO){
        return modelMapper.map(noteDTO, NoteEntity.class);
    }
    public NoteDTO toNoteDTO(NoteEntity noteEntity){
        return modelMapper.map(noteEntity, NoteDTO.class);
    }
    public List<NoteDTO> asNoteDTOList(List<NoteEntity> noteEntities) {
        return modelMapper.map(noteEntities, new TypeToken<List<NoteDTO>>() {}.getType());
    }
}
