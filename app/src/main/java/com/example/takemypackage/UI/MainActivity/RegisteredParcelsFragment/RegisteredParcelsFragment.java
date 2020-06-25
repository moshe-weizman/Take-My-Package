package com.example.takemypackage.UI.MainActivity.RegisteredParcelsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Data.PendingParcelsFirebaseManager;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;

import static com.example.takemypackage.UI.Login.LoginActivity.LoginActivity.MEMBER_KEY;

import java.util.ArrayList;
import java.util.List;

public class RegisteredParcelsFragment extends Fragment {
    private RecyclerView registeredParcelsRecyclerView;
    private TextView noDataTextView;
    private List<PendingParcel> registeredParcels;
    private Member member;

    public RegisteredParcelsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent intent = this.getActivity().getIntent();
        member = (Member) intent.getSerializableExtra(MEMBER_KEY);
        registeredParcels = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_friends_parcels, container, false);
        registeredParcelsRecyclerView = view.findViewById(R.id.parcelRecyclerView);
        noDataTextView = view.findViewById(R.id.noDataTextView);
        noDataTextView.setText("No package is waiting for you yet");
        registeredParcelsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        registeredParcelsRecyclerView.setLayoutManager(layoutManager);


        PendingParcelsFirebaseManager.NotifyToParcelList(new PendingParcelsFirebaseManager.NotifyDataChange<List<PendingParcel>>() {
            @Override
            public void OnDataChanged(List<PendingParcel> obj) {
                registeredParcels.clear();
                for (PendingParcel pendingParcel : obj) {
                    if (pendingParcel.getParcelDetails().getRecipientPhone().equals(member.getPhone())) {
                        registeredParcels.add(pendingParcel);
                    }
                }
                if (registeredParcels.isEmpty()){
                    noDataTextView.setVisibility(View.VISIBLE);
                    registeredParcelsRecyclerView.setVisibility(View.GONE);
                }else{
                    noDataTextView.setVisibility(View.GONE);
                    registeredParcelsRecyclerView.setVisibility(View.VISIBLE);
                }
                if (registeredParcelsRecyclerView.getAdapter() == null) {
                    registeredParcelsRecyclerView.setAdapter(new RegisteredParcelsRecyclerViewAdapter(registeredParcels, member, getContext()));
                } else registeredParcelsRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getContext(), "Failed to get your registered parcels data \n" + exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onDestroyView() {
        super.onDestroyView();
        PendingParcelsFirebaseManager.stopNotifyToPendingList();
        super.onDestroy();
    }
}
