/*
Java Workshop 2020
First Application
25/06/2020
Moshe Weizman 305343931
Aharon Packter 201530508
 */

package com.example.takemypackage.Entities;

public class DeliveryPerson {
    private String name;
    private String phone;
    private boolean authorized;
    private String imageFirebaseUri;

    //------------------------------------constructors----------------------------------------------------
    public DeliveryPerson() {
    }

    public DeliveryPerson(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public DeliveryPerson(Member member) {
        name = member.getfName() + " " + member.getlName();
        phone = member.getPhone();
        imageFirebaseUri = member.getImageFirebaseUri();
    }

    //-------------------------------------------getters and setters------------------------------------------------------
    public String getImageFirebaseUri() {
        return imageFirebaseUri;
    }

    public void setImageFirebaseUri(String imageFirebaseUri) {
        this.imageFirebaseUri = imageFirebaseUri;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
