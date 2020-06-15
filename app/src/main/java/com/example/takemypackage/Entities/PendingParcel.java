package com.example.takemypackage.Entities;


import java.util.Date;
import java.util.Dictionary;
import java.util.List;

public class PendingParcel extends Parcel {
   private class Deliverer{
      private String name;
      private String phone;
      private boolean isAuthorized;
   }
   private List<Deliverer> optionalDeliverers;

   public PendingParcel(String recipientPhone, String recipientFirstName, String recipientLastName,
                        String recipientAddress, ParcelType type, boolean fragile, ParcelWeight weight,
                        String parcelID, String locationOfStorage, Status status) {
      super(recipientPhone, recipientFirstName, recipientLastName, recipientAddress, type, fragile, weight, parcelID, locationOfStorage, status);
   }
}
