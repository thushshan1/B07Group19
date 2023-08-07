package com.example.b07group19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.b07group19.models.Store;
import com.google.firebase.auth.FirebaseAuth;


public class StoreDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonManageProducts;
    private Button buttonViewOrder;
    private TextView storeText;
    private String currentUserID;
    private Model model;
    private Store store;

    public static String thename;
    public static String storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_dashboard);

        buttonManageProducts = (Button) findViewById(R.id.buttonManageProducts);
        buttonManageProducts.setOnClickListener(this);
        // currentUserID = getIntent().getStringExtra("currentUserID");
        currentUserID = FirebaseAuth.getInstance().getUid();
        buttonViewOrder = (Button) findViewById(R.id.buttonViewOrders);

        model = Model.getInstance();
        storeText = (TextView) findViewById(R.id.textViewWelcome);
        getStore();

        buttonViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(StoreDashboardActivity.this,StoreOrdersActivity.class);
                intent.putExtra("storeName", storeName);
                startActivity(intent);

            }

        }
        );
    }

    //@Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.buttonManageProducts) {
            Intent intent = new Intent(this, ManageProducts.class);
            intent.putExtra("storeName", storeName);
            startActivity(intent);
        }
        if (viewId == R.id.btnViewOrders) {
            Intent intent = new Intent(this, StoreOrdersActivity.class);
            intent.putExtra("currentUserID", currentUserID);
            startActivity(intent);
        }




    }
    private void getStore() {
        model.getStoreByOwner(currentUserID, (Store store) -> {
            if (store == null) {
                Intent intent = new Intent(this, CreateStoreActivity.class);
                intent.putExtra("currentUserID", currentUserID);
                startActivity(intent);
                return;
            }
            this.store = store;
            storeText.setText(store.storeName);
            storeName = store.storeName;

        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }



}