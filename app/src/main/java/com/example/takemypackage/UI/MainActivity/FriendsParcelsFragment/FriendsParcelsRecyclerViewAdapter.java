package com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;

import java.util.List;

public class FriendsParcelsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsParcelsRecyclerViewAdapter.FriendsParcelsViewHolder> {
    private List<PendingParcel> pendingParcels;

    public FriendsParcelsRecyclerViewAdapter(List<PendingParcel> pendingParcels) {
        this.pendingParcels = pendingParcels;
    }

    @NonNull
    @Override
    public FriendsParcelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_parcel_friends_itemview, parent, false);
        return new FriendsParcelsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsParcelsViewHolder holder, int position) {
        PendingParcel pendingParcel=pendingParcels.get(position);
        holder.textViewParcelId.setText(pendingParcel.getParcelDetails().getParcelID());
        holder.textViewLocationOfStorage.setText(pendingParcel.getParcelDetails().getLocationOfStorage());
        holder.textViewRecipientAddress.setText(pendingParcel.getParcelDetails().getRecipientAddress());
    }

    @Override
    public int getItemCount() {
        return pendingParcels.size();
    }

    public static class FriendsParcelsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewParcelId;
        TextView textViewRecipientAddress;
        TextView textViewLocationOfStorage;

        public FriendsParcelsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewParcelId = (TextView) itemView.findViewById(R.id.addressTextView);
            textViewRecipientAddress = (TextView) itemView.findViewById(R.id.weightTextView);
            textViewLocationOfStorage = (TextView) itemView.findViewById(R.id.distanceFromMeTextView);
        }
    }
}
