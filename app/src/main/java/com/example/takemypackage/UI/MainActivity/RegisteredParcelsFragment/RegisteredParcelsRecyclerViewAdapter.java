package com.example.takemypackage.UI.MainActivity.RegisteredParcelsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Entities.DeliveryPerson;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.Entities.Parcel;
import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;

import java.util.List;

public class RegisteredParcelsRecyclerViewAdapter extends RecyclerView.Adapter<RegisteredParcelsRecyclerViewAdapter.RegisteredParcelsViewHolder> {
   private List<PendingParcel> _registeredParcels;
   Member _member;
   Context context;

   public static class RegisteredParcelsViewHolder extends RecyclerView.ViewHolder {
      // each data item is just a string in this case
      public TextView parcelIdTextView;
      public TextView fragileTextView;
      public TextView parcelTypeTextView;
      public TextView parcelWeightTextView;
      public TextView storageLocationTextView;
      public TextView whoCanTakeTextView;
      public ConstraintLayout expandableLayout;
      public boolean isExpanded;
      public RecyclerView deliveryPersonsRecyclerView;

      public RegisteredParcelsViewHolder(@NonNull View itemView) {
         super(itemView);
         isExpanded = false;
         parcelIdTextView = itemView.findViewById(R.id.serialNumTextView);
         fragileTextView = itemView.findViewById(R.id.fragileTextView);
         parcelTypeTextView = itemView.findViewById(R.id.parcelTypeTextView);
         parcelWeightTextView = itemView.findViewById(R.id.parcelWeightTextView);
         storageLocationTextView = itemView.findViewById(R.id.storageLocationTextView);
         expandableLayout = itemView.findViewById(R.id.expandableLayout);
         whoCanTakeTextView = itemView.findViewById(R.id.whoCanTakeTextView);
         deliveryPersonsRecyclerView = itemView.findViewById(R.id.deliveryPersonsRecyclerView);
         deliveryPersonsRecyclerView.setHasFixedSize(true);
      }
   }

   public RegisteredParcelsRecyclerViewAdapter(List<PendingParcel> registeredParcels, Member member, Context context) {
      _registeredParcels = registeredParcels;
      _member = member;
      this.context = context;
   }

   @NonNull
   @Override
   public RegisteredParcelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_registered_parcel_itemview, parent, false);
      context =parent.getContext();
      return new RegisteredParcelsViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull final RegisteredParcelsViewHolder holder, int position) {
      PendingParcel pendingParcel = _registeredParcels.get(position);
      Parcel parcelDetails = pendingParcel.getParcelDetails();
      holder.parcelIdTextView.setText(parcelDetails.getParcelID());
      holder.fragileTextView.setVisibility(parcelDetails.isFragile()? TextView.VISIBLE : TextView.INVISIBLE);
      holder.storageLocationTextView.setText(parcelDetails.getLocationOfStorage());
      holder.parcelTypeTextView.setText(parcelDetails.getType().toString());
      holder.parcelWeightTextView.setText(parcelDetails.getWeight().toString());
      holder.expandableLayout.setVisibility(View.GONE);
      RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
      holder.deliveryPersonsRecyclerView.setLayoutManager(layoutManager);
      holder.deliveryPersonsRecyclerView.setAdapter(new DeliveryPersonsRecyclerViewAdapter(pendingParcel,pendingParcel.getOptionalDeliveries(), _member));

      holder.whoCanTakeTextView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            holder.isExpanded = !holder.isExpanded;
            holder.expandableLayout.setVisibility(holder.isExpanded ? View.VISIBLE : View.GONE);
         }
      });
   }

   @Override
   public int getItemCount() {
      return _registeredParcels.size();
   }


}
