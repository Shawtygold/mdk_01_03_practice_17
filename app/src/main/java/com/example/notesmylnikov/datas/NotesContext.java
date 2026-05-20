package com.example.notesmylnikov.datas;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.notesmylnikov.domains.models.Note;

import java.util.ArrayList;

public class NotesContext {
    public static ArrayList<Note> AllNotes(){
        ArrayList<Note> allNotes = new ArrayList<>();
        Cursor cursor = DbContext.sqLiteDatabase.query("Notes", null, null, null, null, null, null);
        if (cursor.moveToFirst() == false){
            return allNotes;
        }

        do {
            Note note = new Note();

            note.id = cursor.getInt(0);
            note.title = cursor.getString(1);
            note.text = cursor.getString(2);
            note.date = cursor.getString(3);
            note.color = cursor.getString(4);

            allNotes.add(note);
        }
        while (cursor.moveToNext());

        cursor.close();
        return allNotes;
    }

    public static void Save(Note note, boolean update){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", note.title);
        contentValues.put("Text", note.text);
        contentValues.put("Date", note.date);
        contentValues.put("Color", note.color);

        if (update == false){
            DbContext.sqLiteDatabase.insert("Notes", null, contentValues);
        }
        else {
            DbContext.sqLiteDatabase.update("Notes", contentValues, "Id = ?", new String[] {String.valueOf(note.id)});
        }
    }

    public static void Delete(Integer noteId){
        DbContext.sqLiteDatabase.delete("Notes", "Id = ?", new String[] {String.valueOf(noteId)});
    }
}
