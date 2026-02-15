package com.eliza.assetwise.data.local.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 為替レートエンティティ
 * Exchange rate entity - stores currency exchange rates
 */
@Entity(
    tableName = "exchange_rates",
    indices = @Index(value = {"fromCurrency", "toCurrency"}, unique = true)
)
public class ExchangeRateEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String fromCurrency;   // 変換元通貨（USD等）
    private String toCurrency;     // 変換先通貨（JPY等）
    private double rate;           // レート
    private long timestamp;        // 取得日時（Unix timestamp）

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFromCurrency() { return fromCurrency; }
    public void setFromCurrency(String fromCurrency) { this.fromCurrency = fromCurrency; }

    public String getToCurrency() { return toCurrency; }
    public void setToCurrency(String toCurrency) { this.toCurrency = toCurrency; }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
