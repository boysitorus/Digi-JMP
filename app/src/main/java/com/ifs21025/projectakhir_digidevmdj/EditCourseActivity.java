package com.ifs21025.projectakhir_digidevmdj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ifs21025.projectakhir_digidevmdj.databinding.ActivityEditCourseBinding;

public class EditCourseActivity extends AppCompatActivity {
    ActivityEditCourseBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        Integer id = intent.getIntExtra("ID", 0);
        Cursor cursor = databaseHelper.getCourse(id);
        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            // Extract course details from the cursor
            String title = cursor.getString(2);
            String description = cursor.getString(3);
            String sks = String.valueOf(cursor.getInt(4));
            String grade = cursor.getString(5);

            // Display course details in the TextView
            binding.etTitle.setText(title);
            binding.etDescription.setText(description);
            binding.etSks.setText(sks);
            binding.etGrade.setText(grade);

        } else {
            binding.textView4.setText("Course not found");
        }

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.etTitle.getText().toString().equals("") ||
                        binding.etDescription.getText().toString().equals("") ||
                        binding.etSks.getText().toString().equals("")){
                    Toast.makeText(EditCourseActivity.this, "Field Title, Description, dan SKS harus terisi", Toast.LENGTH_SHORT).show();
                } else {
                    String title = binding.etTitle.getText().toString();
                    String description = binding.etDescription.getText().toString();
                    Integer sks = Integer.parseInt(binding.etSks.getText().toString());
                    String grade = binding.etGrade.getText().toString();
                    Boolean update = databaseHelper.updateCourse(id, title, description, sks, grade);

                    if(update){
                        Toast.makeText(EditCourseActivity.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(EditCourseActivity.this, "Update Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}