/**
 * Stijn Buiteman
 * <Lists>
 */
package com.example.stijn.lists;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains an onClick method for the button: addToDoButton to add items to a to-do
 * ArrayList toDos, and an onLongClick method to remove items from toDos. The ArrayList is saved
 * in a textfile after each addToDoButton click and this textfile is read at each onCreate call.
 * The toDos are passed to AdapterActivity.
 */
public class MainActivity extends AppCompatActivity {

    // object belonging to class
    public Button addToDoButton;
    public ArrayList<String> toDos;
    public ListView toDoListView;
    public AdapterActivity myAdapter;
    public EditText userInput;

    /**
     * onCreate reads the textfile containing the old to-do entries and passes them to the
     * adapter. Two click listeners are instantiated to handle an add and remove item event.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDos = new ArrayList<>();
        addToDoButton = (Button) findViewById(R.id.addToDoButton);
        userInput = (EditText) findViewById(R.id.listInputET);
        toDoListView = (ListView) findViewById(R.id.toToListView);

        //reading text from file listSafe.text to restore toDos
        try {
            // read the file and add a to-do for each newline.
            Scanner scan = new Scanner(openFileInput("listSafe.txt"));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                toDos.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // make AdapterActivity object of this class to enable calls to it,link with ListView
        myAdapter = new AdapterActivity(this, toDos);
        toDoListView.setAdapter(myAdapter);

        /**
         * OnClickListener for addToDoButton, passing new to-do to the adapter and adding the
         * new entry to listSafe.txt.
         */
        addToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = userInput.getText().toString();

                //user must input something
                if (input != null && !input.isEmpty()) {
                    myAdapter.add(userInput.getText().toString());
                    userInput.setText("");

                    // call writeTextFile to add item to file
                    writeTextFile();

                    // tell user nothing to add
                } else {
                    Toast.makeText(MainActivity.this, "Nothing to add.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        /**
         * onItemLongClick listener that handles removing to-dos via long-clicking them. The
         * adapter is notified of the change.
         */
        toDoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // remove the item from the array
                toDos.remove(position);

                // call writeTextFile to remove item from file
                writeTextFile();

                // call a UI update for the list
                myAdapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_LONG).show();
                return true;
            }
        });

        /**
         * on ListView item click a toast message is displayed
         */
        toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.w("List was pressed", "click heard");
                final String toDo = toDos.get(position);
                Log.w(toDo, "string");
                //final String toDo = (String) toDoListView.getItemAtPosition(position);

                String text = "You clicked on " + toDo;
                Toast toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
                toast.show();

                Log.w(toDo, "string");

                Intent myIntent = new Intent(MainActivity.this, EditListActivity.class);
                myIntent.putExtra("key", toDo);
                startActivity(myIntent);
            }
        });


    }

    /**
     * rewrites the listSafe.txt file with toDos from which an item was removed to store it's
     * removal
     */
    public void writeTextFile(){
        // write input to listSafe.txt, each item on a new line
        try {
            PrintStream out = new PrintStream(openFileOutput("listSafe.txt", MODE_PRIVATE));
            int i = 0;
            while (i < toDos.size()) {
                out.println(toDos.get(i));
                i++;
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}