package com.example.takemypackage.Entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

public class PendingParcel {

    private List<DeliveryPerson> optionalDeliveries;
    private Parcel parcelDetails;

    public PendingParcel(Parcel _parcelDetails) {
        parcelDetails = _parcelDetails;
        optionalDeliveries = new ArrayList<>();
    }

    public List<DeliveryPerson> getOptionalDeliveries() {
        return optionalDeliveries;
    }


    public Parcel getParcelDetails() {
        return parcelDetails;
    }


    public void setOptionalDeliveries(List<DeliveryPerson> optionalDeliveries) {
        this.optionalDeliveries = optionalDeliveries;
    }

    public void setParcelDetails(Parcel _parcelDetails) {
        parcelDetails = _parcelDetails;
    }

    public void addOptionalDelivery (DeliveryPerson deliveryPerson){
        optionalDeliveries.add(deliveryPerson);
    }

    public void authorize(DeliveryPerson deliveryPerson, boolean authorized) {
        int index = optionalDeliveries.indexOf(deliveryPerson);
        deliveryPerson.setAuthorized(authorized);
        optionalDeliveries.set(index, deliveryPerson);
    }
}
