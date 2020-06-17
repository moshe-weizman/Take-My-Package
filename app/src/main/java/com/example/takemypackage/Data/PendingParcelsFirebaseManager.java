package com.example.takemypackage.Data;

import com.example.takemypackage.Entities.Parcel;
import com.example.takemypackage.Entities.PendingParcel;
import com.google.firebase.database.ChildEventListener;
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


    public static void NotifyToParcelList(final NotifyDataChange<List<PendingParcel>> notifyDataChange) {

    }

    public static void stopNotifyToParcelList() {

    }
//-----------------------------------------CRUD Functions----------------------------------------------------------------------------------
    public List<Parcel> getMembersPendingParcels(String recipientPhone){
        return null;
    }

    public List<Parcel> getPendingParcelByQuery(Query query){
        return null;
    }

    public List<Parcel> getAllPendingParcels(){
        return null;
    }

    public void addPendingParcel(PendingParcel pendingParcel){

    }

    public void updatePendingParcel(PendingParcel pendingParcel){

    }

    public void deletePendingParcel(PendingParcel pendingParcel){

    }

}
