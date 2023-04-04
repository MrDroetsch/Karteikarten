package me.bunnykick.utils;

import me.bunnykick.db.Note;

import java.util.Date;

public class DummyNote extends Note {

    public DummyNote(String title) {
        super(title, null, 0);
    }

    @Override
    public String getTitle() {
        return "";
    }

    public String getContent() {
        return title;
    }

}
