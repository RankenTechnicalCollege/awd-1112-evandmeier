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
                    Log.i(TAG, "Database has been created!");
                    db.execSQL("DELETE FROM note_table");
                    db.execSQL("INSERT INTO note_table (title, date, body) VALUES ('Note 1', '4/15/2020', 'I must remember this')");
                    db.execSQL("INSERT INTO note_table (title, date, body) VALUES ('Note 2', '4/16/2020', 'This is another thing that I must remember')");
                    db.execSQL("INSERT INTO note_table (title, date, body) VALUES ('Note 3', '4/17/2020', 'At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio.')");
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    Log.i(TAG, "Database has been opened!!");
                }
            };
}
