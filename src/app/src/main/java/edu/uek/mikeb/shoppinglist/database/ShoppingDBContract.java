package edu.uek.mikeb.shoppinglist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import edu.uek.mikeb.shoppinglist.entity.ShoppingList;


public final class ShoppingDBContract {

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_SHOPPING_LIST =
            "CREATE TABLE " + ShoppingListEntry.TABLE_NAME + " (" +
                    ShoppingListEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    ShoppingListEntry.COLUMN_NAME_DATE + " DATE" +
                    ");";
    private static final String SQL_CREATE_SHOPPING_LIST_ENTRY =
            "CREATE TABLE " + ShoppingListElementEntry.TABLE_NAME + " (" +
                    ShoppingListElementEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    ShoppingListElementEntry.COLUMN_NAME_PRODUCT + " TEXT, " +
                    ShoppingListElementEntry.COLUMN_NAME_QUANTITY + " REAL, " +
                    ShoppingListElementEntry.COLUMN_NAME_UNIT + " TEXT, " +
                    ShoppingListElementEntry.COLUMN_NAME_PRICE + " REAL, " +
                    ShoppingListElementEntry.COLUMN_NAME_SHOPPING_LIST_ID + " INTEGER, " +
                    "FOREIGN KEY(" + ShoppingListElementEntry.COLUMN_NAME_SHOPPING_LIST_ID +
                    ") REFERENCES " + ShoppingListEntry.TABLE_NAME + "(" + ShoppingListEntry.COLUMN_NAME_ID +
                    "));";

    private static final String SQL_DELETE_SHOPPING_LIST =
            "DROP TABLE IF EXISTS " + ShoppingListEntry.TABLE_NAME + ";";

    private static final String SQL_DELETE_SHOPPING_LIST_ENTRY =
            "DROP TABLE IF EXISTS " + ShoppingListElementEntry.TABLE_NAME + ";";

    public ShoppingDBContract() {}

    public static abstract class ShoppingListEntry implements BaseColumns {
        public static final String TABLE_NAME = "shopping_list";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DATE = "date";
    }

    public static abstract class ShoppingListElementEntry implements BaseColumns {
        public static final String TABLE_NAME = "shopping_list_entry";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_PRODUCT = "product";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_UNIT = "unit";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_SHOPPING_LIST_ID = "shopping_list_id";
    }

    public static class ShoppingListDBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "shoppinglist.db";
        private static final int DATABASE_VERSION = 2;

        public ShoppingListDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_SHOPPING_LIST);
            db.execSQL(SQL_CREATE_SHOPPING_LIST_ENTRY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_SHOPPING_LIST);
            db.execSQL(SQL_DELETE_SHOPPING_LIST_ENTRY);
            onCreate(db);
        }

    }

}
