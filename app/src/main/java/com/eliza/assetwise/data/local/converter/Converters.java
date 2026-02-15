package com.eliza.assetwise.data.local.converter;

import androidx.room.TypeConverter;

import com.eliza.assetwise.data.local.entity.AccountType;
import com.eliza.assetwise.data.local.entity.AssetType;
import com.eliza.assetwise.data.local.entity.TransactionType;

/**
 * Room TypeConverters
 * EnumをStringに変換してDBに保存するためのコンバーター
 */
public class Converters {

    // AccountType
    @TypeConverter
    public static String fromAccountType(AccountType type) {
        return type == null ? null : type.name();
    }

    @TypeConverter
    public static AccountType toAccountType(String value) {
        return value == null ? null : AccountType.valueOf(value);
    }

    // AssetType
    @TypeConverter
    public static String fromAssetType(AssetType type) {
        return type == null ? null : type.name();
    }

    @TypeConverter
    public static AssetType toAssetType(String value) {
        return value == null ? null : AssetType.valueOf(value);
    }

    // TransactionType
    @TypeConverter
    public static String fromTransactionType(TransactionType type) {
        return type == null ? null : type.name();
    }

    @TypeConverter
    public static TransactionType toTransactionType(String value) {
        return value == null ? null : TransactionType.valueOf(value);
    }
}
