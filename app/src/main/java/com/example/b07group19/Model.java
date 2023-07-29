package com.example.b07group19;

import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.b07group19.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;

public class Model {
    private static Model instance;
    private DatabaseReference userRef;
    private FirebaseAuth auth;

    private Model() {
        userRef = FirebaseDatabase.getInstance().getReference("Users");
        auth = FirebaseAuth.getInstance();
    }

    public static Model getInstance() {
        if (instance == null)
                instance = new Model();
        return instance;
    }

    public void authenticate(String email, String password, Consumer<UserModel> callback) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    callback.accept(null);
                } else {
                    userRef.child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserModel user = snapshot.getValue(UserModel.class);
                            callback.accept(user);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    public void getUser(String userID, Consumer<UserModel> callback){
        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);
                callback.accept(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void register(String email, String password, Consumer<String>callback) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    callback.accept(auth.getUid());
                } else {
                    Log.d("Model.register", "Failed to register user", task.getException());
                    callback.accept(null);
                }
            }
        });
    }


    public void postUser(UserModel user, Consumer<Boolean> callback){
        userRef.child(user.userID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.accept(true);
                } else {
                    callback.accept(false);
                    Exception e = task.getException();
                    Log.e("RegisterActivity", "Failed to post user", e);
                }
            }
        });

    }

}