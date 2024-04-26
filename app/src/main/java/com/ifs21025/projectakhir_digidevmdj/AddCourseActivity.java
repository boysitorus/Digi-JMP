package com.ifs21025.projectakhir_digidevmdj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ifs21025.projectakhir_digidevmdj.databinding.ActivityAddCourseBinding;

public class AddCourseActivity extends AppCompatActivity {

    ActivityAddCourseBinding binding;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCourseBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");

        binding.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.etTitle.getText().toString().equals("") ||
                binding.etDescription.getText().toString().equals("") ||
                binding.etSks.getText().toString().equals("")){
                    Toast.makeText(AddCourseActivity.this, "Field Title, Description, dan SKS harus terisi", Toast.LENGTH_SHORT).show();
                } else {
                    String title = binding.etTitle.getText().toString();
                    String description = binding.etDescription.getText().toString();
                    Integer sks = Integer.parseInt(binding.etSks.getText().toString());
                    String grade = binding.etGrade.getText().toString();

                    Boolean insert = databaseHelper.addCourse(email, title, description, sks, grade);
                    if(insert){
                        Toast.makeText(getApplicationContext(), "Berhasil menambahkan Course", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("EMAIL", email);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Gagal menambahkan Course", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}