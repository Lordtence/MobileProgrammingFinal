package com.example.finalproject;

public class GoogleBookModel {
    private String title, authors, subtitle, description, publisher;

    GoogleBookModel()
    {
        title = "";
        authors = "";
        subtitle = "";
        description = "";
        publisher = "";
    }

    public void addTitle(String t)
    {
        title = t;
    }
    public void addAuthors(String a)
    {
        authors = a;
    }
    public void addSubtitle(String s)
    {
        subtitle = s;
    }
    public void addDescription(String d)
    {
        description = d;
    }
    public void addPublisher(String p)
    {
        publisher = p;
    }
    public String getAuthors()
    {
        return authors;
    }
    public String getTitle()
    {
        return title;
    }
    public String getSubtitle()
    {
        return subtitle;
    }
    public String getDescription()
    {
        return description;
    }
    public String getPublisher()
    {
        return publisher;
    }
}
