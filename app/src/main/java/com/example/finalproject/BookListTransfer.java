package com.example.finalproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookListTransfer extends MainActivity {
    private List<GoogleBookModel> TbookList;
    BookListTransfer(List<GoogleBookModel> inputBookList){
        TbookList = new ArrayList<>();
        TbookList.addAll(inputBookList);
    }

    public void setMainList()
    {
        setBookList(TbookList);
    }
}
