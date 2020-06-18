/*
Java Workshop 2020
First Application
06/05/2020
Moshe Weizman 305343931
Aharon Packter 201530508
 */
package com.example.takemypackage.Entities;

/**
 * represents a package sent to a receiver including relevant information
 */
public class Parcel {


    /**
     * Optional types of parcel sent by the network
     */
    public enum ParcelType {
        ENVELOPE, SMALL_PARCEL, BIG_PARCEL
    }

    public enum Status {
        Registered, CollectionOffered, OnTheWay, Delivered
    }

    /**
     * Optional weight of the package
     */
    public enum ParcelWeight {
        UP_TO_500G, UP_TO_1KG, UP_TO_5KG, UP_TO_20KG
    }


    private ParcelType type;
    private boolean fragile;
    private ParcelWeight weight;
    private String locationOfStorage;
    private String parcelID;

    private String recipientPhone;
    private String recipientFirstName;
    private String recipientLastName;
    private String recipientAddress;

    //---------------------------------------Constructors------------------------------------------
    public Parcel() {
    }

    /**
     * @param recipientPhone     recipient's phone
     * @param recipientFirstName recipient's first name
     * @param recipientLastName  recipient's last name
     * @param recipientAddress   recipient's address
     * @param type               parcel type
     * @param fragile            is the parcel fragile
     * @param weight             maximum weight
     * @param parcelID           the serial number of the parcel on the database
     * @param locationOfStorage  the location the parcel is stored
     */
    public Parcel(String recipientPhone, String recipientFirstName, String recipientLastName, String recipientAddress,
                  ParcelType type, boolean fragile, ParcelWeight weight, String parcelID,
                  String locationOfStorage, Status status) {
        this.type = type;
        this.fragile = fragile;
        this.weight = weight;
        this.recipientPhone = recipientPhone;
        this.parcelID = parcelID;
        this.locationOfStorage = locationOfStorage;
        this.recipientFirstName = recipientFirstName;
        this.recipientLastName = recipientLastName;
        this.recipientAddress = recipientAddress;
    }
//------------------------------Getters------------------------------------------

    public String getRecipientFirstName() {
        return recipientFirstName;
    }

    public String getRecipientLastName() {
        return recipientLastName;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public ParcelType getType() {
        return type;
    }

    public boolean isFragile() {
        return fragile;
    }

    public ParcelWeight getWeight() {
        return weight;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public String getParcelID() {
        return parcelID;
    }

    public String getLocationOfStorage() {
        return locationOfStorage;
    }

//-------------------------------Setters--------------------------------------

    public void set_parcelID(String parcelID) {
        this.parcelID = parcelID;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public void setRecipientFirstName(String recipientFirstName) {
        this.recipientFirstName = recipientFirstName;
    }
//moshe
    public void setRecipientLastName(String recipientLastName) {
        this.recipientLastName = recipientLastName;
    }

    public void setType(ParcelType type) {
        this.type = type;
    }

    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public void setWeight(ParcelWeight weight) {
        this.weight = weight;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public void setLocationOfStorage(String locationOfStorage) {
        this.locationOfStorage = locationOfStorage;
    }
}
