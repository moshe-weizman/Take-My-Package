package com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Data.HisroryParcelsFirebaseManager;
import com.example.takemypackage.Data.PendingParcelsFirebaseManager;
import com.example.takemypackage.Entities.DeliveryPerson;
import com.example.takemypackage.Entities.HistoryParcel;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;
import com.example.takemypackage.Utils.LoadingDialog;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FriendsParcelsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsParcelsRecyclerViewAdapter.FriendsParcelsViewHolder> {
    private List<PendingParcel> pendingParcels;
    private HashMap<String, DeliveryPerson> optionalDeliveries;
    private Member member;
    private DeliveryPerson deliveryPerson;
    private HistoryParcel historyParcel;
    private Date calendar;
    private LoadingDialog loadingDialog;


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

        holder.textViewParcelId.setText(pendingParcel.getParcelDetails().getParcelID());
        holder.textViewLocationOfStorage.setText(pendingParcel.getParcelDetails().getLocationOfStorage());
        holder.textViewRecipientAddress.setText(pendingParcel.getParcelDetails().getRecipientAddress());
        if (pendingParcel.getOptionalDeliveries().containsKey(member.getPhone())) {
            if (pendingParcel.getOptionalDeliveries().get(member.getPhone()).isAuthorized()) {
                holder.buttonITookIt.setEnabled(true);
                holder.buttonIWantToTakeIt.setText("You can now to take it");
            } else
                memberHasOffered(holder);
        }
        holder.buttonIWantToTakeIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryPerson = new DeliveryPerson(member);
                pendingParcel.addOptionalDelivery(deliveryPerson);

                PendingParcelsFirebaseManager.addOrUpdateMemberToOptionalDeliveries(pendingParcel, deliveryPerson, new PendingParcelsFirebaseManager.Action<String>() {

                    @Override
                    public void onSuccess(String obj) {
                        //TODO toast
                        //Toast.makeText(, "welcome " + obj, Toast.LENGTH_LONG).show();
                        memberHasOffered(holder);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        //TODO toast

                        // Toast.makeText(getBaseContext(), "Error \n", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgress(String status, double percent) {

                    }
                });
            }
        });

        holder.buttonITookIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliveryPerson = new DeliveryPerson(member);
                calendar=new Date();
                historyParcel = new HistoryParcel(pendingParcel, deliveryPerson, calendar);
                PendingParcelsFirebaseManager.deletePendingParcel(pendingParcel, new PendingParcelsFirebaseManager.Action<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        //TODO toast
                        //Toast.makeText(, "welcome " + obj, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        //TODO toast

                        // Toast.makeText(getBaseContext(), "Error \n", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgress(String status, double percent) {
                    }
                });

                HisroryParcelsFirebaseManager.addParcelToHistory(historyParcel, new HisroryParcelsFirebaseManager.Action<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        //TODO toast
                        //Toast.makeText(, "welcome " + obj, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        //TODO toast

                        // Toast.makeText(getBaseContext(), "Error \n", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgress(String status, double percent) {
                    }
                });
            }
        });
    }

    private void memberHasOffered(FriendsParcelsViewHolder holder) {
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
        Button buttonIWantToTakeIt;
        Button buttonITookIt;

        public FriendsParcelsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewParcelId = itemView.findViewById(R.id.textViewParcelId);
            textViewRecipientAddress = itemView.findViewById(R.id.textViewRecipientAddress);
            textViewLocationOfStorage = itemView.findViewById(R.id.textViewLocationOfStorage);
            buttonIWantToTakeIt = itemView.findViewById(R.id.buttonIWantToTake);
            buttonITookIt = itemView.findViewById(R.id.buttonITookIt);
        }
    }
}
