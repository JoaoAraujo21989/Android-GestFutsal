package com.example.gestfutsal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gestfutsal.adapter.AtheletesListAdapter;
import com.example.gestfutsal.model.Athelete;
import com.example.gestfutsal.model.Team;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static int versao = 1;
    private static String nomeDB = "gestFutsalDB.db";
    String[] sql = {
            "CREATE TABLE utilizador (id INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, email TEXT NOT NULL UNIQUE,pass TEXT NOT NULL);",
            "INSERT INTO utilizador (email, pass) VALUES ('joao@mail.com', '1234'), ('joaquim@mail.com', '1234'), ('ana@mail.com', '1234');",


            "CREATE TABLE team ( id INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, underAge INTEGER);",
            "INSERT INTO team (id, name, underAge) VALUES (1, 'Benjamins B', 11), (2, 'Benjamins A', 11), (3, 'Infantis', 13), (4, 'Iniciados', 15), (5, 'Juvenis', 17), (6, 'Juniores', 19);",

            "CREATE TABLE athelete (id INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL ,number INTEGER, genre TEXT, email TEXT, tel TEXT, address TEXT, idTeam INTEGER, FOREIGN KEY(idTeam) REFERENCES team(id));",
            "INSERT INTO athelete (name, number, genre, email, tel, address, idTeam) VALUES " +
                    "('Carlos Cardoso',1,'Masculino','carlos@mail.com','911000111', 'R. da paz, Calendário, 4760-090, Vila Nova de Famalicão',1), " +
                    "('Manuel Oliveira',2,'Masculino','manuel@mail.com','962456789', 'Braga',2), " +
                    "('Clara de Sousa',3,'Feminino','clara@mail.com','935412896', 'Lisboa',3), " +
                    "('Maria Oliveira',4,'Feminino','maria@mail.com','962456789', 'Braga',4), " +
                    "('António de Sousa',5,'Masculino','antonio@mail.com','935412896', 'Lisboa',5), " +
                    "('Augusto Ferreira',6,'Masculino','augusto@mail.com','962456789', 'Braga',6), " +
                    "('Ana Teixeira',7,'Feminino','ana@mail.com','935412896', 'Lisboa',1), " +
                    "('Martinha Oliveira',8,'Feminino','martinha@mail.com','962456789', 'Braga',2), " +
                    "('Martim Ferreira',9,'Masculino','martim@mail.com','935412896', 'Lisboa',3), " +
                    "('Marco Oliveira',10,'Masculino','marco@mail.com','962456789', 'Braga',4), " +
                    "('Francisca Cardoso',11,'Feminino','francisca@mail.com','935412896', 'Lisboa',5), " +
                    "('Claudia Silva',12,'Feminino','claudia@mail.com','962456789', 'Braga',6), " +
                    "('Pedro Sá',13,'Masculino','sa@mail.com','935412896', 'Lisboa',1), " +
                    "('João Oliveira',14,'Masculino','joao@mail.com','962456789', 'Braga',2), " +
                    "('Isabel Martins',15,'Feminino','isabel@mail.com','935412896', 'Lisboa',3), " +
                    "('Catarina Sousa',16,'Feminino','catarina@mail.com','962456789', 'Braga',4), " +
                    "('José Pedro',17,'Masculino','jose@mail.com','935412896', 'Lisboa',5), " +
                    "('Duarte Matos',18,'Masculino','duarte@mail.com','921452893', 'Aveiro',6)"

    };

    public DBHelper(@Nullable Context context) {
        super(context, nomeDB, null, versao);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for (int i = 0; i < sql.length; i++) {
            sqLiteDatabase.execSQL(sql[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        versao++;
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS utilizador");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS team");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS athelete");
        onCreate(sqLiteDatabase);
    }

    //************************************** Utilizador ************************************************
    //----------------------------------------INSERT-----------------------------------------------------
    public long Insert_Utilizador(String email, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("pass", pass);
        return db.insert("utilizador", null, values);
    }

    //----------------------------------------UPDATE-----------------------------------------------------
    public long Update_Utilizador(int id, String email, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pass", pass);
        return db.update("utilizador", values, "id=?", new String[]{String.valueOf(id)});
    }

    //----------------------------------------DELETE-----------------------------------------------------
    public long Delete_Utilizador(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("utilizador", "id=?", new String[]{String.valueOf(id)});
    }

    //----------------------------------------SELECT-----------------------------------------------------
    public Cursor SelectAll_Utilizador() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT*FROM utilizador", null);
    }

    public Cursor SelectById_Utilizador(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT*FROM utilizador WHERE id=?", new String[]{String.valueOf(id)});
    }

    public long Utilizador_Login(String email, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM utilizador WHERE email =? AND pass=?", new String[]{email, pass});
        c.moveToFirst();
        if (c.getCount() == 1) {
            int posId = c.getColumnIndex("id");
            int id = c.getInt(posId);
            return id;
        } else {
            return -1;
        }
    }
    //=====================================================================================================

    //*************************************** Teams *****************************************************
    //----------------------------------------INSERT-----------------------------------------------------
    public long Insert_Team(String name, int underAge) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("underAge", underAge);
        return db.insert("team", null, values);
    }

    public long Insert_Team(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        return db.insert("team", null, values);
    }

    //----------------------------------------UPDATE-----------------------------------------------------
    public long Update_Team(int id, String name, int underAge) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("underAge", underAge);
        return db.update("team", values, "id=?", new String[]{String.valueOf(id)});
    }

    public long Update_Team(int id, String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        return db.update("team", values, "id=?", new String[]{String.valueOf(id)});
    }

    //----------------------------------------DELETE-----------------------------------------------------
    public long Delete_Team(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("team", "id=?", new String[]{String.valueOf(id)});
    }

    //----------------------------------------SELECT-----------------------------------------------------
    //Select All
    public List<Team> Team_SelectAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM team", null);
        List<Team> listaTeams = new ArrayList<>();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                int posId = c.getColumnIndex("id");
                int id = c.getInt(posId);

                int posName = c.getColumnIndex("name");
                String name = c.getString(posName);

                int posUnderAge = c.getColumnIndex("underAge");
                int underAge = c.getInt(posUnderAge);

                listaTeams.add(new Team(id, name, underAge));

            } while (c.moveToNext());
        }
        return listaTeams;
    }

    //Select All OrderBY name ASC
    public List<Team> Team_SelectAllOrderByNameASC() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM team ORDER BY name ASC", null);
        List<Team> listaTeams = new ArrayList<>();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                int posId = c.getColumnIndex("id");
                int id = c.getInt(posId);

                int posName = c.getColumnIndex("name");
                String name = c.getString(posName);

                int posUnderAge = c.getColumnIndex("underAge");
                int underAge = c.getInt(posUnderAge);

                listaTeams.add(new Team(id, name, underAge));

            } while (c.moveToNext());
        }
        return listaTeams;
    }

    //Select All OrderBY name DESC
    public List<Team> Team_SelectAllOrderByNameDESC() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM team ORDER BY name DESC", null);
        List<Team> listaTeams = new ArrayList<>();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                int posId = c.getColumnIndex("id");
                int id = c.getInt(posId);

                int posName = c.getColumnIndex("name");
                String name = c.getString(posName);

                int posUnderAge = c.getColumnIndex("underAge");
                int underAge = c.getInt(posUnderAge);

                listaTeams.add(new Team(id, name, underAge));

            } while (c.moveToNext());
        }
        return listaTeams;
    }

    //Select by ID
    public Cursor Team_SelectByID(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM team WHERE id=?", new String[]{String.valueOf(id)});
    }


    //=====================================================================================================

    //************************************** Athelete ***************************************************
    //----------------------------------------INSERT-----------------------------------------------------
    public long Insert_Athelete(String name, int number, String genre, String email, String tel, String address, int idTeam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("number", number);
        values.put("genre", genre);
        values.put("email", email);
        values.put("tel", tel);
        values.put("address", address);
        values.put("idTeam", idTeam);
        return db.insert("athelete", null, values);
    }

    //----------------------------------------UPDATE-----------------------------------------------------
    public long Update_Athelete(int id, String name, int number, String genre, String email, String tel, String address, int idTeam) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("number", number);
        values.put("genre", genre);
        values.put("email", email);
        values.put("tel", tel);
        values.put("address", address);
        values.put("idTeam", idTeam);
        return db.update("athelete", values, "id=?", new String[]{String.valueOf(id)});
    }

    //----------------------------------------DELETE-----------------------------------------------------
    public long Delete_Athelete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("athelete", "id=?", new String[]{String.valueOf(id)});
    }

    //----------------------------------------SELECT-----------------------------------------------------
    //Select ALL
    public List<Athelete> Atheletes_SelectAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM athelete", null);
        List<Athelete> listaAtheletes = new ArrayList<>();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                int posId = c.getColumnIndex("id");
                int id = c.getInt(posId);

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


                listaAtheletes.add(new Athelete(id, name, number, genre, email, tel, address, idTeam));

            } while (c.moveToNext());
        }
        return listaAtheletes;
    }

    //Select by ID
    public Cursor Athelete_SelectByID(int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM athelete WHERE id=?", new String[]{String.valueOf(id)});
    }

    //Select by Team
    public List<Athelete> Athelete_SelectByTeam(int idTeam) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM athelete WHERE idTeam=?", new String[]{String.valueOf(idTeam)});
        List<Athelete> listaAthelete = new ArrayList<>();
        c.moveToFirst();
        if (c.getCount() > 0) {
            do {
                int posId = c.getColumnIndex("id");
                int id = c.getInt(posId);

                int posName = c.getColumnIndex("name");
                String name = c.getString(posName);

                int posnumber = c.getColumnIndex("number");
                int number = c.getInt(posnumber);

                listaAthelete.add(new Athelete(id, name, number));

            } while (c.moveToNext());
        }
        return listaAthelete;
    }

    //=====================================================================================================


}
