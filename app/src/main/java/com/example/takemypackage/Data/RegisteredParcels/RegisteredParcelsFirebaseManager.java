package com.example.takemypackage.Data.RegisteredParcels;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisteredParcelsFirebaseManager {
    /**
     * Static reference to the parcels database
     */
    public static DatabaseReference parcelRef;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        parcelRef = database.getReference("PendingParcel");
    }


    public static void addFriendOfferedToFirebase(  ) {

    }
}
