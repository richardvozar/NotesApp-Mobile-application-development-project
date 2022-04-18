package com.example.jegyzetek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {

    Intent data;
    EditText editTitleOfNoteET, editContentOfNoteET;
    FloatingActionButton saveEditedNoteFABTN;

    TextView dateTV;
    String dateSTR;


    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editTitleOfNoteET = findViewById(R.id.editTitleOfNote);
        editContentOfNoteET = findViewById(R.id.editContentOfNote);
        saveEditedNoteFABTN = findViewById(R.id.saveEditedNoteButton);

        dateTV = findViewById(R.id.editNoteDate);

        data = getIntent();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar = findViewById(R.id.toolbarEditNote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String noteTitle = data.getStringExtra("title");
        String noteContent = data.getStringExtra("content");

        long dateInMillis = data.getLongExtra("dateInMillis", new Date().getTime()); // getting time of last edited, default: now

        Date lastEditedDate = new Date(dateInMillis);
        dateSTR = lastEditedDate.toString();
        dateTV.setText("Utolsó frissítés: " + dateSTR);


        editContentOfNoteET.setText(noteContent);
        editTitleOfNoteET.setText(noteTitle);



        saveEditedNoteFABTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newTitle = editTitleOfNoteET.getText().toString();
                String newContent = editContentOfNoteET.getText().toString();

                if (newTitle.isEmpty() || newContent.isEmpty()) {
                    Toast.makeText(EditNoteActivity.this, "Valamit üresen hagytál", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(data.getStringExtra("noteId"));

                    Map<String, Object> note = new HashMap<>();

                    note.put("title", newTitle);
                    note.put("content", newContent);

                    Date timestamp = new Date();
                    note.put("dateInMillis", dateInMillis);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(EditNoteActivity.this, "Sikeres módosítás", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditNoteActivity.this, NotesActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditNoteActivity.this, "Sikertelen módosítás", Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }



}