package edu.uek.mikeb.shoppinglist;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.uek.mikeb.shoppinglist.adapter.PlanningListAdapter;
import edu.uek.mikeb.shoppinglist.adapter.ShoppingListAdapter;
import edu.uek.mikeb.shoppinglist.database.ShoppingListDataSource;
import edu.uek.mikeb.shoppinglist.database.ShoppingListElementDataSource;
import edu.uek.mikeb.shoppinglist.entity.ShoppingList;
import edu.uek.mikeb.shoppinglist.entity.ShoppingListElement;
import edu.uek.mikeb.shoppinglist.util.Validator;


public class ShoppingListActivity extends ActionBarActivity {

    private ShoppingListElementDataSource shoppingListElementDataSource;
    private ShoppingListDataSource shoppingListDataSource;
    private ArrayAdapter<ShoppingListElement> adapter;
    private ArrayList<ShoppingListElement> shoppingListElements;
    private long shoppingListId;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shoppingListElementDataSource = new ShoppingListElementDataSource(this);
        shoppingListDataSource = new ShoppingListDataSource(this);

        try {
            shoppingListDataSource.open();
            shoppingListElementDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ShoppingList shoppingList = null;
        shoppingListElements = null;

        Intent intent = getIntent();
        long id = intent.getLongExtra(PlanningActivity.SHOPPING_LIST_ID, -1);

        if (id >= 0) {
            shoppingList = shoppingListDataSource.getShoppingList(id);
            shoppingListElements =
                    new ArrayList<ShoppingListElement>(shoppingListElementDataSource.getAllShoppingListElements(id));
        } else {
            shoppingList = shoppingListDataSource.createShoppingList();
            shoppingListElements = new ArrayList<ShoppingListElement>();
        }

        shoppingListId = shoppingList.getId();

        ListView shoppingListView = (ListView) findViewById(R.id.list_shopping_list);
        adapter = new ShoppingListAdapter(this, R.layout.shopping_list_entry, shoppingListElements);

        shoppingListView.setAdapter(adapter);

        /*Spinner spinner = (Spinner) findViewById(R.id.spinner_unit);
        ArrayAdapter<CharSequence> unitsArray = ArrayAdapter.createFromResource(this, R.array.units_array,
                android.R.layout.simple_spinner_item);
        unitsArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(unitsArray);*/
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void save(View view) {
        finish();
    }

    public void add(View view) {

        EditText productView = (EditText) findViewById(R.id.edit_product_name);
        String product = productView.getText().toString();
        if (Validator.isEmpty(product)) {
            productView.setError(getString(R.string.validation_message_empty_value));
            return;
        }

        EditText quantityView = (EditText) findViewById(R.id.edit_quantity);
        String quantityValue = quantityView.getText().toString();
        if (Validator.isEmpty(quantityValue)) {
            quantityView.setError(getString(R.string.validation_message_empty_value));
            return;
        } else if (Validator.isNumber(quantityValue) == false) {
            productView.setText("");
            quantityView.setError(getString(R.string.validation_message_number_value));
            return;
        }
        quantityValue = quantityValue.replace(',', '.');
        double quantity = Double.parseDouble(quantityValue);

        Spinner unitView = (Spinner) findViewById(R.id.spinner_unit);
        String unit = unitView.getSelectedItem().toString();

        ShoppingListElement shoppingListElement = shoppingListElementDataSource
                .createShoppingListElement(product, quantity, unit, shoppingListId);

        shoppingListElements.add(shoppingListElement);
        adapter.notifyDataSetChanged();

        ListView listView = (ListView) findViewById(R.id.list_shopping_list);
        listView.invalidateViews();

        productView.setText("");
        quantityView.setText("");

    }

    public void delete(View view) {
        TextView idView = (TextView) findViewById(R.id.text_element_id);
        long id = Long.valueOf(idView.getText().toString());

        shoppingListElementDataSource.delete(id);

        ShoppingListElement element = findElementToRemove(id);

        shoppingListElements.remove(element);
        adapter.notifyDataSetChanged();

        ListView listView = (ListView) findViewById(R.id.list_shopping_list);
        listView.invalidateViews();
    }

    private ShoppingListElement findElementToRemove(long id) {
        for (ShoppingListElement e : shoppingListElements) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

/*    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ShoppingList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://edu.uek.mikeb.shoppinglist/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "ShoppingList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://edu.uek.mikeb.shoppinglist/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/
}
