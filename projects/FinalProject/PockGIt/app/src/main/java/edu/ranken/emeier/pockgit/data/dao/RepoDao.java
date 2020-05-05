package edu.ranken.emeier.pockgit.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.ranken.emeier.pockgit.data.entity.Account;
import edu.ranken.emeier.pockgit.data.entity.Repo;

@Dao
public interface RepoDao {

    @Query("SELECT * FROM repo_table ORDER BY name ASC")
    LiveData<List<Repo>> getAllRepos();

    @Query("SELECT * FROM repo_table WHERE owner_id = :ownerId")
    List<Repo> getRepoByOwnerId(int ownerId);

    @Query("SELECT * FROM repo_table WHERE id = :id")
    Repo getRepoById(int id);

    @Query("SELECT * FROM account_table WHERE id = :ownerId")
    Account getRepoOwner(int ownerId);

    @Insert
    void insert(Repo repo);

    @Update
    void updateRepo(Repo... repo);

    @Query("DELETE FROM repo_table WHERE owner_id = :ownerId")
    void deleteReposByOwnerId(int ownerId);
}