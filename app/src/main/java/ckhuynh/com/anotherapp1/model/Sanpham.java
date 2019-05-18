package ckhuynh.com.anotherapp1.model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    public int id;
    public  String tensp;
    public Integer giasp;
    public  String motasp;
    public  String hinhanhsp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Integer getGiasp() {
        return giasp;
    }

    public void setGiasp(Integer giasp) {
        this.giasp = giasp;
    }

    public String getMotasp() {
        return motasp;
    }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }

    public int getIdloaisp() {
        return idloaisp;
    }

    public void setIdloaisp(int idloaisp) {
        this.idloaisp = idloaisp;
    }

    public Sanpham(int id, String tensp, Integer giasp, String motasp, String hinhanhsp, int idloaisp) {
        this.id = id;
        this.tensp = tensp;
        this.giasp = giasp;
        this.motasp = motasp;
        this.hinhanhsp = hinhanhsp;
        this.idloaisp = idloaisp;
    }

    public int idloaisp;



}
