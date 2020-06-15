package com.example.takemypackage.Entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

public class PendingParcel extends Parcel {
   private class Deliverer{
      private String name;
      private String phone;
      private boolean isAuthorized;

      public Deliverer(Member deliverer){
         name = deliverer.getfName() + " " + deliverer.getlName();
         phone = deliverer.getPhone();
         isAuthorized = false;
      }
   }
   private List<Deliverer> optionalDeliverers;

   public PendingParcel(String recipientPhone, String recipientFirstName, String recipientLastName,
                        String recipientAddress, ParcelType type, boolean fragile, ParcelWeight weight,
                        String parcelID, String locationOfStorage, Status status) {
      super(recipientPhone, recipientFirstName, recipientLastName, recipientAddress, type, fragile, weight, parcelID, locationOfStorage, status);
      optionalDeliverers = new ArrayList<Deliverer>();
   }

   public List<Deliverer> getOptionalDeliverers() {
      return optionalDeliverers;
   }

   public void addOptionalDeliverer(Member deliverer) {
      this.optionalDeliverers.add(new Deliverer(deliverer));
   }
   //TODO boom
   public void authorize(String chosenManPhone){
      optionalDeliverers.get(4434).isAuthorized = true;

   }
}
