package com.example.takemypackage.Data;

import com.example.takemypackage.Entities.Parcel;
import com.example.takemypackage.Entities.PendingParcel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class PendingParcelsFirebaseManager {

    public interface Action<T> {
        void onSuccess(T obj);

        void onFailure(Exception exception);

        void onProgress(String status, double percent);
    }

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }

    /**
     * Static reference to the parcels database
     */
    public static DatabaseReference parcelRef;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        parcelRef = database.getReference("PendingParcel");
    }

    public static ChildEventListener parcelRefChildEventListener;


    public static void NotifyToParcelList(final List<PendingParcel> PendingParcelList, final NotifyDataChange<List<PendingParcel>> notifyDataChange) {
        parcelRef.addChildEventListener(parcelRefChildEventListener);
        if (notifyDataChange != null) {
            if (parcelRefChildEventListener != null) {
                notifyDataChange.onFailure(new Exception("first unNotify student list"));
                return;
            }
            PendingParcelList.clear();
            parcelRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    PendingParcel pendingParcel = dataSnapshot.getValue(PendingParcel.class);
                    String id = dataSnapshot.getKey();
                    //  pendingParcel.setId(Long.parseLong(id));
                    PendingParcelList.add(pendingParcel);
                    notifyDataChange.OnDataChanged(PendingParcelList);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    PendingParcel pendingParcel = dataSnapshot.getValue(PendingParcel.class);
                    String phone = dataSnapshot.getKey();
                    String parcelID = dataSnapshot.child(phone).getKey();
                    pendingParcel.getParcelDetails().set_parcelID(parcelID);
                    for (int i = 0; i < PendingParcelList.size(); i++) {
                        if (PendingParcelList.get(i).getParcelDetails().getParcelID().equals(parcelID)) {
                            PendingParcelList.set(i, pendingParcel);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(PendingParcelList);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    PendingParcel pendingParcel = dataSnapshot.getValue(PendingParcel.class);
                    String phone = dataSnapshot.getKey();
                    String parcelID = dataSnapshot.child(phone).getKey();
                    pendingParcel.getParcelDetails().set_parcelID(parcelID);
                    for (int i = 0; i < PendingParcelList.size(); i++) {
                        if (PendingParcelList.get(i).getParcelDetails().getParcelID().equals(parcelID)) {
                            PendingParcelList.remove(i);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(PendingParcelList);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    notifyDataChange.onFailure(databaseError.toException());
                }
            };
            parcelRef.addChildEventListener(parcelRefChildEventListener);
        }
    }


    public static void stopNotifyToStudentList() {
        if (parcelRefChildEventListener != null) {
            parcelRef.removeEventListener(parcelRefChildEventListener);
            parcelRefChildEventListener = null;
        }
    }

    //-----------------------------------------CRUD Functions----------------------------------------------------------------------------------
    public List<Parcel> getMembersPendingParcels(String recipientPhone) {
        return null;
    }

    public List<Parcel> getPendingParcelByQuery(Query query) {
        return null;
    }

    public List<Parcel> getAllPendingParcels() {
        return null;
    }

    public void addPendingParcel(PendingParcel pendingParcel) {

    }

    public void updatePendingParcel(PendingParcel pendingParcel) {

    }

    public void deletePendingParcel(PendingParcel pendingParcel) {

    }

}
