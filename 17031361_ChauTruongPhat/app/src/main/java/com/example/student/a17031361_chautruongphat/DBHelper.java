package com.example.student.a17031361_chautruongphat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "BookList", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Book(Id interger primary key,"+" Title text, AuthorName text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Book");
        onCreate(db);
    }

    public boolean insertBook(Book book){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Id", book.getId());
        contentValues.put("Title", book.getTitle());
        contentValues.put("AuthorName", book.getAuthorName());
        db.insert("Book",null, contentValues);
        return true;
    }

    public Book getBookById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Book where Id = " + id, null);
        if(cursor != null)
            cursor.moveToFirst();
        Book book = new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();
        return book;
    }

    public ArrayList<Book> getBooks(){
        ArrayList<Book> books = new ArrayList<Book>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Book", null);
        if(cursor != null)
            cursor.moveToFirst();
        while (cursor.isAfterLast() == false ){
            books.add(new Book(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return books;
    }

    public boolean updateBook(Book book){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Title", book.getTitle());
        values.put("AuthorName", book.getAuthorName());

        db.update("Book", values, "Id" + " = ?", new String[] { String.valueOf(book.getId()) });
        db.close();
        return true;
    }

    public boolean deleteBook(int bookId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Book", "Id" + " = ?", new String[] { String.valueOf(bookId) });
        db.close();
        return true;
    }

}
