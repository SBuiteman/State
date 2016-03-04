package com.example.stijn.lists;
/**
 * Created by Stijn on 23/02/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class contains an getView method which takes in the toDo list from MainActivity and
 * builds the ListView. In the getView method is an onClick method which registers clicks
 * on list items and displays a toast message in response.
 */
public class AdapterActivity extends ArrayAdapter<String> {
    public Context layoutMAContext;

    public AdapterActivity(Context context, ArrayList<String> toDos) {
        super(context, R.layout.single_row_layout, toDos);

        layoutMAContext = context;
    }

    /**
     * handles the layout of the ListView
     */
    public View getView(int position, View view, ViewGroup parent) {

        final String toDo = getItem(position);

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) layoutMAContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_row_layout, parent, false);
        }

        TextView textview = (TextView) view.findViewById(R.id.toDOTextView);

        textview.setText(toDo);

//        /**
//         * on ListView item click a toast message is displayed
//         */
//        View.OnClickListener toDoListener = new View.OnClickListener() {
//            public void onClick(View view) {
//                String text = "You clicked on "+ toDo;
//                Toast toast = Toast.makeText(layoutMAContext, text, Toast.LENGTH_SHORT);
//                toast.show();
//
//                Intent myIntent = new Intent(layoutMAContext, EditListActivity.class);
//                myIntent.putExtra("key", toDo);
//                layoutMAContext.startActivity(myIntent);
//            }
//        };

        // set OnClickListener and make items in ListView longclickable
        //view.setOnClickListener(toDoListener);
        view.setLongClickable(true);

        return view;
    }
}