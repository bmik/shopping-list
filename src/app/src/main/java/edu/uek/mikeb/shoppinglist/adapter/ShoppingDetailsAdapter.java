package edu.uek.mikeb.shoppinglist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uek.mikeb.shoppinglist.R;
import edu.uek.mikeb.shoppinglist.database.ShoppingListElementDataSource;
import edu.uek.mikeb.shoppinglist.entity.ShoppingListElement;
import edu.uek.mikeb.shoppinglist.util.StringUtil;


public class ShoppingDetailsAdapter extends ArrayAdapter<ShoppingListElement> {

    private final Context context;
    private final ArrayList<ShoppingListElement> values;
    private ShoppingListElementDataSource shoppingListElementDataSource;

    public ShoppingDetailsAdapter(Context context, int resource, ArrayList<ShoppingListElement> objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.shopping_detail_list_entry, parent, false);

        TextView id = (TextView) rowView.findViewById(R.id.text_element_id);
        TextView product = (TextView) rowView.findViewById(R.id.text_product_name);
        TextView quantity = (TextView) rowView.findViewById(R.id.text_quantity);
        TextView unit = (TextView) rowView.findViewById(R.id.text_unit);
//        EditText price = (EditText) rowView.findViewById(R.id.text_price_edit);

        id.setText(String.valueOf(values.get(position).getId()));
        product.setText(values.get(position).getProduct());
        quantity.setText(StringUtil.parseStringFromDouble(values.get(position).getAmount()));
        unit.setText(values.get(position).getUnit());
//        price.setText(StringUtil.parseStringFromDouble(values.get(position).getPrice()));
        /*final View row = rowView;

        final EditText priceView = (EditText) row.findViewById(R.id.text_price_edit);
        if (priceView != null) {
            priceView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText priceView = (EditText) v;

                    if (hasFocus == false) {
                        shoppingListElementDataSource = new ShoppingListElementDataSource(context);

                        TextView idView = (TextView) row.findViewById(R.id.text_element_id);
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

                }
            });
        }*/

        return rowView;
    }
}
