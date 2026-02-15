package com.eliza.assetwise.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 口座エンティティ
 * Account entity - represents a bank account, brokerage, crypto wallet, etc.
 */
@Entity(tableName = "accounts")
public class AccountEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;           // 口座名（例：三菱UFJ普通預金）
    private AccountType type;      // 口座種類
    private String currency;       // 通貨コード（JPY, USD, etc.）
    private String institution;    // 金融機関名
    private String note;           // メモ
    private long createdAt;        // 作成日時（Unix timestamp）
    private long updatedAt;        // 更新日時（Unix timestamp）

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }
}
