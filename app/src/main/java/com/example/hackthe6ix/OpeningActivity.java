package com.example.hackthe6ix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class OpeningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
    }

    public void signup(View view)
    {
        Intent signUp = new Intent(this, SignUpActivity.class);
        startActivity(signUp);
    }
}
