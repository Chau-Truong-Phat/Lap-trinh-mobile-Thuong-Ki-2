package com.example.student.a17031361_chautruongphat;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper db;

    EditText et_Id, et_Title, et_AuthorName;
    Button btn_Exit, btn_Select, btn_Save, btn_Delete, btn_Update;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_dialog,null);

        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.setCancelable(false);

        db = new DBHelper(this);

        et_Id = (EditText)dialogView.findViewById(R.id.et_Id);
        et_Title = (EditText)dialogView.findViewById(R.id.et_Title);
        et_AuthorName = (EditText)dialogView.findViewById(R.id.et_AuthorName);

        gridView = (GridView)dialogView.findViewById(R.id.gridview);

        btn_Save = (Button)dialogView.findViewById(R.id.btn_Save);
        btn_Select = (Button)dialogView.findViewById(R.id.btn_Select);
        btn_Update = (Button)dialogView.findViewById(R.id.btn_Update);
        btn_Delete = (Button)dialogView.findViewById(R.id.btn_Delete);

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(et_Id.getText().toString());
                String title = et_Title.getText().toString();
                String authorName = et_AuthorName.getText().toString();
                Book book = new Book(id, title, authorName);
                if(db.insertBook(book))
                    Toast.makeText(getApplicationContext(),"Đã lưu thành công", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Lưu thất bại", Toast.LENGTH_LONG).show();
            }
        });

        btn_Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(et_Id.getText().toString());
                ArrayList<String> listString = new ArrayList<String>();

                if(et_Id.getText().toString().equals("")){
                    ArrayList<Book> books = new ArrayList<Book>();
                    books = db.getBooks();
                    if(books.size() > 0)
                        for (Book book : books){
                            listString.add(book.getId() + "");
                            listString.add(book.getTitle());
                            listString.add(book.getAuthorName());
                        }
                }
                else{
                    Book book = db.getBookById(id);
                    if(book != null){
                        listString.add(book.getId() + "");
                        listString.add(book.getTitle());
                        listString.add(book.getAuthorName());
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,listString);
                gridView.setAdapter(arrayAdapter);
            }
        });

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(et_Id.getText().toString());
                Book book = db.getBookById(id);
                book.setTitle(et_Title.getText().toString());
                book.setAuthorName(et_AuthorName.getText().toString());

                if (db.updateBook(book))
                    Toast.makeText(getApplicationContext(),"Đã lưu thành công", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Lưu thất bại", Toast.LENGTH_LONG).show();
            }
        });

        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(et_Id.getText().toString());
                if(db.deleteBook(id))
                    Toast.makeText(getApplicationContext(),"Xóa thành công", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Xóa thất bại", Toast.LENGTH_LONG).show();
            }
        });

        dialog.show();

//        Intent intent = new Intent(this,Dialog.class);
//        this.startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}
