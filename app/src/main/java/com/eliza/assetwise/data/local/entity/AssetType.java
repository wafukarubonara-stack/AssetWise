package com.eliza.assetwise.data.local.entity;

/**
 * 資産の種類を定義するEnum
 * Asset type enumeration
 */
public enum AssetType {
    STOCK,          // 株式
    ETF,            // ETF
    MUTUAL_FUND,    // 投資信託
    BOND,           // 債券
    CRYPTO,         // 暗号資産
    CURRENCY,       // 外貨預金
    DEPOSIT,        // 預金残高
    OTHER           // その他
}
