package com.ifs21025.projectakhir_digidevmdj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "SignLog.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "SignLog.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table users(email TEXT primary key, password TEXT)");
        MyDatabase.execSQL("create Table courses(id INTEGER primary key, email TEXT, title TEXT, description TEXT, sks INTEGER, grade TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists courses");
    }
    public Boolean insertData(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    public Cursor getCourses(String email){
        SQLiteDatabase myDatabase = this.getReadableDatabase();
        Cursor cursor = myDatabase.rawQuery("Select * from courses where email = ?", new String[]{email});
        return cursor;
    }

    public Cursor getCourse(Integer id){
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery("Select * from courses where id = ?", new String[]{String.valueOf(id)});
        return cursor;
    }

    public Boolean addCourse(String email, String title, String description, Integer sks, String grade){
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", email);
        values.put("title", title);
        values.put("description", description);
        values.put("sks", sks);
        values.put("grade", grade);

        long newRowId = myDatabase.insert("courses", null, values);
        myDatabase.close();

        return newRowId != -1;

    }

    public Boolean updateCourse(Integer id, String newTitle, String newDescription, Integer newSks, String newGrade){
        SQLiteDatabase myDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Set new values for the course if needed

        values.put("title", newTitle);
        values.put("description", newDescription);
        values.put("sks", newSks);
        values.put("grade", newGrade);

        int rowsAffected = myDatabase.update("courses", values, "id = ?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }

    public Boolean deleteCourse(Integer id){
        SQLiteDatabase myDatabase = this.getWritableDatabase();

        // Perform the delete operation
        int rowsAffected = myDatabase.delete("courses", "id = ?", new String[]{String.valueOf(id)});

        // Close the database connection
        myDatabase.close();

        // Return true if rows were affected (deletion successful), false otherwise
        return rowsAffected > 0;
    }
}