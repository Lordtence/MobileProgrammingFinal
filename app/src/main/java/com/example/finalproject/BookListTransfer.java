package com.example.finalproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookListTransfer extends MainActivity {
    private List<GoogleBookModel> bookList;
    BookListTransfer(List<GoogleBookModel> inputBookList){
        bookList = new ArrayList<>();
        bookList.addAll(inputBookList);
    }

    public void setMainList()
    {
        setBookList(bookList);
    }
}
