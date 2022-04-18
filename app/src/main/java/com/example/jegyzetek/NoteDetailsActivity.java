package com.example.jegyzetek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class NoteDetailsActivity extends AppCompatActivity {

    private TextView titleOfNoteDetailTV, contentOfNoteDetailTV, dateOfNoteDetailTV;
    FloatingActionButton editNoteFABTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);


        titleOfNoteDetailTV = findViewById(R.id.titleOfNoteDetail);
        contentOfNoteDetailTV = findViewById(R.id.contentOfNoteDetail);
        dateOfNoteDetailTV = findViewById(R.id.noteDetailDate);
        editNoteFABTN = findViewById(R.id.goToEditNoteButton);

        Toolbar toolbar = findViewById(R.id.toolbarNoteDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();

        editNoteFABTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditNoteActivity.class);

                // from NotesActivity.java file --> I used intent.putExtra() method
                // here I get them
                intent.putExtra("title", data.getStringExtra("title"));
                intent.putExtra("content", data.getStringExtra("content"));
                intent.putExtra("noteId", data.getStringExtra("noteId"));

                view.getContext().startActivity(intent);

            }
        });

        contentOfNoteDetailTV.setText(data.getStringExtra("content"));
        titleOfNoteDetailTV.setText(data.getStringExtra("title"));

        //getting the date
        Date lastEditedDate = new Date(data.getLongExtra("dateInMillis", new Date().getTime()));
        dateOfNoteDetailTV.setText("Utolsó frissítés: " + lastEditedDate.toString());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


}