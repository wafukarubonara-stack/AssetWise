package com.eliza.assetwise.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.eliza.assetwise.data.local.entity.AssetEntity;

import java.util.List;

/**
 * 資産データアクセスオブジェクト
 * Asset DAO - CRUD operations for individual assets
 */
@Dao
public interface AssetDao {

    @Insert
    long insert(AssetEntity asset);

    @Update
    void update(AssetEntity asset);

    @Delete
    void delete(AssetEntity asset);

    @Query("SELECT * FROM assets ORDER BY name ASC")
    LiveData<List<AssetEntity>> getAllAssets();

    @Query("SELECT * FROM assets WHERE id = :id")
    LiveData<AssetEntity> getAssetById(long id);

    @Query("SELECT * FROM assets WHERE accountId = :accountId ORDER BY name ASC")
    LiveData<List<AssetEntity>> getAssetsByAccountId(long accountId);

    @Query("SELECT * FROM assets WHERE symbol = :symbol")
    LiveData<List<AssetEntity>> getAssetsBySymbol(String symbol);

    @Query("DELETE FROM assets WHERE accountId = :accountId")
    void deleteByAccountId(long accountId);
}
