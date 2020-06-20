package com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Data.PendingParcelsFirebaseManager;
import com.example.takemypackage.Entities.DeliveryPerson;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;

import java.util.List;

public class FriendsParcelsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsParcelsRecyclerViewAdapter.FriendsParcelsViewHolder> {
    private List<PendingParcel> pendingParcels;
    private Member member;

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
    public void onBindViewHolder(@NonNull FriendsParcelsViewHolder holder, int position) {

        final PendingParcel pendingParcel=pendingParcels.get(position);
        holder.textViewParcelId.setText(pendingParcel.getParcelDetails().getParcelID());
        holder.textViewLocationOfStorage.setText(pendingParcel.getParcelDetails().getLocationOfStorage());
        holder.textViewRecipientAddress.setText(pendingParcel.getParcelDetails().getRecipientAddress());
        //holder.buttonIWantToTakeIt.setTag(pendingParcel);
        holder.buttonIWantToTakeIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pendingParcel.addOptionalDelivery(new DeliveryPerson(member));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pendingParcels.size();
    }

    public static class FriendsParcelsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewParcelId;
        TextView textViewRecipientAddress;
        TextView textViewLocationOfStorage;
        Button buttonIWantToTakeIt;

        public FriendsParcelsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewParcelId = itemView.findViewById(R.id.textViewParcelId);
            textViewRecipientAddress = itemView.findViewById(R.id.textViewRecipientAddress);
            textViewLocationOfStorage = itemView.findViewById(R.id.textViewLocationOfStorage);
            buttonIWantToTakeIt = itemView.findViewById(R.id.buttonIWantToTake);
        }
    }
}
