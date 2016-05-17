package edu.uek.mikeb.shoppinglist.entity;

import java.util.Date;
import java.util.List;


public class ShoppingList {

    private long id;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
