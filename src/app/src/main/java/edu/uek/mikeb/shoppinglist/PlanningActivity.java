package edu.uek.mikeb.shoppinglist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import edu.uek.mikeb.shoppinglist.adapter.PlanningListAdapter;
import edu.uek.mikeb.shoppinglist.database.ShoppingListDataSource;
import edu.uek.mikeb.shoppinglist.database.ShoppingListElementDataSource;
import edu.uek.mikeb.shoppinglist.entity.ShoppingList;


public class PlanningActivity extends ActionBarActivity {

    public static final String SHOPPING_LIST_ID = "shopping_list_id";

    private ShoppingListDataSource shoppingListDataSource;
    private ShoppingListElementDataSource shoppingListElementDataSource;
    private ArrayAdapter<ShoppingList> adapter;
    private ArrayList<ShoppingList> shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);
    }

    @Override
    protected void onResume() {
        super.onResume();

        shoppingListDataSource = new ShoppingListDataSource(this);
        try {
            shoppingListDataSource.open();
            shoppingList = new ArrayList<ShoppingList>(shoppingListDataSource.getAllShoppingLists());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            shoppingListDataSource.close();
        }

        ListView planningList = (ListView) findViewById(R.id.list_planning);

        adapter = new PlanningListAdapter(this, R.layout.planning_list_entry, shoppingList);

        planningList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long shoppingListEntryId = shoppingList.get(position).getId();
                Intent intent = new Intent(PlanningActivity.this, ShoppingListActivity.class);
                intent.putExtra(SHOPPING_LIST_ID, shoppingListEntryId);
                startActivity(intent);
            }
        });

        planningList.setAdapter(adapter);
    }

    public void addNewShoppingList(View view) {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        startActivity(intent);
    }

    public void deleteAll(View view) {
        shoppingListElementDataSource = new ShoppingListElementDataSource(this);
        shoppingListDataSource = new ShoppingListDataSource(this);
        try {
            shoppingListElementDataSource.open();
            for (ShoppingList sl : shoppingList) {
                shoppingListElementDataSource.deleteAll(sl.getId());
            }
            shoppingListDataSource.open();
            shoppingListDataSource.deleteAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            shoppingListElementDataSource.close();
            shoppingListDataSource.close();
        }

        shoppingList.clear();
        adapter.notifyDataSetChanged();

        ListView listView = (ListView) findViewById(R.id.list_planning);
        listView.invalidateViews();
    }

}
