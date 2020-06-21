package com.example.takemypackage.Entities;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class HistoryParcel {
    private DeliveryPerson deliveryPerson;
    private Parcel parcelDetails;
    private Calendar dateCollected;

    public HistoryParcel() {
    }

    public HistoryParcel(Parcel _parcelDetails, DeliveryPerson _deliveryPerson, Calendar _dateCollected) {
        parcelDetails = _parcelDetails;
        deliveryPerson = _deliveryPerson;
        dateCollected = _dateCollected;
    }

    public HistoryParcel(PendingParcel _parcelDetails, DeliveryPerson _deliveryPerson, Calendar _dateCollected) {
        parcelDetails = _parcelDetails.getParcelDetails();
        deliveryPerson = _deliveryPerson;
        dateCollected = _dateCollected;
    }

    public DeliveryPerson getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public Parcel getParcelDetails() {
        return parcelDetails;
    }

    public void setParcelDetails(Parcel parcelDetails) {
        this.parcelDetails = parcelDetails;
    }

    public Calendar getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(Calendar dateCollected) {
        this.dateCollected = dateCollected;
    }
}