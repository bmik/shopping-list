package edu.uek.mikeb.shoppinglist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uek.mikeb.shoppinglist.R;
import edu.uek.mikeb.shoppinglist.entity.ShoppingList;


public class PlanningListAdapter extends ArrayAdapter<ShoppingList> {

    private final Context context;
    private final ArrayList<ShoppingList> values;

    public PlanningListAdapter(Context context, int resource, ArrayList<ShoppingList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.planning_list_entry, parent, false);

        TextView number = (TextView) rowView.findViewById(R.id.planning_list_entry_no);
        TextView date = (TextView) rowView.findViewById(R.id.planning_list_entry_date);

        number.setText(String.valueOf(position + 1));
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        date.setText(format.format(values.get(position).getDate()));

        return rowView;

    }
}
