package com.example.hackthe6ix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText nameText;
    EditText emailText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameText = findViewById(R.id.name);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);
    }
    public void back(View view)
    {
        finish();
    }
    public void signUp(View view)
    {
        if(!(nameText.getText().equals("") && emailText.getText().equals("") && passwordText.getText().equals("")))
        {
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("name",nameText.getText().toString());
            Log.d("name",nameText.getText().toString());
            startActivity(intent);
        }
    }

}
