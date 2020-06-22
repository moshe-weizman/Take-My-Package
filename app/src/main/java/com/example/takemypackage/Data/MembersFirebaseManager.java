package com.example.takemypackage.Data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.takemypackage.Entities.Member;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MembersFirebaseManager {

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

    public static DatabaseReference memberRef;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        memberRef = database.getReference("Members");
    }

    public static ChildEventListener memberRefChildEventListener;


    public static void addMemberToFirebase(final Member member, final Action<String> action) {
        memberRef.child(member.getPhone()).setValue(member).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                action.onSuccess(member.getPhone());
                action.onProgress("upload member data", 100);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload student data", 100);
            }
        });
    }


    public static void UpdateUserProfile(final String phoneOldMember, final Member newMember, final Action<String> action) {
        String phoneNewMember = newMember.getPhone();
        if (phoneOldMember.equals(phoneNewMember)) {
            memberRef.child(phoneOldMember).setValue(newMember).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    action.onSuccess("Update successful");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else {//if needed to update the key too (phone number)
            deleteMember(true, phoneOldMember, action);
            memberRef.child(phoneNewMember).setValue(newMember).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    action.onSuccess("Update successful");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    action.onFailure(e);
                }
            });

        }
    }

    public static void deleteMember(String phoneMember, final Action<String> action) {
        deleteMember(false, phoneMember, action);
    }

    public static void deleteMember(final boolean deleteForUpdate, final String phoneMember, final Action<String> action) {
        memberRef.child(phoneMember).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if (!deleteForUpdate)
                    action.onSuccess("Deletion was successful");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
            }
        });
    }

//    public static void NotifyToMember(final NotifyDataChange<Member> notifyDataChange) {
//        if (notifyDataChange != null) {
//            if (memberRefChildEventListener != null) {
//                notifyDataChange.onFailure(new Exception("first unNotify parcel list"));
//                return;
//            }
//            memberRefChildEventListener=new ChildEventListener(){
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            };
//
//            memberRef.addChildEventListener(memberRefChildEventListener);
//        }
//    }
}
