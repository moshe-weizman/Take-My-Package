package com.example.takemypackage.Entities;


import java.util.Date;
import java.util.Dictionary;
import java.util.List;

public class PendingParcel {

   private List<DeliveryPerson> optionalDeliveries;
   private List<DeliveryPerson> authorizedDeliveries;
   private Parcel parcelDetails;

   public PendingParcel(Parcel parcelDetails) {
      this.parcelDetails = parcelDetails;
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

   public void authorize(DeliveryPerson deliveryPerson, boolean authorized){
      int index = optionalDeliveries.indexOf(deliveryPerson);
      deliveryPerson.setAuthorized(authorized);
      optionalDeliveries.set(index, deliveryPerson);
   }
}
