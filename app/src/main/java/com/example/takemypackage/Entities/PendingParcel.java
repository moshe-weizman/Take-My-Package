package com.example.takemypackage.Entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class PendingParcel {

    private HashMap<String, DeliveryPerson> optionalDeliveries;
    private Parcel parcelDetails;

    public PendingParcel() {
        this.optionalDeliveries = new HashMap<>();
    }

    public PendingParcel(Parcel _parcelDetails) {
        parcelDetails = _parcelDetails;
        optionalDeliveries = new HashMap<>();
    }
//
    public HashMap<String, DeliveryPerson> getOptionalDeliveries() {
        return optionalDeliveries;
    }


    public Parcel getParcelDetails() {
        return parcelDetails;
    }


    public void setOptionalDeliveries(HashMap<String, DeliveryPerson> optionalDeliveries) {
        this.optionalDeliveries = optionalDeliveries;
    }

    public void setParcelDetails(Parcel _parcelDetails) {
        parcelDetails = _parcelDetails;
    }

    public void addOptionalDelivery(DeliveryPerson deliveryPerson) {
        optionalDeliveries.put(deliveryPerson.getPhone(), deliveryPerson);
    }

//    public void authorize(DeliveryPerson deliveryPerson, boolean authorized) {
//
//        deliveryPerson.setAuthorized(authorized);
//
//    }
}
