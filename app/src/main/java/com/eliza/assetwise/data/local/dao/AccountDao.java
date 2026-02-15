package com.eliza.assetwise.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.eliza.assetwise.data.local.entity.AccountEntity;
import com.eliza.assetwise.data.local.entity.AccountType;

import java.util.List;

/**
 * 口座データアクセスオブジェクト
 * Account DAO - CRUD operations for accounts
 */
@Dao
public interface AccountDao {

    @Insert
    long insert(AccountEntity account);

    @Update
    void update(AccountEntity account);

    @Delete
    void delete(AccountEntity account);

    @Query("SELECT * FROM accounts ORDER BY name ASC")
    LiveData<List<AccountEntity>> getAllAccounts();

    @Query("SELECT * FROM accounts WHERE id = :id")
    LiveData<AccountEntity> getAccountById(long id);

    @Query("SELECT * FROM accounts WHERE type = :type ORDER BY name ASC")
    LiveData<List<AccountEntity>> getAccountsByType(AccountType type);

    @Query("SELECT COUNT(*) FROM accounts")
    LiveData<Integer> getAccountCount();

    @Query("DELETE FROM accounts")
    void deleteAll();
}
