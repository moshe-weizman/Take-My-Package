package com.example.takemypackage.UI.MainActivity.HistoryParcelsFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Entities.HistoryParcel;
import com.example.takemypackage.R;

import java.util.List;

public class HistoryParcelsRecyclerViewAdapter extends RecyclerView.Adapter<HistoryParcelsRecyclerViewAdapter.HistoryParcelsViewHolder> {
    private List<HistoryParcel> historyParcelList;

    public static class HistoryParcelsViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHistoryParcelID;
        TextView textViewDeliveryPerson;
        TextView textViewDate;
        TextView textViewParcelType;
        TextView textViewParcelWeight;
        TextView textViewFragile;

        public HistoryParcelsViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHistoryParcelID = itemView.findViewById(R.id.textViewHistoryParcelID);
            textViewDeliveryPerson = itemView.findViewById(R.id.textViewDeliveryPerson);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewParcelType = itemView.findViewById(R.id.parcelTypeTextView);
            textViewParcelWeight = itemView.findViewById(R.id.parcelWeightTextView);
            textViewFragile = itemView.findViewById(R.id.fragileTextView);

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
        holder.textViewParcelType.setText(historyParcel.getParcelDetails().getType().toString());
        holder.textViewParcelWeight.setText(historyParcel.getParcelDetails().getWeight().toString());
        holder.textViewFragile.setVisibility(historyParcel.getParcelDetails().isFragile() ? View.VISIBLE : View.INVISIBLE);
        holder.textViewDate.setText(historyParcel.getDateCollected().toString());
    }

    @Override
    public int getItemCount() {
        return historyParcelList.size();
    }
}
