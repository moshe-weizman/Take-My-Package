package com.example.takemypackage.Data;

import androidx.annotation.NonNull;

import com.example.takemypackage.Entities.HistoryParcel;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.Entities.Parcel;
import com.example.takemypackage.Entities.PendingParcel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HisroryParcelsFirebaseManager {
    private static List<HistoryParcel> historyParcelList = new ArrayList<HistoryParcel>();

    public interface Action<T> {
        void onSuccess(T obj);

        void onFailure(Exception exception);

        void onProgress(String status, double percent);
    }

    //TODO implement the interface NotifyDataChange
    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }

    public static DatabaseReference histortParcelsRef;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        histortParcelsRef = database.getReference("HistoryParcels");
    }

    public static ChildEventListener historyParcelRefChildEventListener;

    public static void addParcelToHistory(final HistoryParcel historyParcel, final Action<String> action) {
        Parcel parcel = historyParcel.getParcelDetails();
        histortParcelsRef.child(parcel.getRecipientPhone()).child(parcel.getParcelID()).setValue(historyParcel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess("Registration was successful");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
            }
        });
    }


    public static void NotifyToHistoryParcelList(final String userPhone, final NotifyDataChange<List<HistoryParcel>> notifyDataChange) {
        if (notifyDataChange != null) {
            if (historyParcelRefChildEventListener != null) {
                notifyDataChange.onFailure(new Exception("first unNotify history parcel list"));
                return;
            }
            historyParcelList.clear();
            historyParcelRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    historyParcelList.clear();
                    if (dataSnapshot.getKey().equals(userPhone)) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            HistoryParcel historyParcel = child.getValue(HistoryParcel.class);
                            historyParcel.getParcelDetails().set_parcelID(child.getKey());
                            historyParcelList.add(historyParcel);
                        }
                    }
                    notifyDataChange.OnDataChanged(historyParcelList);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                    HistoryParcel historyParcel = dataSnapshot.getValue(HistoryParcel.class);
//                    String phone = dataSnapshot.getKey();
//                    String parcelID = dataSnapshot.child(phone).getKey();
//                    historyParcel.getParcelDetails().set_parcelID(parcelID);
//                    for (int i = 0; i < historyParcelList.size(); i++) {
//                        if (historyParcelList.get(i).getParcelDetails().getParcelID().equals(parcelID)) {
//                            historyParcelList.set(i, historyParcel);
//                            break;
//                        }
//                    }
//                    notifyDataChange.OnDataChanged(historyParcelList);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
//                    HistoryParcel historyParcel = dataSnapshot.getValue(HistoryParcel.class);
//                    String phone = dataSnapshot.getKey();
//                    String parcelID = dataSnapshot.child(phone).getKey();
//                    historyParcel.getParcelDetails().set_parcelID(parcelID);
//                    for (int i = 0; i < historyParcelList.size(); i++) {
//                        if (historyParcelList.get(i).getParcelDetails().getParcelID().equals(parcelID)) {
//                            historyParcelList.remove(i);
//                            break;
//                        }
//                    }
//                    notifyDataChange.OnDataChanged(historyParcelList);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyDataChange.onFailure(databaseError.toException());
                }
            };
            // DatabaseReference userHistoryParcelRef = histortParcelsRef.child(userPhone);
            histortParcelsRef.addChildEventListener(historyParcelRefChildEventListener);
        }
    }


    public static void stopNotifyToHistoryList() {
        if (historyParcelRefChildEventListener != null) {
            histortParcelsRef.removeEventListener(historyParcelRefChildEventListener);
            historyParcelRefChildEventListener = null;
        }
    }
}
