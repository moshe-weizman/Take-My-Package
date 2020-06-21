package com.example.takemypackage.UI.MainActivity.HistoryParcelsFragment;

import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Data.HisroryParcelsFirebaseManager;
import com.example.takemypackage.Data.PendingParcelsFirebaseManager;
import com.example.takemypackage.Entities.DeliveryPerson;
import com.example.takemypackage.Entities.HistoryParcel;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.example.takemypackage.Entities.HistoryParcel;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment.FriendsParcelsRecyclerViewAdapter;

import java.util.List;

public class HistoryParcelsRecyclerViewAdapter extends RecyclerView.Adapter<HistoryParcelsRecyclerViewAdapter.HistoryParcelsViewHolder> {
    private List<HistoryParcel> historyParcelList;

    public static class HistoryParcelsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHistoryParcelID;
        TextView textViewDeliveryPerson;
        TextView textViewDate;


        public HistoryParcelsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHistoryParcelID = itemView.findViewById(R.id.textViewHistoryParcelID);
            textViewDeliveryPerson = itemView.findViewById(R.id.textViewDeliveryPerson);
            textViewDate = itemView.findViewById(R.id.textViewDate);

        }
    }

    public HistoryParcelsRecyclerViewAdapter(List<HistoryParcel> _historyParcelList) {
        this.historyParcelList = _historyParcelList;
    }

    @NonNull
    @Override
    public HistoryParcelsRecyclerViewAdapter.HistoryParcelsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_history_parcel_itemview, parent, false);
        return new HistoryParcelsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final HistoryParcelsViewHolder holder, int position) {

        final HistoryParcel historyParcel = historyParcelList.get(position);
        holder.textViewHistoryParcelID.setText(historyParcel.getParcelDetails().getParcelID());
        holder.textViewDeliveryPerson.setText(historyParcel.getDeliveryPerson().getName() + " Phone: " + historyParcel.getDeliveryPerson().getPhone());
        holder.textViewDate.setText(historyParcel.getDateCollected().toString());
    }

    @Override
    public int getItemCount() {
        return historyParcelList.size();
    }
}
