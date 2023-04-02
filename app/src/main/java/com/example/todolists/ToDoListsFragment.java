package com.example.todolists;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ToDoListsFragment extends Fragment {

    private RecyclerView todoListRecyclerView;
    private FloatingActionButton addTodoFloatingButton;
    private ToDoListAdapter toDoListAdapter;
    private List<ToDoItem> toDoItemList;

    public ToDoListsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void loadToDoItemCheckedStateFromSharedPreferences(ToDoItem toDoItem) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        boolean isChecked = sharedPreferences.getBoolean(toDoItem.getTitle(), false);
        toDoItem.setChecked(isChecked);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_lists, container, false);

        todoListRecyclerView = view.findViewById(R.id.todo_list_recycler_view);
        todoListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        toDoItemList = new ArrayList<>();
        toDoListAdapter = new ToDoListAdapter(toDoItemList);
        todoListRecyclerView.setAdapter(toDoListAdapter);

        // Retrieve the to-do list from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        Set<String> stringSet = sharedPreferences.getStringSet("todo_list", new HashSet<String>());

        List<ToDoItem> toDoItemList = new ArrayList<>();
        for (String title : stringSet) {
            ToDoItem toDoItem = new ToDoItem(title);
            loadToDoItemCheckedStateFromSharedPreferences(toDoItem);
            toDoItemList.add(toDoItem);
        }
        toDoListAdapter.updateList(toDoItemList);

        addTodoFloatingButton = view.findViewById(R.id.add_todo_floating_button);
        addTodoFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an AlertDialog with an EditText for the user to enter the To-Do item title
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("New To-Do Item");

                // Set up the EditText
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the OK button to add the new To-Do item
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = input.getText().toString();
                        toDoItemList.add(new ToDoItem(title));
                        toDoListAdapter.notifyDataSetChanged();

                        // Save the to-do list to shared preferences
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        Set<String> stringSet = new HashSet<>();
                        for (ToDoItem item : toDoItemList) {
                            stringSet.add(item.getTitle());
                            editor.putBoolean(item.getTitle(), item.isChecked());
                        }

                        editor.putStringSet("todo_list", stringSet);
                        editor.apply();
                    }
                });

                // Set up the Cancel button to dismiss the dialog
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                // Show the dialog
                builder.show();
            }
        });




        return view;
    }
}