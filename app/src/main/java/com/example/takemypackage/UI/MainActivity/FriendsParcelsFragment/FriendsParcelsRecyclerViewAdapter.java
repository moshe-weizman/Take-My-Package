/*
Java Workshop 2020
First Application
25/06/2020
Moshe Weizman 305343931
Aharon Packter 201530508
 */

package com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Data.HistroryParcelsFirebaseManager;
import com.example.takemypackage.Data.PendingParcelsFirebaseManager;
import com.example.takemypackage.Entities.DeliveryPerson;
import com.example.takemypackage.Entities.HistoryParcel;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.Entities.Parcel;
import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;

import java.util.Date;
import java.util.List;

public class FriendsParcelsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsParcelsRecyclerViewAdapter.FriendsParcelsViewHolder> {
    private List<PendingParcel> pendingParcels;
    private Member member;
    private DeliveryPerson deliveryPerson;
    private HistoryParcel historyParcel;
    private Date calendar;

    public FriendsParcelsRecyclerViewAdapter(List<PendingParcel> pendingParcels, Member member) {
        this.pendingParcels = pendingParcels;
        this.member = member;
    }

    @NonNull
    @Override
    public FriendsParcelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_parcel_friends_itemview, parent, false);
        return new FriendsParcelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FriendsParcelsViewHolder holder, int position) {

        final PendingParcel pendingParcel = pendingParcels.get(position);
        Parcel parcelDetails = pendingParcel.getParcelDetails();
        holder.textViewParcelId.setText(parcelDetails.getParcelID());
        holder.textViewLocationOfStorage.setText(parcelDetails.getLocationOfStorage());
        holder.textViewRecipientAddress.setText(parcelDetails.getRecipientAddress());
        holder.textViewParcelType.setText(parcelDetails.getType().toString());
        holder.textViewParcelWeight.setText(parcelDetails.getWeight().toString());
        holder.textViewFragile.setVisibility(parcelDetails.isFragile() ? View.VISIBLE : View.INVISIBLE);
        holder.buttonITookIt.setVisibility(View.GONE);
        boolean theUserHasOffered = pendingParcel.getOptionalDeliveries().containsKey(member.getPhone());
        if (theUserHasOffered)
            memberHasOffered(holder, pendingParcel);

        holder.buttonIWantToTakeIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                deliveryPerson = new DeliveryPerson(member);
                pendingParcel.addOptionalDelivery(deliveryPerson);
                PendingParcelsFirebaseManager.addOrUpdateMemberToOptionalDeliveries(pendingParcel, deliveryPerson, new PendingParcelsFirebaseManager.Action<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        memberHasOffered(holder, pendingParcel);
                        Toast.makeText(v.getContext(), "You have successfully registered", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(v.getContext(), "Registration failed \n", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        holder.buttonITookIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                deliveryPerson = new DeliveryPerson(member);
                deliveryPerson.setAuthorized(true);
                calendar = new Date();
                historyParcel = new HistoryParcel(pendingParcel, deliveryPerson, calendar);
                PendingParcelsFirebaseManager.deletePendingParcel(pendingParcel, new PendingParcelsFirebaseManager.Action<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        holder.buttonITookIt.setEnabled(false);
                        Toast.makeText(v.getContext(), "Successfully recorded \n", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(v.getContext(), "Error. try again \n", Toast.LENGTH_LONG).show();
                    }
                });

                HistroryParcelsFirebaseManager.addParcelToHistory(historyParcel, new HistroryParcelsFirebaseManager.Action<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        Toast.makeText(v.getContext(), "The package was successfully registered in history \n", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(v.getContext(), "Error. try again \n", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void memberHasOffered(FriendsParcelsViewHolder holder, PendingParcel pendingParcel) {
        if (pendingParcel.getOptionalDeliveries().get(member.getPhone()).getAuthorized()) {
            holder.buttonITookIt.setVisibility(View.VISIBLE);
            holder.buttonIWantToTakeIt.setVisibility(View.GONE);
        }
        holder.buttonIWantToTakeIt.setEnabled(false);
        holder.buttonIWantToTakeIt.setText("Waiting for a Permission...");
    }


    @Override
    public int getItemCount() {
        return pendingParcels.size();
    }

    public static class FriendsParcelsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewParcelId;
        TextView textViewRecipientAddress;
        TextView textViewLocationOfStorage;
        TextView textViewParcelType;
        TextView textViewParcelWeight;
        TextView textViewFragile;
        Button buttonIWantToTakeIt;
        Button buttonITookIt;

        public FriendsParcelsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewParcelId = itemView.findViewById(R.id.textViewParcelId);
            textViewRecipientAddress = itemView.findViewById(R.id.textViewRecipientAddress);
            textViewLocationOfStorage = itemView.findViewById(R.id.textViewLocationOfStorage);
            buttonIWantToTakeIt = itemView.findViewById(R.id.buttonIWantToTake);
            textViewParcelType = itemView.findViewById(R.id.parcelTypeTextView);
            textViewParcelWeight = itemView.findViewById(R.id.parcelWeightTextView);
            textViewFragile = itemView.findViewById(R.id.fragileTextView);
            buttonITookIt = itemView.findViewById(R.id.buttonITookIt);
        }
    }
}
