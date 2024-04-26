package com.ifs21025.projectakhir_digidevmdj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.ifs21025.projectakhir_digidevmdj.databinding.ActivityMainBinding;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    ActivityMainBinding binding;
    DatabaseHelper databaseHelper;
    ArrayList<String> title;
    ArrayList<Integer> id;
    CourseAdapter courseAdapter;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");

        databaseHelper = new DatabaseHelper(this);

        id = new ArrayList<>();
        title = new ArrayList<>();
        recyclerView = binding.rvCourse;
        courseAdapter = new CourseAdapter(this, id, title);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.addItemClickListener(this::onItemClick);
        displayData();

        binding.fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddCourseActivity.class);
                intent.putExtra("EMAIL", email);
                startActivity(intent);
            }
        });

        binding.appbarMain.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.mainMenuInfo){
                    new AlertDialog
                            .Builder(MainActivity.this)
                            .setTitle("Ku-Course")
                            .setMessage("Aplikasi manajemen course, dimana aplikasi ini memiliki fungsi untuk melist, menambah, edit, dan menghapus course mulai dari judul, deskripsi, sks, dan nilai course nya")
                            .create()
                            .show();
                    return true;
                } else if (item.getItemId() == R.id.mainMenuLogOut){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }

    void onItemClick(Integer id){
        dialogAction(id);
    }

    public void dialogAction(int id){
        new AlertDialog.Builder(this)
                .setTitle("Option")
                .setMessage("")
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), EditCourseActivity.class);
                        intent.putExtra("ID", id);
                        startActivity(intent);
                    }
                })
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Boolean delete = databaseHelper.deleteCourse(id);
                        if(delete){
                            Toast.makeText(MainActivity.this, "Berhasil menghapus course", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Gagal menghapus course", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).create().show();
    }

    private void displayData() {
        Cursor cursor = databaseHelper.getCourses(email);
        if (cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "Belum ada data course", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                id.add(cursor.getInt(0));
                title.add(cursor.getString(2));
            }
        }
    }
}