package com.eliza.assetwise.data.local.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 価格履歴エンティティ
 * Price history entity - stores historical prices for assets
 */
@Entity(
    tableName = "price_history",
    foreignKeys = @ForeignKey(
        entity = AssetEntity.class,
        parentColumns = "id",
        childColumns = "assetId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {
        @Index(value = "assetId"),
        @Index(value = {"assetId", "timestamp"}, unique = true)
    }
)
public class PriceHistoryEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long assetId;      // 資産ID（FK → assets）
    private double price;      // 価格
    private String currency;   // 通貨コード
    private long timestamp;    // 取得日時（Unix timestamp）

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getAssetId() { return assetId; }
    public void setAssetId(long assetId) { this.assetId = assetId; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
