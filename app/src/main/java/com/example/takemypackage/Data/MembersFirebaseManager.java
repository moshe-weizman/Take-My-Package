package com.example.takemypackage.Data;

import com.example.takemypackage.Entities.Member;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MembersFirebaseManager {

    public static DatabaseReference memberRef;

    static {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        memberRef = database.getReference("Members");
    }

    public static Task<Void> addMemberToFirebase(final Member member) {
        return memberRef.child(member.getPhone()).setValue(member);
    }


}
