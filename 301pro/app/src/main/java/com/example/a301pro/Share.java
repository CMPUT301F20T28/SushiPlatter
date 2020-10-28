package com.example.a301pro;

public class Share {
    private int imageId;
    private String book_name;
    private String des;
    private String sit;
    private User onwer;

    public Share(int imageId,String book_name, String des, String sit, User onwer) {
        this.imageId =imageId;
        this.book_name = book_name;
        this.des = des;
        this.sit = sit;
        this.onwer = onwer;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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

    public User getOnwer() {
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

    public void setOnwer(User onwer) {
        this.onwer = onwer;
    }
}
