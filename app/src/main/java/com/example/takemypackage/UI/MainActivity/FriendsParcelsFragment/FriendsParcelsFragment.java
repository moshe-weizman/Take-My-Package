package com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.example.takemypackage.Data.PendingParcelsFirebaseManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.Entities.PendingParcel;
import com.example.takemypackage.R;

import static com.example.takemypackage.UI.Login.LoginActivity.LoginActivity.MEMBER_KEY;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *fragment of Friends Parcels
 */
public class FriendsParcelsFragment extends Fragment {
    private RecyclerView parcelRecyclerView;
    private TextView noDataTextView;
    private List<PendingParcel> pendingParcels;
    private Member member;
    private static float MAX_DISTANCE = 1000000000;

    public FriendsParcelsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pendingParcels = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        member = (Member) intent.getSerializableExtra(MEMBER_KEY);
        View view = inflater.inflate(R.layout.fragment_friends_parcels, container, false);
        parcelRecyclerView = view.findViewById(R.id.parcelRecyclerView);
        noDataTextView = view.findViewById(R.id.noDataTextView);
        noDataTextView.setText("Your friends dont have any pending package");
        noDataTextView.setVisibility(View.GONE);
        parcelRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        parcelRecyclerView.setLayoutManager(layoutManager);

        PendingParcelsFirebaseManager.NotifyToParcelList(new PendingParcelsFirebaseManager.NotifyDataChange<List<PendingParcel>>() {
            @Override
            public void OnDataChanged(List<PendingParcel> obj) {
                String addressMember = member.getAddress();
                for (PendingParcel parcel : obj) {
                    if (!parcel.getParcelDetails().getRecipientPhone().equals(member.getPhone())
                            && getDistance(getContext(), addressMember, parcel.getParcelDetails().getLocationOfStorage()) < MAX_DISTANCE
                            && !pendingParcels.contains(parcel))
                        pendingParcels.add(parcel);

                }
                if (pendingParcels.isEmpty()) {
                    noDataTextView.setVisibility(View.VISIBLE);
                    parcelRecyclerView.setVisibility(View.GONE);
                } else {
                    noDataTextView.setVisibility(View.GONE);
                    parcelRecyclerView.setVisibility(View.VISIBLE);
                }

                if (parcelRecyclerView.getAdapter() == null) {
                    parcelRecyclerView.setAdapter(new FriendsParcelsRecyclerViewAdapter(pendingParcels, member));
                } else parcelRecyclerView.getAdapter().// If there is an adapter already

                        notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getContext(), "error to get parcel list of yours friends \n" + exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    /**
     *function that action  when fragment terminated
     */
    public void onDestroyView() {
        super.onDestroyView();
        PendingParcelsFirebaseManager.stopNotifyToPendingList();
        super.onDestroy();
    }

    /**
     * function to get Distance from to points
     * @param context
     * @param locA
     * @param locB
     * @return
     */
    static public float getDistance(Context context, String locA, String locB) {
        Location locationA = setLatLon(context, locA, "pointA");
        Location locationB = setLatLon(context, locB, "pointB");
        return locationA.distanceTo(locationB) / 1000;
    }

    /**
     * function to get Lan and lat on address
     * @param context
     * @param loc
     * @param pnt
     * @return
     */
    static private Location setLatLon(Context context, String loc, String pnt) {
        Geocoder geocoder = new Geocoder(context);
        Location location = new Location(pnt);
        List<Address> addresses;
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
//