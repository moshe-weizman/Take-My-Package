package com.example.takemypackage.Data;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.takemypackage.Entities.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingParcelsFirebaseManager {
    private static List<PendingParcel> pendingParcelList = new ArrayList<PendingParcel>();

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
//private GenericTypeIndicator<PendingParcel> typeIndicator = new GenericTypeIndicator<PendingParcel>() {};

    public static void NotifyToParcelList(/*final List<PendingParcel> pendingParcelList*/ final NotifyDataChange<List<PendingParcel>> notifyDataChange) {
        if (notifyDataChange != null) {
            if (parcelRefChildEventListener != null) {
                notifyDataChange.onFailure(new Exception("first unNotify parcel list"));
                return;
            }
            pendingParcelList.clear();
            parcelRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    pendingParcelList.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        PendingParcel pendingParcel = child.getValue(PendingParcel.class);
//                        for (DataSnapshot childDelviries : child.child("optionalDeliveries").getChildren()) {
//                            pendingParcel.getOptionalDeliveries().put(childDelviries.getKey(), childDelviries.getValue(DeliveryPerson.class));
//                        }
                        pendingParcel.getParcelDetails().set_parcelID(child.getKey());
                        pendingParcelList.add(pendingParcel);
                    }
                    notifyDataChange.OnDataChanged(pendingParcelList);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    PendingParcel pendingParcel = dataSnapshot.getValue(PendingParcel.class);
                    String phone = dataSnapshot.getKey();
                    String parcelID = dataSnapshot.child(phone).getKey();
                    pendingParcel.getParcelDetails().set_parcelID(parcelID);
                    for (int i = 0; i < pendingParcelList.size(); i++) {
                        if (pendingParcelList.get(i).getParcelDetails().getParcelID().equals(parcelID)) {
                            pendingParcelList.set(i, pendingParcel);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(pendingParcelList);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    PendingParcel pendingParcel = dataSnapshot.getValue(PendingParcel.class);
                    String phone = dataSnapshot.getKey();
                    String parcelID = dataSnapshot.child(phone).getKey();
                    pendingParcel.getParcelDetails().set_parcelID(parcelID);
                    for (int i = 0; i < pendingParcelList.size(); i++) {
                        if (pendingParcelList.get(i).getParcelDetails().getParcelID().equals(parcelID)) {
                            pendingParcelList.remove(i);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(pendingParcelList);
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


    public static void stopNotifyToPendingList() {
        if (parcelRefChildEventListener != null) {
            parcelRef.removeEventListener(parcelRefChildEventListener);
            parcelRefChildEventListener = null;
        }
    }

    //-----------------------------------------CRUD Functions----------------------------------------------------------------------------------
    public static void addOrUpdateMemberToOptionalDeliveries(PendingParcel pendingParcel, DeliveryPerson deliveryPerson, final Action<String> action) {
        DatabaseReference DeliveryPersonRef = parcelRef.child(pendingParcel.getParcelDetails().getRecipientPhone()).child(pendingParcel.getParcelDetails().getParcelID()).child("optionalDeliveries");

        DeliveryPersonRef.child(deliveryPerson.getPhone()).setValue(deliveryPerson).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    //TODO replace to get  parmeters insted PendingParcel to RecipientPhone and parcelID
    public static void deletePendingParcel(PendingParcel pendingParcel, final Action<String> action) {
        Parcel parcel = pendingParcel.getParcelDetails();
        parcelRef.child(parcel.getRecipientPhone()).child(parcel.getParcelID()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess("Deletion was successful");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
            }
        });

    }

//    public static void updateAuthorization(PendingParcel pendingParcel, DeliveryPerson deliveryPerson, boolean isChecked){
//
//        DatabaseReference DeliveryPersonRef = parcelRef.child(pendingParcel.getParcelDetails().getRecipientPhone()).child(pendingParcel.getParcelDetails().getParcelID()).child("optionalDeliveries").child(deliveryPerson.getPhone());
//        DeliveryPersonRef.setValue(deliveryPerson);
//    }

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


}
