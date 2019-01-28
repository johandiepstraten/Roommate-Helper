package com.example.johan.roommatehelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ShoppingAdapter extends ArrayAdapter<String> {

//    Declare variables to use throughout adapter.
    private ArrayList<String> groceryList;
    private Context context;

//  Initiate adapter.
    public ShoppingAdapter(Context context, int resource, ArrayList<String> groceryList) {
        super(context, resource, groceryList);
        this.groceryList = groceryList;
        this.context = context;
    }
//    Set adapter to view the grocery list.
    public View getView(int position, View listView, ViewGroup parent) {
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.row_grocery, parent,
                    false);
        }
        String currentGrocery = groceryList.get(position);
        ((TextView) listView.findViewById(R.id.groceryName)).setText(currentGrocery);

        return listView;
    }
}
