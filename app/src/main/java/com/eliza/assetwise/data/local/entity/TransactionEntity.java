package com.eliza.assetwise.data.local.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 取引エンティティ
 * Transaction entity - records all financial transactions
 */
@Entity(
    tableName = "transactions",
    foreignKeys = {
        @ForeignKey(
            entity = AccountEntity.class,
            parentColumns = "id",
            childColumns = "accountId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = CategoryEntity.class,
            parentColumns = "id",
            childColumns = "categoryId",
            onDelete = ForeignKey.SET_NULL
        )
    },
    indices = {
        @Index(value = "accountId"),
        @Index(value = "categoryId")
    }
)
public class TransactionEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long accountId;            // 口座ID（FK → accounts）
    private Long categoryId;           // カテゴリID（FK → categories、null可）
    private TransactionType type;      // 取引種類
    private double amount;             // 金額
    private String currency;           // 通貨コード
    private long date;                 // 取引日（Unix timestamp）
    private String note;               // メモ
    private long createdAt;

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getAccountId() { return accountId; }
    public void setAccountId(long accountId) { this.accountId = accountId; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public long getDate() { return date; }
    public void setDate(long date) { this.date = date; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
