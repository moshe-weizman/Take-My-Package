/*
Java Workshop 2020
First Application
25/06/2020
Moshe Weizman 305343931
Aharon Packter 201530508
 */

package com.example.takemypackage.Entities;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class HistoryParcel {
    private DeliveryPerson deliveryPerson;
    private Parcel parcelDetails;
    private Date dateCollected;
//------------------------------------constructors----------------------------------------------------

    public HistoryParcel() {
    }

    public HistoryParcel(Parcel _parcelDetails, DeliveryPerson _deliveryPerson, Date _dateCollected) {
        parcelDetails = _parcelDetails;
        deliveryPerson = _deliveryPerson;
        dateCollected = _dateCollected;
    }

    public HistoryParcel(PendingParcel _parcelDetails, DeliveryPerson _deliveryPerson, Date _dateCollected) {
        parcelDetails = _parcelDetails.getParcelDetails();
        deliveryPerson = _deliveryPerson;
        dateCollected = _dateCollected;
    }
//-------------------------------------------getters and setters------------------------------------------------------

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

    public Date getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(Date dateCollected) {
        this.dateCollected = dateCollected;
    }
}
