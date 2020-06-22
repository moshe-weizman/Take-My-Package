package com.example.takemypackage.UI.MainActivity.RegisteredParcelsFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.Entities.Parcel;
import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;

import java.util.List;

public class RegisteredParcelsRecyclerViewAdapter extends RecyclerView.Adapter<RegisteredParcelsRecyclerViewAdapter.RegisteredParcelsViewHolder> {
   private List<PendingParcel> _registeredParcels;
   Member _member;

   public static class RegisteredParcelsViewHolder extends RecyclerView.ViewHolder {
      // each data item is just a string in this case
      public TextView parcelIdTextView;
      public TextView fragileTextView;
      public TextView parcelTypeTextView;
      public TextView parcelWeightTextView;
      public TextView recipientAddressTextView;
      public TextView storageLocation;
      public Spinner deliverySpinner;

      public RegisteredParcelsViewHolder(@NonNull View itemView) {
         super(itemView);
         parcelIdTextView = itemView.findViewById(R.id.serialNumTextView);
         fragileTextView = itemView.findViewById(R.id.fragileTextView);
         parcelTypeTextView = itemView.findViewById(R.id.parcelTypeTextView);
         parcelWeightTextView = itemView.findViewById(R.id.parcelWeightTextView);
         recipientAddressTextView = itemView.findViewById(R.id.recipientAddressTextView);
         storageLocation = itemView.findViewById(R.id.storageLocationTextView);
      }
   }

   public RegisteredParcelsRecyclerViewAdapter(List<PendingParcel> registeredParcels, Member member) {
      _registeredParcels = registeredParcels;
      _member = member;
   }

   @NonNull
   @Override
   public RegisteredParcelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_registered_parcel_itemview, parent, false);
      return new RegisteredParcelsViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull RegisteredParcelsViewHolder holder, int position) {
      PendingParcel pendingParcel = _registeredParcels.get(position);
      Parcel parcelDetails = pendingParcel.getParcelDetails();
      holder.parcelIdTextView.setText(parcelDetails.getParcelID());
      holder.fragileTextView.setVisibility(parcelDetails.isFragile()? TextView.VISIBLE : TextView.INVISIBLE);
      holder.storageLocation.setText(parcelDetails.getLocationOfStorage());
      holder.recipientAddressTextView.setText(parcelDetails.getRecipientAddress());
      holder.parcelTypeTextView.setText(parcelDetails.getType().toString());
      holder.parcelWeightTextView.setText(parcelDetails.getWeight().toString());
   }

   @Override
   public int getItemCount() {
      return _registeredParcels.size();
   }


}
