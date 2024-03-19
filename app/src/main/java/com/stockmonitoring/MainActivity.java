package com.stockmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.stockmonitoring.dao.StockDao;
import com.stockmonitoring.model.Stock;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    FirebaseDatabase database ;
    StockDao stockDao;
    Button btnStart;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        stockDao = new StockDao( database , this );

        btnStart = findViewById(R.id.btn_start);
        textView = findViewById(R.id.item_value);

        btnStart.setOnClickListener(this);

        fetchDataAndProcess();

    }

    private void fetchDataAndProcess() {
        stockDao.getLed(new StockDao.OnFetchListener() {
            @Override
            public void onFetchListener(Stock stock) {

                Log.d(TAG, "Led value récupérées avec succès : " + stock);
                textView.setText(stock.getValue().toString());

            }

            @Override
            public void onFetchFailure(Exception e) {
                Log.e(TAG, "Erreur lors de la récupération de event : ", e);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_start){
            if(textView.getText()=="1"){

                stockDao.update(new Stock(0));
                textView.setText("0");

            } else {

                stockDao.update(new Stock(1));
                textView.setText("1");

            }
        }
    }
}