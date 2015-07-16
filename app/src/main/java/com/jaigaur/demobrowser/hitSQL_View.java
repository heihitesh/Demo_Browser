package com.jaigaur.demobrowser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by Hitesh Verma on 6/10/2015.
 */
public class hitSQL_View extends Activity implements View.OnClickListener {

    Button BackButton;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hitsqlview);
        tv = (TextView) findViewById(R.id.tvhitSQLinfo);
        BackButton = (Button) findViewById(R.id.bBack);
        BackButton.setOnClickListener(this);


        HistoryDataBase info = new HistoryDataBase(this);
        try {
            info.open();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception Caught at SQLView Class", Toast.LENGTH_SHORT).show();
        }
        String data = info.getData();
        info.close();
        tv.setText(data);
        tv.setTextSize(17);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bBack:
                finish();
                break;


        }
    }
}