package com.example.student.a17031361_chautruongphat;

public class Book {
    private int Id;
    private String Title;
    private String AuthorName;

    public Book(int id, String title, String authorName) {
        Id = id;
        Title = title;
        AuthorName = authorName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }
}
