package com.example.whattodo;

public class NewTaskModel {

    String title, content;
    boolean checked;

    public NewTaskModel(String title, String content){
        this.title = title;
        this.content = content;
        this.checked = false;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
