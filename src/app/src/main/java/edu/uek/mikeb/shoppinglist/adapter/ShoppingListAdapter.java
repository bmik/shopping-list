package edu.uek.mikeb.shoppinglist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uek.mikeb.shoppinglist.R;
import edu.uek.mikeb.shoppinglist.entity.ShoppingListElement;

public class ShoppingListAdapter extends ArrayAdapter<ShoppingListElement> {

    private final Context context;
    private final ArrayList<ShoppingListElement> values;

    public ShoppingListAdapter(Context context, int resource, ArrayList<ShoppingListElement> objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.shopping_list_entry, parent, false);

        TextView id = (TextView) rowView.findViewById(R.id.text_element_id);
        TextView product = (TextView) rowView.findViewById(R.id.text_product_name);
        TextView quantity = (TextView) rowView.findViewById(R.id.text_quantity);
        TextView unit = (TextView) rowView.findViewById(R.id.text_unit);

        id.setText(String.valueOf(values.get(position).getId()));
        product.setText(values.get(position).getProduct());
        quantity.setText(String.valueOf(values.get(position).getAmount()));
        unit.setText(values.get(position).getUnit());

        return rowView;

    }
}
