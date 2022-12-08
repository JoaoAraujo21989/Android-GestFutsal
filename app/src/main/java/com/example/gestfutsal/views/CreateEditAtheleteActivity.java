package com.example.gestfutsal.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestfutsal.DBHelper;
import com.example.gestfutsal.R;
import com.example.gestfutsal.model.Team;

import java.util.ArrayList;
import java.util.List;

public class CreateEditAtheleteActivity extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    private DBHelper db;
    Intent i;
    Intent j;
    int idTeam;


    List<Team> listTeam;
    ArrayAdapter<Team> aListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_athelete);

        viewHolder.bt_cea_register = findViewById(R.id.bt_cea_register);
        viewHolder.bt_cea_exit_athelete = findViewById(R.id.bt_cea_exit_athelete);
        viewHolder.bt_cea_update_athelete = findViewById(R.id.bt_cea_update_athelete);
        viewHolder.bt_cea_delete_athelete = findViewById(R.id.bt_cea_delete_athelete);
        viewHolder.et_cea_address = findViewById(R.id.et_cea_address);
        viewHolder.et_cea_tel = findViewById(R.id.et_cea_tel);
        viewHolder.et_cea_email = findViewById(R.id.et_cea_email);
        viewHolder.et_cea_number = findViewById(R.id.et_cea_number);
        viewHolder.et_cea_name = findViewById(R.id.et_cea_name);
        viewHolder.rb_cea_Masculino = findViewById(R.id.rb_cea_Masculino);
        viewHolder.rb_cea_Feminino = findViewById(R.id.rb_cea_Feminino);
        viewHolder.sp_cea_levelTeam = findViewById(R.id.sp_cea_levelTeam);
        viewHolder.tv_cea_header = findViewById(R.id.tv_cea_header);



        //Intent i
        i = getIntent();
        int id = -1;


        //Intent J
        j = getIntent();
        String page = j.getExtras().getString("page").toString();
        String origenDetails = "details";
        String origenNew = "new";

        try {
            id = i.getExtras().getInt("id");
        } catch (Exception e) {
        }

        //Base dados
        db = new DBHelper(this);

        //BT REGISTO atleta
        viewHolder.bt_cea_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = viewHolder.et_cea_name.getText().toString().trim();
                String numberString =viewHolder.et_cea_number.getText().toString();
                int number = 0;
                if (!numberString.isEmpty()){
                    number = Integer.parseInt(viewHolder.et_cea_number.getText().toString().trim());
                }
                String genre = "";
                if (viewHolder.rb_cea_Masculino.isChecked()){
                    genre = "Masculino";
                } else if (viewHolder.rb_cea_Feminino.isChecked()){
                    genre = "Feminino";
                }
                String email = viewHolder.et_cea_email.getText().toString().trim();
                String tel = viewHolder.et_cea_tel.getText().toString().trim();
                String address = viewHolder.et_cea_address.getText().toString().trim();

                long Team = viewHolder.sp_cea_levelTeam.getSelectedItemId();
                String sTeam = String.valueOf(Team);
                int idTeam = Integer.parseInt(sTeam)+1;

                long res = 0;
                res = db.Insert_Athelete(name,number,genre,email, tel, address, idTeam);

                if (viewHolder.et_cea_name.getText().toString().isEmpty()){
                    Toast.makeText(CreateEditAtheleteActivity.this, "Nome vazio!", Toast.LENGTH_SHORT).show();
                    viewHolder.et_cea_name.requestFocus();
                }else if (numberString.isEmpty()){
                    Toast.makeText(CreateEditAtheleteActivity.this, "Número vazio!", Toast.LENGTH_SHORT).show();
                    viewHolder.et_cea_number.requestFocus();
                }else if (!viewHolder.rb_cea_Feminino.isChecked() && !viewHolder.rb_cea_Masculino.isChecked()){
                    Toast.makeText(CreateEditAtheleteActivity.this, "Género vazio!", Toast.LENGTH_SHORT).show();
                }else {
                    if (res > 0) {
                        Toast.makeText(CreateEditAtheleteActivity.this, "Registo efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CreateEditAtheleteActivity.this, ViewAthletesActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(CreateEditAtheleteActivity.this, "Erro ao registar!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        //BT UPDATE atleta
        viewHolder.bt_cea_update_athelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = viewHolder.et_cea_name.getText().toString().trim();
                int number = Integer.parseInt(viewHolder.et_cea_number.getText().toString().trim());

                String genre = "";
                if (viewHolder.rb_cea_Masculino.isChecked()){
                    genre = "Masculino";
                }else if (viewHolder.rb_cea_Feminino.isChecked()){
                    genre = "Feminino";
                }

                String email = viewHolder.et_cea_email.getText().toString().trim();
                String tel = viewHolder.et_cea_tel.getText().toString().trim();
                String address = viewHolder.et_cea_address.getText().toString().trim();

                long Team = viewHolder.sp_cea_levelTeam.getSelectedItemId();
                String sTeam = String.valueOf(Team);
                int idTeam = Integer.parseInt(sTeam)+1;

                int id = i.getExtras().getInt("id");
                long res = 0;
                res = db.Update_Athelete(id,name,number,genre,email, tel, address, idTeam);

                if (res > 0) {
                    Toast.makeText(CreateEditAtheleteActivity.this, "Edicão salva com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent u = new Intent(CreateEditAtheleteActivity.this, DetailsAtheleteActivity.class);
                    u.putExtra("id",id);
                    startActivity(u);
                    finish();
                } else {
                    Toast.makeText(CreateEditAtheleteActivity.this, "Erro ao editar!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BT APAGAR atleta
        viewHolder.bt_cea_delete_athelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = i.getExtras().getInt("id");
                long res = 0;
                res = db.Delete_Athelete(id);
                if (res > 0) {
                    Intent d = new Intent(CreateEditAtheleteActivity.this, ViewAthletesActivity.class);
                    d.putExtra("id",id);
                    startActivity(d);
                    Toast.makeText(CreateEditAtheleteActivity.this, "Registo eliminado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateEditAtheleteActivity.this, "Erro ao eliminar!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BT SAIR atleta
        viewHolder.bt_cea_exit_athelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (origenDetails.equals(page)){
                    Intent s = new Intent(CreateEditAtheleteActivity.this, DetailsAtheleteActivity.class);
                    startActivity(s);
                    finish();
                } else if (origenNew.equals(page)){
                    Intent s = new Intent(CreateEditAtheleteActivity.this, ViewAthletesActivity.class);
                    startActivity(s);
                    finish();
                }

            }
        });


        //Carregar dados Equipas para editar ATLETA
        listTeam = db.Team_SelectAll();
        aListAdapter = new ArrayAdapter<Team>(this, android.R.layout.simple_spinner_item,listTeam);
        aListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        viewHolder.sp_cea_levelTeam.setAdapter(aListAdapter);


        //BT Spiner
        viewHolder.sp_cea_levelTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idTeam = listTeam.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                idTeam =-1;
            }
        });

        //Carregar dados para editar ATLETA
        if (id > 0) {
            uploadAthelete(id);
        }


    }

    private void uploadAthelete(int id) {
        Cursor c = db.Athelete_SelectByID(id);
        c.moveToFirst();
        if (c.getCount() == 1) {

            int posName = c.getColumnIndex("name");
            String name = c.getString(posName);

            int posNumber = c.getColumnIndex("number");
            int number = c.getInt(posNumber);

            int posGenre = c.getColumnIndex("genre");
            String genre = c.getString(posGenre);

            int posEmail = c.getColumnIndex("email");
            String email = c.getString(posEmail);

            int posTel = c.getColumnIndex("tel");
            String tel = c.getString(posTel);

            int posAddress = c.getColumnIndex("address");
            String address = c.getString(posAddress);

            int posIdTeam = c.getColumnIndex("idTeam");
            int idTeam = c.getInt(posIdTeam);

            viewHolder.tv_cea_header.setText("Editar " + name);
            viewHolder.et_cea_name.setText(name);
            viewHolder.et_cea_number.setText(String.valueOf(number));
            if (genre.toString().trim().equals("Masculino")){
                viewHolder.rb_cea_Masculino.setChecked(true);
            }else if (genre.toString().trim().equals("Feminino")){
                viewHolder.rb_cea_Feminino.setChecked(true);
            }
            viewHolder.et_cea_email.setText(email);
            viewHolder.et_cea_tel.setText(tel);
            viewHolder.et_cea_address.setText(address);

            for (int j = 0; j < aListAdapter.getCount(); j++) {
                if (aListAdapter.getItem(j).getId()==idTeam){
                    viewHolder.sp_cea_levelTeam.setSelection(idTeam-1);
                }
            }

            viewHolder.bt_cea_register.setVisibility(View.GONE);
            viewHolder.bt_cea_update_athelete.setVisibility(View.VISIBLE);
            viewHolder.bt_cea_delete_athelete.setVisibility(View.VISIBLE);
        }
    }

    private static class ViewHolder {
        Button bt_cea_register, bt_cea_update_athelete, bt_cea_delete_athelete;
        EditText et_cea_address, et_cea_tel, et_cea_email, et_cea_number, et_cea_name;
        RadioButton rb_cea_Masculino, rb_cea_Feminino;
        Spinner sp_cea_levelTeam;
        TextView tv_cea_header;
        ImageView bt_cea_exit_athelete;
    }
}