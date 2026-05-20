package com.example.notesmylnikov.datas;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.notesmylnikov.domains.models.Note;
import java.util.ArrayList;
import java.util.Set;
public class RepoNotes {
    private static final String KEY_NOTE_COUNT = "note_count";
    private SharedPreferences prefs;
    public static ArrayList<Note> Notes = new ArrayList<>();
    public RepoNotes(Context context){
        prefs = context.getSharedPreferences("MyNotes", Context.MODE_PRIVATE);
    }

    public int getNewId(){
        return prefs.getInt(KEY_NOTE_COUNT, 0);
    }

    public void saveNote(Note note){
        SharedPreferences.Editor editor = prefs.edit();

        String value = note.id + "@" + note.title + "@" + note.text + "@" + note.date + "@" + note.color;
        editor.putString("note_" + note.id + "_data", value);

        int count = prefs.getInt(KEY_NOTE_COUNT, 0);
        editor.putInt(KEY_NOTE_COUNT, count + 1);

        editor.apply();
    }

    public Note getNote(String id){
        String data = prefs.getString(id, "");
        String[] parts = data.split("@");
        return new Note(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4]);
    }

    public void deleteNote(int id){
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("note_" + id + "_data");
        editor.apply();
    }

    public Set<String> getAllNotesIds() {
        return prefs.getAll().keySet();
    }
}
