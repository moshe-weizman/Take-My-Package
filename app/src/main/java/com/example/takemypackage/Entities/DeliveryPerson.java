package com.example.takemypackage.Entities;

public class DeliveryPerson {
   private String name;
   private String phone;
   private boolean isAuthorized;

   public DeliveryPerson(String name, String phone) {
      this.name = name;
      this.phone = phone;
   }

   public DeliveryPerson(Member member) {
      name = member.getfName() + " " + member.getlName();
      phone = member.getPhone();
   }

   public String getName() {
      return name;
   }

   public String getPhone() {
      return phone;
   }

   public boolean isAuthorized() {
      return isAuthorized;
   }

   public void setAuthorized(boolean authorized) {
      isAuthorized = authorized;
   }
}
