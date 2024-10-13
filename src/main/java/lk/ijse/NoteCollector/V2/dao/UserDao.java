package lk.ijse.NoteCollector.V2.dao;

import lk.ijse.NoteCollector.V2.entity.Impl.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity,String> {
}
