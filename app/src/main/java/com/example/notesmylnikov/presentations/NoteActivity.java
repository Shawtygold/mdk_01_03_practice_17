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
import com.example.notesmylnikov.datas.RepoNotes;
import com.example.notesmylnikov.domains.models.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

//    RepoNotes notesRepository;
    Note note;
    EditText etTitle, etText;
    TextView tvDate;
    View btnSelectColor, btnBack, btnTrash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note);

//        notesRepository = new RepoNotes(this);

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
            String noteIdentificator = arguments.getString("data");
            note = notesRepository.getNote(noteIdentificator);

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
                    note = new Note(notesRepository.getNewId(), Title, Text, FormatForDateNow.format(DateNow), "Black");
                }
                else{
                    note = new Note(note.id, Title, Text, FormatForDateNow.format(DateNow), "Black");
                }

                notesRepository.saveNote(note);
            }

            finish();
        });

        btnTrash.setOnClickListener(v -> {
            notesRepository.deleteNote(note.id);
            finish();
            Toast.makeText(this, "Заметка удалена", Toast.LENGTH_SHORT).show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

//    public void onColorPickerClick(View view){
//        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(this);
//        builder.setTitle("Выберите цвет")
//                .setPositiveButton("OK", (colorPickerView, color) -> {
//                    // Использовать выбранный цвет
//                    int selectedColor = color;
//                    view.setBackgroundColor(selectedColor);
//                })
//                .setNegativeButton("Отмена", (colorPickerView, color) -> {
//                    // Отмена
//                })
//                .show();
//    }
}