package com.eliza.assetwise.data.local.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 個別資産エンティティ
 * Asset entity - represents individual holdings (stocks, crypto coins, etc.)
 */
@Entity(
    tableName = "assets",
    foreignKeys = @ForeignKey(
        entity = AccountEntity.class,
        parentColumns = "id",
        childColumns = "accountId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = @Index(value = "accountId")
)
public class AssetEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long accountId;        // 所属口座ID（FK → accounts）
    private String symbol;         // シンボル（AAPL, BTC, etc.）
    private String name;           // 資産名（Apple Inc., Bitcoin, etc.）
    private AssetType type;        // 資産種類
    private double quantity;       // 保有数量
    private double costBasis;      // 取得原価（総額）
    private String currency;       // 通貨コード
    private long createdAt;
    private long updatedAt;

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getAccountId() { return accountId; }
    public void setAccountId(long accountId) { this.accountId = accountId; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public AssetType getType() { return type; }
    public void setType(AssetType type) { this.type = type; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public double getCostBasis() { return costBasis; }
    public void setCostBasis(double costBasis) { this.costBasis = costBasis; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
