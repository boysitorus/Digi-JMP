package com.ifs21025.projectakhir_digidevmdj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.ifs21025.projectakhir_digidevmdj.databinding.ActivityDetailCourseBinding;

public class DetailCourseActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ActivityDetailCourseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailCourseBinding.inflate(getLayoutInflater());
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
    }
}