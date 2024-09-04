package com.project.android.qr;

public class ContactModel {
    private int _id;
    private String name;
    private String email;
    private String street;
    private String city;
    private String state;
    private String country;
    private String postcode;
    private String phone;
    private String cell;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    private String dob;
    private String uid;
    private String img_lg;
    private String img_md;
    private String img_sm;
    private String nat;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImg_lg() {
        return img_lg;
    }

    public void setImg_lg(String img_lg) {
        this.img_lg = img_lg;
    }

    public String getImg_md() {
        return img_md;
    }

    public void setImg_md(String img_md) {
        this.img_md = img_md;
    }

    public String getImg_sm() {
        return img_sm;
    }

    public void setImg_sm(String img_sm) {
        this.img_sm = img_sm;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }
}
