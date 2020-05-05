package edu.ranken.emeier.pockgit.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.ranken.emeier.pockgit.data.entity.Commit;
import edu.ranken.emeier.pockgit.data.entity.Repo;

@Dao
public interface CommitDao {

    @Query("SELECT * FROM commit_table ORDER BY commit_date ASC")
    LiveData<List<Commit>> getAllCommits();

    @Query("SELECT * FROM commit_table WHERE repo_id = :repoId")
    LiveData<List<Commit>> getCommitsByRepoId(int repoId);

    @Query("SELECT * FROM commit_table WHERE id = :id")
    Commit getCommitById(String id);

    @Query("SELECT * FROM commit_table WHERE repo_id = :repoId ORDER BY commit_date DESC LIMIT 1")
    Commit getMostRecentCommit(int repoId);

    @Query("SELECT * FROM repo_table WHERE id = :repoId")
    Repo getCommitRepo(int repoId);

    @Insert
    void insert(Commit commit);

    @Update
    void updateCommit(Commit... commit);

    @Query("DELETE FROM commit_table WHERE repo_id = :repoId")
    void deleteCommitByRepoId(int repoId);
}