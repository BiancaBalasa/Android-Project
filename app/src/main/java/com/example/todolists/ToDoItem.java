package com.example.todolists;

public class ToDoItem {
    private String title;
    private boolean isChecked;

    public ToDoItem(String title) {
        this.title = title;
        this.isChecked = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

