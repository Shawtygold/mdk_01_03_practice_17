package com.example.notesmylnikov.presentations;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.notesmylnikov.R;
import com.example.notesmylnikov.datas.NotesContext;
import com.example.notesmylnikov.datas.RepoNotes;
import com.example.notesmylnikov.domains.models.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class NoteActivity extends AppCompatActivity {

    Note note;
    EditText etTitle, etText;
    TextView tvDate;
    View btnSelectColor, btnBack, btnTrash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note);

        Date DateNow = new Date();
        SimpleDateFormat FormatForDateNow = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");

        btnSelectColor = findViewById(R.id.btn_select_color);
        btnBack = findViewById(R.id.btn_back);
        btnTrash = findViewById(R.id.btn_trash);
        etTitle = findViewById(R.id.et_title);
        etText = findViewById(R.id.et_text);
        tvDate = findViewById(R.id.tv_date);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null){
            Integer noteIdentificator = arguments.getInt("note_id");
            Optional<Note> note_opt = NotesContext.AllNotes().stream().filter(n -> n.id == noteIdentificator).findFirst();
            note = note_opt.orElseThrow();

            etTitle.setText(note.title);
            etText.setText(note.text);
        } else{
            btnTrash.setVisibility(View.GONE);
        }

        tvDate.setText("Отредактировано: " + FormatForDateNow.format(DateNow));

        btnSelectColor.setOnClickListener(v -> {
            Toast.makeText(this, "Выбор цвета недоступен", Toast.LENGTH_SHORT).show();
        });

        btnBack.setOnClickListener(v -> {
            boolean newNote = false;
            String Title = etTitle.getText().toString();
            String Text = etText.getText().toString();

            if (Text.replace(" ", "")
                    .replace("\r", "")
                    .replace("\n", "")
                    .isEmpty()){
                Toast.makeText(this, "Нечего сохранять", Toast.LENGTH_SHORT).show();
            } else{
                if (note == null){
                    note = new Note(Title, Text, FormatForDateNow.format(DateNow), "Black");
                    NotesContext.Save(note, false);
                }
                else{
                    note = new Note(note.id, Title, Text, FormatForDateNow.format(DateNow), "Black");
                    NotesContext.Save(note, true);
                }
            }

            finish();
        });

        btnTrash.setOnClickListener(v -> {
            NotesContext.Delete(note.id);
            finish();
            Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show();
        });
    }
}