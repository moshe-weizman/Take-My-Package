/*
Java Workshop 2020
First Application
25/06/2020
Moshe Weizman 305343931
Aharon Packter 201530508
 */

package com.example.takemypackage;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.takemypackage.Data.PendingParcelsFirebaseManager;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.UI.BroadcastReceiversActivities.MyBroadcastReceiver;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.jetbrains.annotations.NotNull;
import static com.example.takemypackage.UI.Login.LoginActivity.LoginActivity.MEMBER_KEY;


public class MyBroadcastService extends Service {

   private ChildEventListener listener;
   private String usersPhone;

   @Nullable
   @Override
   public IBinder onBind(Intent intent) {
      return null;
   }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId) {
      Member member = (Member)intent.getSerializableExtra(MEMBER_KEY);
      usersPhone = member.getPhone();
      BroadcastReceiver receiver = new MyBroadcastReceiver();
      IntentFilter filter = new IntentFilter();
      filter.addAction("parcel_entered_service");
      this.registerReceiver(receiver, filter);
      PendingParcelsFirebaseManager.parcelRef.child(usersPhone).addChildEventListener(newPackagesEventListener());
      return START_REDELIVER_INTENT;
   }
//------------------------------------------------------------------------

   @NotNull
   private ChildEventListener newPackagesEventListener() {
      if(listener == null) {
         listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               for (DataSnapshot child :
                     dataSnapshot.getChildren()) {
                  if (!dataSnapshot.child("alreadyNotified").exists()){
                     dataSnapshot.child("alreadyNotified").getRef().setValue(true);
                     Intent i = new Intent("parcel_entered_service");
                     i.putExtra("parcelId", dataSnapshot.getKey());
                     sendBroadcast(i);
                  }
               }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
         };
      }
      return listener;
   }
//------------------------------------------------------------------------

   @Override
   public void onDestroy() {
      PendingParcelsFirebaseManager.parcelRef.child(usersPhone).removeEventListener(newPackagesEventListener());
      stopSelf();
      super.onDestroy();
   }
}

