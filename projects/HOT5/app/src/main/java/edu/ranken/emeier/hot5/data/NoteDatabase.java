package edu.ranken.emeier.hot5.data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import edu.ranken.emeier.hot5.data.dao.NoteDao;
import edu.ranken.emeier.hot5.data.entity.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static final String TAG = NoteDatabase.class.getSimpleName();

    public abstract NoteDao getNoteDao();

    public static NoteDatabase buildDatabase(Application app) {
        return Room.databaseBuilder(app, NoteDatabase.class, "note_database")
                .addCallback(callback)
                .fallbackToDestructiveMigration()
                .build();
    }

    private static final RoomDatabase.Callback callback =
            new RoomDatabase.Callback(){

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Log.i(TAG, "CREATED!");
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    Log.i(TAG, "OPENED!");
                    //db.execSQL("DELETE FROM note_table");
                    //db.execSQL("INSERT INTO note_table (title, date, body) VALUES ('Test Title', 'Test Date', 'Test Body')");
                }
            };
}
