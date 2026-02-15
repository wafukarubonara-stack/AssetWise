package com.eliza.assetwise.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.eliza.assetwise.data.local.entity.TransactionEntity;

import java.util.List;

/**
 * 取引データアクセスオブジェクト
 * Transaction DAO - CRUD operations for transactions
 */
@Dao
public interface TransactionDao {

    @Insert
    long insert(TransactionEntity transaction);

    @Update
    void update(TransactionEntity transaction);

    @Delete
    void delete(TransactionEntity transaction);

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    LiveData<List<TransactionEntity>> getAllTransactions();

    @Query("SELECT * FROM transactions WHERE id = :id")
    LiveData<TransactionEntity> getTransactionById(long id);

    @Query("SELECT * FROM transactions WHERE accountId = :accountId ORDER BY date DESC")
    LiveData<List<TransactionEntity>> getTransactionsByAccountId(long accountId);

    @Query("SELECT * FROM transactions WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    LiveData<List<TransactionEntity>> getTransactionsByDateRange(long startDate, long endDate);

    @Query("SELECT * FROM transactions WHERE categoryId = :categoryId ORDER BY date DESC")
    LiveData<List<TransactionEntity>> getTransactionsByCategory(long categoryId);
}
