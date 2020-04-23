package edu.ranken.emeier.roomwordsamplelive.data.entitiy;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table", indices = {@Index(value = {"word"}, unique = true)})
public class Word {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private int id;

    @NonNull
    @ColumnInfo(name = "word", typeAffinity = ColumnInfo.TEXT, collate = ColumnInfo.NOCASE)
    private String word;

    public Word(@NonNull String word) {

        this.word = word;
    }

    @Ignore
    public Word(int id, @NonNull  String word) {
        this.id = id;
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getWord() {
        return word;
    }

    public void setWord(@NonNull String word) {
        this.word = word;
    }
}
