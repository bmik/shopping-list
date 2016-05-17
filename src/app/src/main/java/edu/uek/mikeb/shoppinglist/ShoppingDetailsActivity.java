package edu.uek.mikeb.shoppinglist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.uek.mikeb.shoppinglist.adapter.ShoppingDetailsAdapter;
import edu.uek.mikeb.shoppinglist.database.ShoppingListDataSource;
import edu.uek.mikeb.shoppinglist.database.ShoppingListElementDataSource;
import edu.uek.mikeb.shoppinglist.entity.ShoppingList;
import edu.uek.mikeb.shoppinglist.entity.ShoppingListElement;


public class ShoppingDetailsActivity extends ActionBarActivity {

    private ShoppingListElementDataSource shoppingListElementDataSource;
    private ShoppingListDataSource shoppingListDataSource;
    private ArrayAdapter<ShoppingListElement> adapter;
    private ArrayList<ShoppingListElement> shoppingListElements;
    private long shoppingListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_details);
    }

    @Override
    protected void onResume() {
        super.onResume();

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

        ListView shoppingListView = (ListView) findViewById(R.id.list_shopping_details_list);
        adapter = new ShoppingDetailsAdapter(this, R.layout.shopping_detail_list_entry, shoppingListElements);

        shoppingListView.setAdapter(adapter);

       /* final EditText priceView = (EditText) findViewById(R.id.text_price_edit);
        if (priceView != null) {
            priceView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    shoppingListElementDataSource = new ShoppingListElementDataSource(ShoppingDetailsActivity.this);

                    TextView idView = (TextView) findViewById(R.id.text_element_id);
                    long id = Long.parseLong(idView.getText().toString());

                    double price = StringUtil.parseDoubleValue(priceView.getText().toString());

                    try {
                        shoppingListElementDataSource.open();
                        ShoppingListElement element = shoppingListElementDataSource.getShoppingListElement(id);
                        element.setPrice(price);
                        shoppingListElementDataSource.update(element);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        shoppingListElementDataSource.close();
                    }
                }
            });
        }*/
    }

    /*public void recount(View view) {
        double price = 0.0;

        for (ShoppingListElement e : shoppingListElements) {
            price += (e.getPrice() * e.getAmount());
        }

        TextView priceView = (TextView) findViewById(R.id.value_overall);
        priceView.setText(" " + StringUtil.parseStringFromDouble(price) + " zł");
    }*/
}
