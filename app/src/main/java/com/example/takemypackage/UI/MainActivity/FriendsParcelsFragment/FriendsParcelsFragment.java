package com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.example.takemypackage.Data.MembersFirebaseManager;
import com.example.takemypackage.Data.PendingParcelsFirebaseManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.takemypackage.Entities.DeliveryPerson;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;

import static com.example.takemypackage.UI.Login.LoginActivity.LoginActivity.MEMBER_KEY;

import java.io.IOException;
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
    private Member member;
    public static float MAX_DISTANCE = 1000000000;
    private Button buttonIWantToTake;
    private DeliveryPerson deliveryPerson;

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
        Intent intent = getActivity().getIntent();
        member = (Member) intent.getSerializableExtra(MEMBER_KEY);

        View view = inflater.inflate(R.layout.fragment_friends_parcels, container, false);
        parcelRecyclerView = view.findViewById(R.id.parcelRecyclerView);
        parcelRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        parcelRecyclerView.setLayoutManager(layoutManager);

        parcelRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        PendingParcelsFirebaseManager.NotifyToParcelList(/*pendingParcels,*/ new PendingParcelsFirebaseManager.NotifyDataChange<List<PendingParcel>>() {
            @Override
            public void OnDataChanged(List<PendingParcel> obj) {
                if (parcelRecyclerView.getAdapter() == null) {
                    String addressMember = member.getAddress();
                    for (PendingParcel parcel : obj) {
                        if (getDistance(getContext(), addressMember, parcel.getParcelDetails().getLocationOfStorage()) < MAX_DISTANCE)
                            pendingParcels.add(parcel);
                    }
                    parcelRecyclerView.setAdapter(new FriendsParcelsRecyclerViewAdapter(pendingParcels, member));
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

    static public float getDistance(Context context, String locA, String locB) {
        Location locationA = setLatLon(context, locA, "pointA");
        Location locationB = setLatLon(context, locB, "pointB");
        return locationA.distanceTo(locationB) / 1000;
    }

    static private Location setLatLon(Context context, String loc, String pnt) {
        Geocoder geocoder = new Geocoder(context);
        Location location = new Location(pnt);
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(loc, 5);
            location.setLatitude(addresses.get(0).getLatitude());
            location.setLongitude(addresses.get(0).getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

}
