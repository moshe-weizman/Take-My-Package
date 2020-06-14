package com.example.takemypackage.Data;

import androidx.annotation.NonNull;

import com.example.takemypackage.Entities.Member;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MembersFirebaseManager {

    public interface Action<T> {
        void onSuccess(T obj);

        void onFailure(Exception exception);

        void onProgress(String status, double percent);
    }

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }

    public static DatabaseReference memberRef;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        memberRef = database.getReference("Members");
    }

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


}
