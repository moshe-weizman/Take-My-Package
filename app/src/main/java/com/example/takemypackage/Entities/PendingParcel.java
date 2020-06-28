/*
Java Workshop 2020
First Application
25/06/2020
Moshe Weizman 305343931
Aharon Packter 201530508
 */

package com.example.takemypackage.Entities;

import java.util.HashMap;

public class PendingParcel {

    private HashMap<String, DeliveryPerson> optionalDeliveries;
    private Parcel parcelDetails;

    //------------------------------------constructors----------------------------------------------------

    public PendingParcel() {
        this.optionalDeliveries = new HashMap<>();
    }

    public PendingParcel(Parcel _parcelDetails) {
        parcelDetails = _parcelDetails;
        optionalDeliveries = new HashMap<>();
    }

    //-------------------------------------------getters and setters------------------------------------------------------

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
}
