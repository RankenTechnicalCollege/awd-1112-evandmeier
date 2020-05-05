package edu.ranken.emeier.pockgit.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "commit_table", foreignKeys = @ForeignKey(entity = Repo.class, parentColumns = "id", childColumns = "repo_id"), indices = @Index("repo_id"))
public class Commit {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.TEXT)
    private String id;

    @ColumnInfo(name = "repo_id", typeAffinity = ColumnInfo.INTEGER)
    private int repoId;

    @NonNull
    @ColumnInfo(name = "committer_name", typeAffinity = ColumnInfo.TEXT)
    private String committerName;

    @NonNull
    @ColumnInfo(name = "commit_date", typeAffinity = ColumnInfo.TEXT)
    private String commitDate;

    @NonNull
    @ColumnInfo(name = "commit_message", typeAffinity = ColumnInfo.TEXT)
    private String commitMessage;

    @NonNull
    @ColumnInfo(name = "commit_author_avatar", typeAffinity = ColumnInfo.TEXT)
    private String commitAuthorAvatar;

    public Commit(String id, int repoId, @NonNull String committerName, @NonNull String commitDate, @NonNull String commitMessage, @NonNull String commitAuthorAvatar) {
        this.id = id;
        this.repoId = repoId;
        this.committerName = committerName;
        this.commitDate = commitDate;
        this.commitMessage = commitMessage;
        this.commitAuthorAvatar = commitAuthorAvatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRepoId() {
        return repoId;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }

    @NonNull
    public String getCommitterName() {
        return committerName;
    }

    public void setCommitterName(@NonNull String committerName) {
        this.committerName = committerName;
    }

    @NonNull
    public String getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(@NonNull String commitDate) {
        this.commitDate = commitDate;
    }

    @NonNull
    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(@NonNull String commitMessage) {
        this.commitMessage = commitMessage;
    }

    @NonNull
    public String getCommitAuthorAvatar() {
        return commitAuthorAvatar;
    }

    public void setCommitAuthorAvatar(@NonNull String commitAuthorAvatar) {
        this.commitAuthorAvatar = commitAuthorAvatar;
    }
}
