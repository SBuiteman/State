package com.example.stijn.lists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class EditListActivity extends AppCompatActivity {

    // object belonging to class
    public Button addToDoButton;
    public ArrayList<String> toDoList;
    public ListView toDoListView;
    public AdapterActivity myAdapter;
    public EditText userInput;
    public String list;
    public TextView title;
    public MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        toDoList = new ArrayList<>();
        addToDoButton = (Button) findViewById(R.id.addToDoButton);
        userInput = (EditText) findViewById(R.id.listInputET);
        toDoListView = (ListView) findViewById(R.id.toToListView);
        title = (TextView) findViewById(R.id.titleTV);
        list = getIntent().getStringExtra("key");
        mainActivity = new MainActivity();

        title.setText(list);

        //reading text from file listSafe.text to restore toDos
        try {
            // read the file and add a to-do for each newline.
            Scanner scan = new Scanner(openFileInput(list + "Safe.txt"));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                toDoList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // make AdapterActivity object of this class to enable calls to it,link with ListView
        myAdapter = new AdapterActivity(this, toDoList);
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
                    mainActivity.writeTextFile();

                    // tell user nothing to add
                } else {
                    Toast.makeText(EditListActivity.this, "Nothing to add.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
