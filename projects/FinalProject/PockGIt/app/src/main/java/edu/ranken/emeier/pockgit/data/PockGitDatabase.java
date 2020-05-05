package edu.ranken.emeier.pockgit.data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import edu.ranken.emeier.pockgit.data.dao.AccountDao;
import edu.ranken.emeier.pockgit.data.dao.CommitDao;
import edu.ranken.emeier.pockgit.data.dao.RepoDao;
import edu.ranken.emeier.pockgit.data.entity.Account;
import edu.ranken.emeier.pockgit.data.entity.Commit;
import edu.ranken.emeier.pockgit.data.entity.Repo;

@Database(entities = {Account.class, Repo.class, Commit.class}, version = 7)
public abstract class PockGitDatabase extends RoomDatabase {

    // constants
    private static final String TAG = PockGitDatabase.class.getSimpleName();

    // DAOs
    public abstract AccountDao getAccountDao();
    public abstract RepoDao getRepoDao();
    public abstract CommitDao getCommitDao();

    public static PockGitDatabase buildDatabase(Application app) {
        return Room.databaseBuilder(app, PockGitDatabase.class, "pockgit_database")
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
                    db.execSQL("DELETE FROM account_table");
                    db.execSQL("DELETE FROM repo_table");
                    db.execSQL("DELETE FROM commit_table");
                }

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    Log.i(TAG, "Database has been opened!!");
                }
            };
}
