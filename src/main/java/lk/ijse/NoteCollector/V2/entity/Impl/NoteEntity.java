package lk.ijse.NoteCollector.V2.entity.Impl;

import jakarta.persistence.*;
import lk.ijse.NoteCollector.V2.entity.SuperEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "note")
public class NoteEntity implements SuperEntity {
    @Id
    private String noteId;
    private String noteTitle;
    private String noteDesc;
    private String createdDate;
    private String priorityLevel;
    @ManyToOne
    @JoinColumn(name="userId",nullable = false)
    private UserEntity user;
}
