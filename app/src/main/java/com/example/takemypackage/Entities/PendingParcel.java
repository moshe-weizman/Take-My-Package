package com.example.takemypackage.Entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

public class PendingParcel {

   private List<DeliveryPerson> optionalDeliveries;
   private List<DeliveryPerson> authorizedDeliveries;
   private Parcel parcelDetails;

   public PendingParcel(Parcel parcelDetails) {
      this.parcelDetails = parcelDetails;
      optionalDeliveries = new ArrayList<>();
      authorizedDeliveries = new ArrayList<>();
   }

   public List<DeliveryPerson> getOptionalDeliveries() {
      return optionalDeliveries;
   }

   public List<DeliveryPerson> getAuthorizedDeliveries() {
      return authorizedDeliveries;
   }

   public Parcel getParcelDetails() {
      return parcelDetails;
   }


   public void setOptionalDeliveries(List<DeliveryPerson> optionalDeliveries) {
      this.optionalDeliveries = optionalDeliveries;
   }

   public void setAuthorizedDeliveries(List<DeliveryPerson> authorizedDeliveries) {
      this.authorizedDeliveries = authorizedDeliveries;
   }

   public void setParcelDetails(Parcel parcelDetails) {
      this.parcelDetails = parcelDetails;
   }

   public void authorize(DeliveryPerson deliveryPerson, boolean authorized){
      int index = optionalDeliveries.indexOf(deliveryPerson);
      deliveryPerson.setAuthorized(authorized);
      optionalDeliveries.set(index, deliveryPerson);
   }
}
