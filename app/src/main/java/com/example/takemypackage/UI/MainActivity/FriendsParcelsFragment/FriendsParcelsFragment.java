package com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment;

import android.os.Bundle;

import com.example.takemypackage.Data.PendingParcelsFirebaseManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FriendsParcelsFragment extends Fragment {

//=============================================================================================
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public FriendsParcelsFragment() {
//        // Required empty public constructor
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendsParcelsFragment.
     */
//    // TODO: Rename and change types and number of parameters
//    public static FriendsParcelsFragment newInstance(String param1, String param2) {
//        FriendsParcelsFragment fragment = new FriendsParcelsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
// ==============================================================================================
    private RecyclerView parcelRecyclerView;
    private List<PendingParcel> pendingParcels;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pendingParcels = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_friends_parcels, container, false);


        View view = inflater.inflate(R.layout.fragment_friends_parcels, container, false);
        parcelRecyclerView = view.findViewById(R.id.parcelRecyclerView);
        parcelRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        parcelRecyclerView.setLayoutManager(layoutManager);

        PendingParcelsFirebaseManager.NotifyToParcelList(pendingParcels, new PendingParcelsFirebaseManager.NotifyDataChange<List<PendingParcel>>() {
            @Override
            public void OnDataChanged(List<PendingParcel> obj) {
                if (parcelRecyclerView.getAdapter() == null) {
                    pendingParcels = obj;
                    parcelRecyclerView.setAdapter(new FriendsParcelsRecyclerViewAdapter(pendingParcels));
                } else parcelRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getContext(), "error to get parcel list of yours friends \n" + exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

//    public void onDestroyView() {
//        super.onDestroyView();
//        PendingParcelsFirebaseManager.stopNotifyToStudentList();
//        super.onDestroy();
//    }

}
