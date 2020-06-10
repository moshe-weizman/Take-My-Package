package com.example.takemypackage.Entities;

import androidx.annotation.NonNull;

public class Member {
   private String fName;
   private String lName;
   private String id;
   private String address;

   //---------------------------------------------------------------------------------------------------------------------
   public Member() {
   }

   public Member(String fName, String lName, String id, String address) {
      this.fName = fName;
      this.lName = lName;
      this.id = id;
      this.address = address;
   }

   //---------------------------------------------------------------------------------------------------------------------
   public String getfName() {
      return fName;
   }

   public void setfName(String fName) {
      this.fName = fName;
   }

   public String getlName() {
      return lName;
   }

   public void setlName(String lName) {
      this.lName = lName;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   //-----------------------------------------Operations----------------------------------------------------------------------------

   @NonNull
   @Override
   public String toString() {
      return super.toString();
   }
}
