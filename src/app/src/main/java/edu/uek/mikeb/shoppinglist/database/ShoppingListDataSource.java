package edu.uek.mikeb.shoppinglist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.uek.mikeb.shoppinglist.entity.ShoppingList;


public class ShoppingListDataSource {

    private SQLiteDatabase database;
    private ShoppingDBContract.ShoppingListDBHelper dbHelper;
    private String[] allColumns = {ShoppingDBContract.ShoppingListEntry.COLUMN_NAME_ID,
            ShoppingDBContract.ShoppingListEntry.COLUMN_NAME_DATE};

    public ShoppingListDataSource(Context context) {
        dbHelper = new ShoppingDBContract.ShoppingListDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ShoppingList createShoppingList() {
        ContentValues values = new ContentValues();

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        values.put(ShoppingDBContract.ShoppingListEntry.COLUMN_NAME_DATE, format.format(date));

        long insertId = database.insert(ShoppingDBContract.ShoppingListEntry.TABLE_NAME, null, values);

        return getShoppingList(insertId);
    }

    public ShoppingList getShoppingList(long id) {
        Cursor cursor = database.query(ShoppingDBContract.ShoppingListEntry.TABLE_NAME,
                allColumns, ShoppingDBContract.ShoppingListEntry.COLUMN_NAME_ID + " = " +
                        id, null, null, null, null);

        cursor.moveToFirst();

        ShoppingList shoppingList = cursorToShoppingList(cursor);
        cursor.close();

        return shoppingList;
    }

    public List<ShoppingList> getAllShoppingLists() {
        List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

        Cursor cursor = database.query(ShoppingDBContract.ShoppingListEntry.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ShoppingList shoppingList = cursorToShoppingList(cursor);
            shoppingLists.add(shoppingList);
            cursor.moveToNext();
        }

        cursor.close();
        return shoppingLists;
    }

    public void deleteAll() {
        database.delete(ShoppingDBContract.ShoppingListEntry.TABLE_NAME,null,null);
    }

    private ShoppingList cursorToShoppingList(Cursor cursor) {
        long id = cursor.getLong(0);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        try {
            date = format.parse(cursor.getString(1));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ShoppingList shoppingList = new ShoppingList();

        shoppingList.setDate(date);
        shoppingList.setId(id);

        return shoppingList;
    }

}
