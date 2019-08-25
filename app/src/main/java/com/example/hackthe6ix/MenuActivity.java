package com.example.hackthe6ix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    TextView tv;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        tv = findViewById(R.id.textView);
        s = getIntent().getStringExtra("name");
        tv.setText(s);
        Log.d("name",s);
    }

    public void growth(View view)
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void dash(View view)
    {
        Intent intent = new Intent(this,DashboardActivity.class);
        intent.putExtra("name",s);
        startActivity(intent);
    }
}
