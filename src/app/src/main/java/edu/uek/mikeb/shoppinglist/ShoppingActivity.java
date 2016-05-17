package edu.uek.mikeb.shoppinglist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.uek.mikeb.shoppinglist.adapter.PlanningListAdapter;
import edu.uek.mikeb.shoppinglist.database.ShoppingListDataSource;
import edu.uek.mikeb.shoppinglist.database.ShoppingListElementDataSource;
import edu.uek.mikeb.shoppinglist.entity.ShoppingList;


public class ShoppingActivity extends ActionBarActivity {

    public static final String SHOPPING_LIST_ID = "shopping_list_id";

    private ShoppingListDataSource shoppingListDataSource;
    private ShoppingListElementDataSource shoppingListElementDataSource;
    private ArrayAdapter<ShoppingList> adapter;
    private ArrayList<ShoppingList> shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        shoppingListDataSource = new ShoppingListDataSource(this);
        try {
            shoppingListDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ListView planningList = (ListView) findViewById(R.id.list_shopping);

        shoppingList = new ArrayList<ShoppingList>(shoppingListDataSource.getAllShoppingLists());

        adapter = new PlanningListAdapter(this, R.layout.planning_list_entry, shoppingList);

        planningList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long shoppingListEntryId = shoppingList.get(position).getId();
                Intent intent = new Intent(ShoppingActivity.this, ShoppingDetailsActivity.class);
                intent.putExtra(SHOPPING_LIST_ID, shoppingListEntryId);
                startActivity(intent);
            }
        });

        planningList.setAdapter(adapter);
    }
}
