package com.example.a301pro;

public class Share {
    private String book_name;
    private String des;
    private String sit;
    private String onwer;

    public Share(String book_name, String des, String sit, String onwer) {
        this.book_name = book_name;
        this.des = des;
        this.sit = sit;
        this.onwer = onwer;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getDes() {
        return des;
    }

    public String getSit() {
        return sit;
    }

    public String getOnwer() {
        return onwer;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setSit(String sit) {
        this.sit = sit;
    }

    public void setOnwer(String onwer) {
        this.onwer = onwer;
    }
}
