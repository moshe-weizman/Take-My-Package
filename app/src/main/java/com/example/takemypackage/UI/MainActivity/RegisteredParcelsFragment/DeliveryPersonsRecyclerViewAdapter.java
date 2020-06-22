package com.example.takemypackage.UI.MainActivity.RegisteredParcelsFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Entities.DeliveryPerson;
import com.example.takemypackage.R;

import java.util.ArrayList;
import java.util.HashMap;

public class DeliveryPersonsRecyclerViewAdapter extends RecyclerView.Adapter<DeliveryPersonsRecyclerViewAdapter.DeliveryPersonsViewHolder> {
   HashMap<String, DeliveryPerson> deliveryPersonList;

   public static class DeliveryPersonsViewHolder extends RecyclerView.ViewHolder {
      public TextView deliveryNameTextView;
      public TextView deliveryPhoneTextView;
      public CheckBox authorizationCheckBox;

      public DeliveryPersonsViewHolder(@NonNull View itemView) {
         super(itemView);
         deliveryNameTextView = itemView.findViewById(R.id.nameTextView);
         deliveryPhoneTextView = itemView.findViewById(R.id.phoneTextView);
         authorizationCheckBox = itemView.findViewById(R.id.authorizeCheckBox);
      }
   }

   public DeliveryPersonsRecyclerViewAdapter(HashMap<String, DeliveryPerson> deliveryPersonList) {
      this.deliveryPersonList = deliveryPersonList;
   }

   @NonNull
   @Override
   public DeliveryPersonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_delivery_person_itemview, parent, false);
      return new DeliveryPersonsViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull final DeliveryPersonsViewHolder holder, int position) {
      final DeliveryPerson deliveryPerson = deliveryPersonList.get(position);
      holder.deliveryPhoneTextView.setText(deliveryPerson.getPhone());
      holder.deliveryNameTextView.setText(deliveryPerson.getName());
      holder.authorizationCheckBox.setChecked(deliveryPerson.isAuthorized());
      holder.authorizationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            deliveryPerson.setIsAuthorized(isChecked);
            //TODO update the firebase that the delivery is authorized
         }
      });

   }

   @Override
   public int getItemCount() {
      return deliveryPersonList.size();
   }
}
