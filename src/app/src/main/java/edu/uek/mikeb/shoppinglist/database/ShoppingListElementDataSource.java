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
import edu.uek.mikeb.shoppinglist.entity.ShoppingListElement;

/**
 * Created by bmik on 2015-05-22.
 */
public class ShoppingListElementDataSource {

    private SQLiteDatabase database;
    private ShoppingDBContract.ShoppingListDBHelper dbHelper;
    private String[] allColumns = {ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_ID,
            ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_PRODUCT,
            ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_QUANTITY,
            ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_UNIT,
            ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_PRICE,
            ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_SHOPPING_LIST_ID
            };

    public ShoppingListElementDataSource(Context context) {
        dbHelper = new ShoppingDBContract.ShoppingListDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ShoppingListElement createShoppingListElement(String product, double quantity, String unit, long shoppingListId) {
        ContentValues values = new ContentValues();

        values.put(ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_PRODUCT, product);
        values.put(ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_QUANTITY, quantity);
        values.put(ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_UNIT, unit);
        values.put(ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_PRICE, 0.0);
        values.put(ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_SHOPPING_LIST_ID, shoppingListId);

        long insertId = database.insert(ShoppingDBContract.ShoppingListElementEntry.TABLE_NAME, null, values);

        Cursor cursor = database.query(ShoppingDBContract.ShoppingListElementEntry.TABLE_NAME,
                allColumns, ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_ID + " = " +
                        insertId, null, null, null, null);

        cursor.moveToFirst();

        ShoppingListElement shoppingListElement = cursorToShoppingListElement(cursor);
        cursor.close();

        return shoppingListElement;
    }

    public List<ShoppingListElement> getAllShoppingListElements(long shoppingListId) {
        List<ShoppingListElement> shoppingListElements = new ArrayList<ShoppingListElement>();

        Cursor cursor = database.query(ShoppingDBContract.ShoppingListElementEntry.TABLE_NAME,
                allColumns, ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_SHOPPING_LIST_ID + " = " + shoppingListId, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ShoppingListElement shoppingList = cursorToShoppingListElement(cursor);
            shoppingListElements.add(shoppingList);
            cursor.moveToNext();
        }

        cursor.close();
        return shoppingListElements;
    }

    public ShoppingListElement getShoppingListElement(long id) {
        Cursor cursor = database.query(ShoppingDBContract.ShoppingListElementEntry.TABLE_NAME,
                allColumns, ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_ID + " = " + id, null, null, null, null);

        ShoppingListElement shoppingList = null;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            shoppingList = cursorToShoppingListElement(cursor);
            cursor.moveToNext();
        }

        cursor.close();
        return shoppingList;
    }

    public void update(ShoppingListElement element) {
        ContentValues values = new ContentValues();

        values.put(ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_PRODUCT, element.getProduct());
        values.put(ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_QUANTITY, element.getAmount());
        values.put(ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_UNIT, element.getUnit());
        values.put(ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_PRICE, element.getPrice());
        values.put(ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_SHOPPING_LIST_ID, element.getShoppingListId());

        database.update(ShoppingDBContract.ShoppingListElementEntry.TABLE_NAME, values,
                ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_ID + " = " + element.getId(),null);
    }

    public void delete(long id) {
        database.delete(ShoppingDBContract.ShoppingListElementEntry.TABLE_NAME,
                ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_ID + " = " + id,null);
    }

    public void deleteAll (long shoppingListId) {
        database.delete(ShoppingDBContract.ShoppingListElementEntry.TABLE_NAME,
                ShoppingDBContract.ShoppingListElementEntry.COLUMN_NAME_SHOPPING_LIST_ID + " = " + shoppingListId,null);
    }

    private ShoppingListElement cursorToShoppingListElement(Cursor cursor) {
        long id = cursor.getLong(0);
        String product = cursor.getString(1);
        double quantity = cursor.getDouble(2);
        String unit = cursor.getString(3);
        double price = cursor.getDouble(4);
        long shoppingListId = cursor.getLong(5);


        ShoppingListElement shoppingListElement = new ShoppingListElement();

        shoppingListElement.setId(id);
        shoppingListElement.setProduct(product);
        shoppingListElement.setAmount(quantity);
        shoppingListElement.setUnit(unit);
        shoppingListElement.setPrice(price);
        shoppingListElement.setShoppingListId(shoppingListId);

        return shoppingListElement;
    }

}
