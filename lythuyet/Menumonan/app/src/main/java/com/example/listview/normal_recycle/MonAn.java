package com.example.listview.normal_recycle;

import java.util.ArrayList;

public class MonAn {
    String tenmonan;
    int soluong =0;
    int soluonggiamgia = 0;
    int image_resid = 0;
    int giahientai = 0;
    int giachuagiam = 0;
    boolean thongtinchitiet;
    float rating = 0;

    public int getGiahientai() {
        return giahientai;
    }

    public void setGiahientai(int giahientai) {
        this.giahientai = giahientai;
    }

    public int getGiachuagiam() {
        return giachuagiam;
    }

    public void setGiachuagiam(int giachuagiam) {
        this.giachuagiam = giachuagiam;
    }

    public MonAn(String tenmonan, int soluong, int soluonggiamgia, int image_resid) {
        this.tenmonan = tenmonan;
        this.soluong = soluong;
        this.soluonggiamgia = soluonggiamgia;
        this.image_resid = image_resid;
        this.thongtinchitiet = false;
    }
    public MonAn(String tenmonan,int giahientai,int giachuagiam,int image_resid,float rating){
        this.tenmonan = tenmonan;
        this.giahientai = giahientai;
        this.giachuagiam = giachuagiam;
        this.image_resid  = image_resid;
        this.rating = rating;
        this.thongtinchitiet = true;
    }
    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }



    public int getSoluonggiamgia() {
        return soluonggiamgia;
    }

    public void setSoluonggiamgia(int soluonggiamgia) {
        this.soluonggiamgia = soluonggiamgia;
    }
}
