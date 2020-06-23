package com.example.takemypackage.UI.MainActivity.HistoryParcelsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takemypackage.Data.HisroryParcelsFirebaseManager;
import com.example.takemypackage.Entities.HistoryParcel;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment.FriendsParcelsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.takemypackage.UI.Login.LoginActivity.LoginActivity.MEMBER_KEY;

public class HistoryParcelsFragment extends Fragment {
    private RecyclerView parcelRecyclerView;
    private List<HistoryParcel> historyParcelList;
    private Member member;

    public HistoryParcelsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyParcelList = new ArrayList<HistoryParcel>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_friends_parcels, container, false);
        Intent intent = getActivity().getIntent();
        member = (Member) intent.getSerializableExtra(MEMBER_KEY);
        View view = inflater.inflate(R.layout.fragment_friends_parcels, container, false);
        parcelRecyclerView = view.findViewById(R.id.parcelRecyclerView);
        parcelRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        parcelRecyclerView.setLayoutManager(layoutManager);


        HisroryParcelsFirebaseManager.NotifyToHistoryParcelList(member.getPhone(), new HisroryParcelsFirebaseManager.NotifyDataChange<List<HistoryParcel>>() {
            @Override
            public void OnDataChanged(List<HistoryParcel> obj) {
                if (parcelRecyclerView.getAdapter() == null) {
                    historyParcelList = obj;
                    parcelRecyclerView.setAdapter(new HistoryParcelsRecyclerViewAdapter(historyParcelList));
                } else parcelRecyclerView.getAdapter().notifyDataSetChanged();
            }


            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getContext(), "error to get parcel list of yours friends \n" + exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}