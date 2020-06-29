/*
Java Workshop 2020
First Application
25/06/2020
Moshe Weizman 305343931
Aharon Packter 201530508
 */

package com.example.takemypackage.UI.MainActivity.HistoryParcelsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.takemypackage.Data.HistroryParcelsFirebaseManager;
import com.example.takemypackage.Entities.HistoryParcel;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import java.util.ArrayList;
import java.util.List;
import static com.example.takemypackage.UI.Login.LoginActivity.LoginActivity.MEMBER_KEY;

public class HistoryParcelsFragment extends Fragment {
    private RecyclerView parcelRecyclerView;
    private TextView noDataTextView;
    private List<HistoryParcel> historyParcelList;
    private Member member;

    public HistoryParcelsFragment() {
    }
//------------------------------------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyParcelList = new ArrayList<>();
    }
//------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent = getActivity().getIntent();
        member = (Member) intent.getSerializableExtra(MEMBER_KEY);
        View view = inflater.inflate(R.layout.fragment_friends_parcels, container, false);
        parcelRecyclerView = view.findViewById(R.id.parcelRecyclerView);
        noDataTextView = view.findViewById(R.id.noDataTextView);
        noDataTextView.setText("Your packages history is empty");
        parcelRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        parcelRecyclerView.setLayoutManager(layoutManager);
        HistroryParcelsFirebaseManager.NotifyToHistoryParcelList(member.getPhone(), new HistroryParcelsFirebaseManager.NotifyDataChange<List<HistoryParcel>>() {
            @Override
            public void OnDataChanged(List<HistoryParcel> obj) {
                historyParcelList = obj;
                if (historyParcelList.isEmpty()){
                    noDataTextView.setVisibility(View.VISIBLE);
                    parcelRecyclerView.setVisibility(View.GONE);
                }else{
                    noDataTextView.setVisibility(View.GONE);
                    parcelRecyclerView.setVisibility(View.VISIBLE);
                }
                if (parcelRecyclerView.getAdapter() == null) {
                    parcelRecyclerView.setAdapter(new HistoryParcelsRecyclerViewAdapter(historyParcelList));
                } else parcelRecyclerView.getAdapter().notifyDataSetChanged();// If there is an adapter already
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getContext(), "error to get parcel list of yours friends \n" + exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
    //------------------------------------------------------------------------

    /**
     *function that action  when fragment terminated
     */
    public void onDestroyView() {
        super.onDestroyView();
        HistroryParcelsFirebaseManager.stopNotifyToHistoryList();
        super.onDestroy();
    }
}