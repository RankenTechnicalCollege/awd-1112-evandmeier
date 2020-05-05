package edu.ranken.emeier.pockgit.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account_table")
public class Account {

    @PrimaryKey
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private int id;

    @NonNull
    @ColumnInfo(name = "login", typeAffinity = ColumnInfo.TEXT)
    private String login;

    @NonNull
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    private String name;

    @ColumnInfo(name = "avatar_url", typeAffinity = ColumnInfo.TEXT)
    private String avatarUrl;

    @NonNull
    @ColumnInfo(name = "access_token", typeAffinity = ColumnInfo.TEXT)
    private String accessToken;

    public Account(int id, @NonNull String login, @NonNull String name, @NonNull String avatarUrl, @NonNull String accessToken) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.accessToken = accessToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(@NonNull String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @NonNull
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
    }
}
