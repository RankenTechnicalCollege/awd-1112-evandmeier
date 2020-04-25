package edu.ranken.emeier.hot5.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.ranken.emeier.hot5.data.entity.Note;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY id DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM note_table WHERE id = :id")
    Note getNoteById(int id);

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note... note);

    @Query("DELETE FROM note_table WHERE id = :id")
    void deleteNote(int id);

    @Query("DELETE FROM note_table;")
    void deleteAllNotes();
}
