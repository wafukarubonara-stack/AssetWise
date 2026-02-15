package com.eliza.assetwise.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * カテゴリエンティティ
 * Category entity - for classifying transactions
 */
@Entity(tableName = "categories")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;       // カテゴリ名（給与、配当、手数料等）
    private String icon;       // アイコン名
    private String color;      // カラーコード（#FF5722等）
    private boolean isDefault; // デフォルトカテゴリかどうか

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }
}
