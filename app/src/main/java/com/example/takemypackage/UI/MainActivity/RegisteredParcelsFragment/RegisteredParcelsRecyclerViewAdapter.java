package com.example.takemypackage.UI.MainActivity.RegisteredParcelsFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Entities.Parcel;
import com.example.takemypackage.R;

import java.util.List;

public class RegisteredParcelsRecyclerViewAdapter extends RecyclerView.Adapter<RegisteredParcelsRecyclerViewAdapter.RegisteredParcelsViewHolder> {
   private List<Parcel> _registeredParcels;

   public static class RegisteredParcelsViewHolder extends RecyclerView.ViewHolder {
      // each data item is just a string in this case
      public TextView addressTextView;
      public TextView weightTextView;
      public TextView distanceFromMeTextView;
      public TextView storageTimeTextView;

      public RegisteredParcelsViewHolder(@NonNull View itemView) {
         super(itemView);
         addressTextView = (TextView) itemView.findViewById(R.id.addressTextView);
         weightTextView = (TextView) itemView.findViewById(R.id.weightTextView);
         distanceFromMeTextView = (TextView) itemView.findViewById(R.id.distanceFromMeTextView);
         storageTimeTextView = (TextView) itemView.findViewById(R.id.storageTimeTextView);
      }
   }

   public RegisteredParcelsRecyclerViewAdapter(List<Parcel> registeredParcels) {
      _registeredParcels = registeredParcels;
   }

   @NonNull
   @Override
   public RegisteredParcelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_parcel_itemview, parent, false);
      return new RegisteredParcelsViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull RegisteredParcelsViewHolder holder, int position) {
      holder.addressTextView.setText(_registeredParcels.get(position).getRecipientAddress());
      holder.weightTextView.setText(_registeredParcels.get(position).getWeight().toString());
      //to do: inserting distance from me and storage time
   }

   @Override
   public int getItemCount() {
      return _registeredParcels.size();
   }


}
