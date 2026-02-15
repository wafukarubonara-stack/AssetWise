package com.eliza.assetwise.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.eliza.assetwise.data.local.entity.ExchangeRateEntity;

import java.util.List;

/**
 * 為替レートデータアクセスオブジェクト
 * ExchangeRate DAO - operations for exchange rates
 */
@Dao
public interface ExchangeRateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ExchangeRateEntity exchangeRate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ExchangeRateEntity> exchangeRates);

    @Query("SELECT * FROM exchange_rates ORDER BY fromCurrency ASC")
    LiveData<List<ExchangeRateEntity>> getAllRates();

    @Query("SELECT * FROM exchange_rates WHERE fromCurrency = :from AND toCurrency = :to")
    LiveData<ExchangeRateEntity> getRate(String from, String to);

    @Query("SELECT * FROM exchange_rates WHERE fromCurrency = :from AND toCurrency = :to")
    ExchangeRateEntity getRateSync(String from, String to);

    @Query("DELETE FROM exchange_rates")
    void deleteAll();
}
