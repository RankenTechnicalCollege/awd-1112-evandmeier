package edu.ranken.emeier.sqliteexample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class WordListDatabase extends SQLiteOpenHelper {

    // constants
    private static final String DATABASE_NAME = "example.db";
    private static final int DATABASE_VERSION = 1;



    public WordListDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE [WORD_LIST_TABLE](" +
                "[_id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "[word] TEXT NOT NULL," +
                "[definition] TEXT NOT NULL);");

        db.execSQL("INSERT INTO [WORD_LIST_TABLE] (word, definition) VALUES ('alpha', 'first letter')");
        db.execSQL("INSERT INTO [WORD_LIST_TABLE] (word, definition) VALUES ('alpha', 'second letter')");
        db.execSQL("INSERT INTO [WORD_LIST_TABLE] (word, definition) VALUES ('beta', 'particle')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // nothing to upgrade
    }

    public List<Word> getAllWords() {
        String query = "SELECT _id, word, definition FROM [WORD_LIST_TABLE]";
        ArrayList<Word> results = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.rawQuery(query,  null)) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String word = cursor.getString(1);
                String definition = cursor.getString(2);

                results.add(new Word(id, word, definition));
            }
        }

        return results;
    }

    public Word getWordById(int id) {
        String query = "SELECT _id, word, definition FROM [WORD_LIST_TABLE] WHERE _id = ?";
        String[] args = new String[] {String.valueOf(id) };

        SQLiteDatabase db = getReadableDatabase();
        try (Cursor cursor = db.rawQuery(query, args)) {
            if (cursor.moveToFirst()) {
                String word = cursor.getString(1);
                String definition = cursor.getString(2);

                return new Word(id, word, definition);
            }
        }

        return null;
    }
}
