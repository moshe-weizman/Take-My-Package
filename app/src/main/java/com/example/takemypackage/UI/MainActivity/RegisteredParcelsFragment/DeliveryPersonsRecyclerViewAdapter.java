/*
Java Workshop 2020
First Application
25/06/2020
Moshe Weizman 305343931
Aharon Packter 201530508
 */

package com.example.takemypackage.UI.MainActivity.RegisteredParcelsFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.takemypackage.Data.PendingParcelsFirebaseManager;
import com.example.takemypackage.Entities.DeliveryPerson;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DeliveryPersonsRecyclerViewAdapter extends RecyclerView.Adapter<DeliveryPersonsRecyclerViewAdapter.DeliveryPersonsViewHolder> {
    private List<DeliveryPerson> deliveryPersonList;
    private Member member;
    private Context context;
    private PendingParcel pendingParcel;

    public static class DeliveryPersonsViewHolder extends RecyclerView.ViewHolder {
        public TextView deliveryNameTextView;
        public TextView deliveryPhoneTextView;
        public CheckBox authorizationCheckBox;
        private ImageView imageViewDeliveryPerson;

        public DeliveryPersonsViewHolder(@NonNull View itemView) {
            super(itemView);
            deliveryNameTextView = itemView.findViewById(R.id.nameTextView);
            deliveryPhoneTextView = itemView.findViewById(R.id.phoneTextView);
            authorizationCheckBox = itemView.findViewById(R.id.authorizeCheckBox);
            imageViewDeliveryPerson = itemView.findViewById(R.id.imageViewDeliveryPerson);
        }
    }
//------------------------------------------------------------------------

    public DeliveryPersonsRecyclerViewAdapter(PendingParcel pendingParcel, HashMap<String, DeliveryPerson> deliveryPersonList, Member member) {
        Collection mapValues = deliveryPersonList.values();
        this.deliveryPersonList = new ArrayList<>(mapValues);
        this.member = member;
        this.pendingParcel = pendingParcel;
    }
//------------------------------------------------------------------------

    @NonNull
    @Override
    public DeliveryPersonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listed_delivery_person_itemview, parent, false);
        context = parent.getContext();
        return new DeliveryPersonsViewHolder(view);
    }
//------------------------------------------------------------------------

    @Override
    public void onBindViewHolder(@NonNull final DeliveryPersonsViewHolder holder, int position) {
        final DeliveryPerson deliveryPerson = deliveryPersonList.get(position);

        holder.deliveryPhoneTextView.setText(deliveryPerson.getPhone());
        holder.deliveryNameTextView.setText(deliveryPerson.getName());
        holder.authorizationCheckBox.setChecked(deliveryPerson.getAuthorized());
        Glide.with(context).load(deliveryPerson.getImageFirebaseUri()).centerCrop().override(150, 150).into(holder.imageViewDeliveryPerson);

        holder.authorizationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {
                deliveryPerson.setAuthorized(isChecked);
                PendingParcelsFirebaseManager.addOrUpdateMemberToOptionalDeliveries(pendingParcel, deliveryPerson, new PendingParcelsFirebaseManager.Action<String>() {

                    @Override
                    public void onSuccess(String obj) {
                        Toast.makeText(buttonView.getContext(), "welcome " + obj, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(buttonView.getContext(), "Error \n", Toast.LENGTH_LONG).show();
                    }

                });
            }
        });

    }
//------------------------------------------------------------------------

    @Override
    public int getItemCount() {
        return deliveryPersonList.size();
    }
}
