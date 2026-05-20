package com.example.notesmylnikov.presentations;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notesmylnikov.R;
import com.example.notesmylnikov.datas.DbContext;
import com.example.notesmylnikov.datas.NotesContext;
import com.example.notesmylnikov.datas.RepoNotes;
import com.example.notesmylnikov.domains.models.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class NotesActivity extends AppCompatActivity {

    DbContext dbContext;
    GridLayout itemsParent;
    View btnAddNotes;
    EditText etSearch;
//    RepoNotes notesRepository;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes);

        dbContext = new DbContext(this);
        LoadNotes(NotesContext.AllNotes());

        //notesRepository = new RepoNotes(this);
        notes = new ArrayList<>();

        btnAddNotes = findViewById(R.id.btn_add_notes);
        itemsParent = findViewById(R.id.gl_notes);
        etSearch = findViewById(R.id.et_search);

        btnAddNotes.setOnClickListener(v -> {
            Intent intentActivityNote = new Intent(this, NoteActivity.class);
            startActivity(intentActivityNote);
        });

        etSearch.setOnKeyListener(SearchListener);

        LoadNotes();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        etSearch.setText("");
        LoadNotes(NotesContext.AllNotes());
    }

    public void LoadNotes2(ArrayList<Note> notes){
        itemsParent.removeAllViews();

        for (int i = 0; i < notes.size(); i++){
            View item_notes = LayoutInflater.from(this).inflate(R.layout.item_note, itemsParent, false);

            TextView tvTitle = item_notes.findViewById(R.id.tv_title);
            TextView tvText = item_notes.findViewById(R.id.tv_text);
            TextView tvDate = item_notes.findViewById(R.id.tv_date);

            Note note = notes.get(i);
            tvTitle.setText(note.title);
            tvText.setText(note.text);
            tvDate.setText(note.date);

            item_notes.setOnClickListener(v -> {
                Intent intentActivityNote = new Intent(this, NoteActivity.class);
                intentActivityNote.putExtra("data", "note_" + note.id + "_data");
                startActivity(intentActivityNote);
            });

            itemsParent.addView(item_notes);
        }
    }

    public void LoadNotes(){
        itemsParent.removeAllViews();
        notes.clear();
        Set<String> allNotesIds = notesRepository.getAllNotesIds();

        for (String noteIdentificationString : allNotesIds){
            if (Objects.equals(noteIdentificationString, "note_count"))
                continue;

            View item_notes = LayoutInflater.from(this).inflate(R.layout.item_note, itemsParent, false);

            TextView tvTitle = item_notes.findViewById(R.id.tv_title);
            TextView tvText = item_notes.findViewById(R.id.tv_text);
            TextView tvDate = item_notes.findViewById(R.id.tv_date);

            Note note = notesRepository.getNote(noteIdentificationString);
            tvTitle.setText(note.title);
            tvText.setText(note.text);
            tvDate.setText(note.date);

            notes.add(note);

            item_notes.setOnClickListener(v -> {
                Intent intentActivityNote = new Intent(this, NoteActivity.class);
                intentActivityNote.putExtra("data", noteIdentificationString);
                startActivity(intentActivityNote);
                etSearch.setText("");
            });

            itemsParent.addView(item_notes);
        }
    }



    View.OnKeyListener SearchListener = new View.OnKeyListener(){
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event){
            String Search = etSearch.getText().toString();

            if (Search.replace(" ", "")
                    .replace("\r", "")
                    .replace("\n", "")
                    .isEmpty()) {
                LoadNotes(NotesContext.AllNotes());
                return false;
            }

            ArrayList<Note> FindNotes = NotesContext.AllNotes().stream().filter(
                    item -> item.text.contains(Search)
            ).collect(Collectors.toCollection(ArrayList::new));


//            ArrayList<Note> sortedFindNotes = new ArrayList<>();
//
//            for (int i = 0;i < FindNotes.size(); i++){
//                if (!sortedFindNotes.contains()){
//                    sortedFindNotes.add(FindNotes.get(i));
//                }
//            }
//    itemsParent.removeAllViews();
            LoadNotes2(FindNotes);

            return false;
        }
    };


}