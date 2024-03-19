package com.stockmonitoring.dao;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stockmonitoring.model.Stock;

import java.util.HashMap;
import java.util.Map;

public class StockDao {

    private static final String TAG = "StockDao";
    private final FirebaseDatabase db;
    private final Context context;

    public StockDao(FirebaseDatabase db , Context context ) {

        this.db = db;
        this.context=context;

    }

    public void getLed( OnFetchListener listener ){

        Stock stock = new Stock();
        DatabaseReference reference = db.getReference("led").child("value");

        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    listener.onFetchFailure(task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Integer value = task.getResult().getValue(Integer.class);
                    if (value != null) {
                        stock.setValue(value);
                        Log.d(TAG, "DataSnapshot exists, :" + stock);
                        listener.onFetchListener(stock);
                    } else {
                        Log.d(TAG, "DataSnapshot exists, mais la valeur est null");
                    }
                }
            }
        });


    }

    public void update( Stock stock ){

        DatabaseReference reference = db.getReference("led");
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("value",stock.getValue());
        reference.updateChildren(childUpdates);

    }

    public interface OnFetchListener {
        void onFetchListener( Stock stock );
        void onFetchFailure(Exception e);
    }

}
