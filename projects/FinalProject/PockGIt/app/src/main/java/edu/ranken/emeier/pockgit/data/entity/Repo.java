package edu.ranken.emeier.pockgit.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "repo_table", foreignKeys = @ForeignKey(entity = Account.class, parentColumns = "id", childColumns = "owner_id"), indices = @Index("owner_id"))
public class Repo {

    @PrimaryKey
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private int id;

    @ColumnInfo(name = "owner_id", typeAffinity = ColumnInfo.INTEGER)
    private int ownerId;

    @NonNull
    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    private String name;

    @NonNull
    @ColumnInfo(name = "full_name", typeAffinity = ColumnInfo.TEXT)
    private String fullName;

    @NonNull
    @ColumnInfo(name = "description", typeAffinity = ColumnInfo.TEXT)
    private String description;

    @NonNull
    @ColumnInfo(name = "repo_owner", typeAffinity = ColumnInfo.TEXT)
    private String ownerName;

    @NonNull
    @ColumnInfo(name = "owner_avatar", typeAffinity = ColumnInfo.TEXT)
    private String ownerAvatar;

    @ColumnInfo(name = "commit_notifications_enabled", typeAffinity = ColumnInfo.INTEGER)
    private boolean commitNotificationsEnabled;

    @ColumnInfo(name = "pull_notifications_enabled", typeAffinity = ColumnInfo.INTEGER)
    private boolean pullRequestNotificationsEnabled;

    public Repo(int id, int ownerId, @NonNull String name, @NonNull String fullName, @NonNull String description, @NonNull String ownerName, @NonNull String ownerAvatar) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.ownerName = ownerName;
        this.ownerAvatar = ownerAvatar;
        setCommitNotificationsEnabled(false);
        setPullRequestNotificationsEnabled(false);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(@NonNull String fullName) {
        this.fullName = fullName;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public boolean isCommitNotificationsEnabled() {
        return commitNotificationsEnabled;
    }

    public void setCommitNotificationsEnabled(boolean commitNotificationsEnabled) {
        this.commitNotificationsEnabled = commitNotificationsEnabled;
    }

    public boolean isPullRequestNotificationsEnabled() {
        return pullRequestNotificationsEnabled;
    }

    public void setPullRequestNotificationsEnabled(boolean pullRequestNotificationsEnabled) {
        this.pullRequestNotificationsEnabled = pullRequestNotificationsEnabled;
    }

    @NonNull
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(@NonNull String ownerName) {
        this.ownerName = ownerName;
    }

    @NonNull
    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(@NonNull String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }
}
