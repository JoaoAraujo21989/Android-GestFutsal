package com.example.gestfutsal.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestfutsal.DBHelper;
import com.example.gestfutsal.R;

public class LogInActivity extends AppCompatActivity {

    private ViewHolder mViewHolder= new ViewHolder();
    private DBHelper db;
    String emailtemp = "";

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mViewHolder.bt_Login_Login = findViewById(R.id.bt_Login_Login);
        mViewHolder.et_Login_Email = findViewById(R.id.et_Login_Email);
        mViewHolder.et_Login_Password = findViewById(R.id.et_Login_Password);
        mViewHolder.bt_GoAbout = findViewById(R.id.bt_GoAbout);

        sp = getSharedPreferences(getString(R.string.email), Context.MODE_PRIVATE);
        emailtemp = sp.getString(getString(R.string.email),"");
        mViewHolder.et_Login_Email.setText(emailtemp);

        db = new DBHelper(this);

        mViewHolder.bt_Login_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mViewHolder.et_Login_Email.getText().toString().trim();
                String password = mViewHolder.et_Login_Password.getText().toString().trim();

                editor = sp.edit();
                editor.putString(getString(R.string.email),emailtemp);
                editor.apply();

                if (email.isEmpty()){
                    Toast.makeText(LogInActivity.this, "Insira o seu email", Toast.LENGTH_SHORT).show();
                    mViewHolder.et_Login_Email.requestFocus();
                }else if (password.isEmpty()){
                    Toast.makeText(LogInActivity.this, "Insira a sua password", Toast.LENGTH_SHORT).show();
                    mViewHolder.et_Login_Password.requestFocus();
                }else if(!email.isEmpty() && !password.isEmpty()){
                    long res = db.Utilizador_Login(email,password);
                    if (res > 0 ){
                        Intent i =  new Intent(LogInActivity.this, MenuActivity.class);
                        i.putExtra("email", email);
                        i.putExtra("pass", res);
                        startActivity(i);
                        Toast.makeText(LogInActivity.this, "Bem vindo!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LogInActivity.this, "Login invalido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mViewHolder.bt_GoAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a =  new Intent(LogInActivity.this, AboutActivity.class);
                startActivity(a);
            }
        });

    }

    private class ViewHolder{
        Button bt_Login_Login, bt_GoAbout;
        EditText et_Login_Email, et_Login_Password;
    }
}