package com.eliza.assetwise.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.eliza.assetwise.data.local.entity.CategoryEntity;

import java.util.List;

/**
 * カテゴリデータアクセスオブジェクト
 * Category DAO - CRUD operations for categories
 */
@Dao
public interface CategoryDao {

    @Insert
    long insert(CategoryEntity category);

    @Insert
    void insertAll(List<CategoryEntity> categories);

    @Update
    void update(CategoryEntity category);

    @Delete
    void delete(CategoryEntity category);

    @Query("SELECT * FROM categories ORDER BY name ASC")
    LiveData<List<CategoryEntity>> getAllCategories();

    @Query("SELECT * FROM categories WHERE id = :id")
    LiveData<CategoryEntity> getCategoryById(long id);

    @Query("SELECT * FROM categories WHERE isDefault = 1")
    LiveData<List<CategoryEntity>> getDefaultCategories();

    @Query("SELECT COUNT(*) FROM categories")
    int getCategoryCount();
}
