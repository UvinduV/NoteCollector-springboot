package lk.ijse.NoteCollector.V2.dao;

import lk.ijse.NoteCollector.V2.entity.Impl.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDao extends JpaRepository<NoteEntity,String> {
}
