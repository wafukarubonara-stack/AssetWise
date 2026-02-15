package com.eliza.assetwise.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.eliza.assetwise.data.local.entity.PriceHistoryEntity;

import java.util.List;

/**
 * 価格履歴データアクセスオブジェクト
 * PriceHistory DAO - operations for price history
 */
@Dao
public interface PriceHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PriceHistoryEntity priceHistory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PriceHistoryEntity> priceHistories);

    @Query("SELECT * FROM price_history WHERE assetId = :assetId ORDER BY timestamp DESC")
    LiveData<List<PriceHistoryEntity>> getPriceHistoryByAssetId(long assetId);

    @Query("SELECT * FROM price_history WHERE assetId = :assetId ORDER BY timestamp DESC LIMIT 1")
    LiveData<PriceHistoryEntity> getLatestPrice(long assetId);

    @Query("SELECT * FROM price_history WHERE assetId = :assetId AND timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp ASC")
    LiveData<List<PriceHistoryEntity>> getPriceHistoryByDateRange(long assetId, long startTime, long endTime);

    @Query("DELETE FROM price_history WHERE assetId = :assetId")
    void deleteByAssetId(long assetId);
}
