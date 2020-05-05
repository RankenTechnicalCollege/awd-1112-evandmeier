package edu.ranken.emeier.pockgit.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.ranken.emeier.pockgit.data.entity.Account;

@Dao
public interface AccountDao {

    @Query("SELECT * FROM account_table ORDER BY login ASC")
    LiveData<List<Account>> getAllAccounts();

    @Query("SELECT * FROM account_table WHERE id = :id")
    Account getAccountById(int id);

    @Query("SELECT * FROM account_table WHERE login = :login")
    Account getAccountByLogin(String login);

    @Query("SELECT id FROM account_table ORDER BY id DESC LIMIT 1")
    int getNewestAccountId();

    @Insert
    void insert(Account account);

    @Update
    void updateAccount(Account... account);

    @Query("DELETE FROM account_table WHERE id = :id")
    void deleteAccountById(int id);

    @Query("DELETE FROM account_table")
    void deleteAllAccounts();
}