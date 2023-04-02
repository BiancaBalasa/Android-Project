package com.example.todolists;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ToDoItemViewHolder> {
    private static List<ToDoItem> todoList;

    public ToDoListAdapter(List<ToDoItem> todoList) {
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public ToDoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_todo, parent, false);
        return new ToDoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoItemViewHolder holder, int position) {
        ToDoItem toDoItem = todoList.get(position);
        holder.titleTextView.setText(toDoItem.getTitle());
        holder.checkBox.setChecked(toDoItem.isChecked());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                toDoItem.setChecked(isChecked);
                // Save the updated to-do item to shared preferences
                SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(toDoItem.getTitle(), isChecked);
                editor.apply();
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void updateList(List<ToDoItem> updatedList) {
        todoList = updatedList;
        notifyDataSetChanged();
    }

    public static class ToDoItemViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView titleTextView;

        public ToDoItemViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.todo_item_checkbox);
            titleTextView = itemView.findViewById(R.id.todo_item_title);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    int position = getAdapterPosition();
                    // if the position exists then get the position of your element and set the checkbox
                    if (position != RecyclerView.NO_POSITION) {
                        ToDoItem toDoItem = todoList.get(position);
                        toDoItem.setChecked(isChecked);
                    }
                }
            });
        }
    }
}
