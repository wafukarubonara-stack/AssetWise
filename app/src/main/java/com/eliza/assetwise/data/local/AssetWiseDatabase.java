package com.eliza.assetwise.data.local;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.eliza.assetwise.data.local.converter.Converters;
import com.eliza.assetwise.data.local.dao.*;
import com.eliza.assetwise.data.local.entity.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                AccountEntity.class, AssetEntity.class,
                TransactionEntity.class, CategoryEntity.class,
                PriceHistoryEntity.class, ExchangeRateEntity.class
        },
        version = 1, exportSchema = false
)
@TypeConverters(Converters.class)
public abstract class AssetWiseDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();
    public abstract AssetDao assetDao();
    public abstract TransactionDao transactionDao();
    public abstract CategoryDao categoryDao();
    public abstract PriceHistoryDao priceHistoryDao();
    public abstract ExchangeRateDao exchangeRateDao();

    private static volatile AssetWiseDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static AssetWiseDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AssetWiseDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AssetWiseDatabase.class,
                            "assetwise_database"
                    ).addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(() -> {
                        CategoryDao dao = INSTANCE.categoryDao();
                        if (dao.getCategoryCount() == 0) {
                            List<CategoryEntity> defaults = new ArrayList<>();
                            defaults.add(createCategory("Salary", "ic_work", "#4CAF50"));
                            defaults.add(createCategory("Dividend", "ic_trending_up", "#2196F3"));
                            defaults.add(createCategory("Interest", "ic_account_balance", "#00BCD4"));
                            defaults.add(createCategory("Transfer", "ic_swap_horiz", "#FF9800"));
                            defaults.add(createCategory("Fee", "ic_receipt", "#F44336"));
                            defaults.add(createCategory("Investment", "ic_show_chart", "#9C27B0"));
                            defaults.add(createCategory("Living", "ic_home", "#795548"));
                            defaults.add(createCategory("Other", "ic_category", "#607D8B"));
                            dao.insertAll(defaults);
                        }
                    });
                }
            };

    private static CategoryEntity createCategory(String name, String icon, String color) {
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setIcon(icon);
        category.setColor(color);
        category.setDefault(true);
        return category;
    }
}