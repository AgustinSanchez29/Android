package com.example.parcial1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.nio.file.Files;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private Button btnNew, btnDelete, btnCall, btnSave, btnConsultar, btnNext,
            btnPrevius;
    private EditText txtNombre, txtPhone, txtEmail;
    Cursor dbCursor = null;
    int _id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNew = findViewById(R.id.btnNuevo);
        btnDelete = findViewById(R.id.btnEliminar);
        btnCall = findViewById(R.id.btnLlamar);
        btnNext = findViewById(R.id.btnSiguiente);
        btnPrevius = findViewById(R.id.btnAnterior);
        btnSave = findViewById(R.id.btnGuardar);
        btnConsultar = findViewById(R.id.btnBuscar);
        txtNombre = findViewById(R.id.txtNombre);
        txtPhone = findViewById(R.id.txtTelefono);
        txtEmail = findViewById(R.id.txtCorreo);



        btnConsultar.setOnClickListener(this);
        btnNew.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevius.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        int id= view.getId();

        switch (id){
            case R.id.btnNuevo:
                txtPhone.setText("");
                txtNombre.setText("");
                txtEmail.setText("");
                break;

            case R.id.btnEliminar:
                if(_id>0){
                    DeleteRecord();
                    GetAllData();
                }
                break;

            case R.id.btnAnterior:
                if(dbCursor.moveToPrevious()){
                    txtEmail.setText(dbCursor.getString(3));
                    txtPhone.setText(dbCursor.getString(2));
                    txtNombre.setText(dbCursor.getString(1));
                    _id= dbCursor.getInt(0);
                }
                break;

            case R.id.btnSiguiente:
                if(dbCursor.moveToNext()){
                    txtEmail.setText(dbCursor.getString(3));
                    txtPhone.setText(dbCursor.getString(2));
                    txtNombre.setText(dbCursor.getString(1));
                    _id= dbCursor.getInt(0);
                }
                break;

            case R.id.btnGuardar:
                boolean sw= false;
                sw= setData();
                break;


        }
    }




    public boolean DeleteRecord(){
        boolean sw= false;
        AppSQLiteOpenHelper _SQLite = new AppSQLiteOpenHelper(this);
        if(_id !=0){
            try{
                SQLiteDatabase db= _SQLite.getWritableDatabase();
                int result = db.delete("info","id = " + String.valueOf(_id),null);
                Toast.makeText(this,"Usuario Eliminado", Toast.LENGTH_SHORT).show();
                db.close();

                if(result >0){
                    sw= true;
                    this.txtNombre.setText("");
                    this.txtPhone.setText("");
                    this.txtEmail.setText("");
                }


            }catch(Exception _ex){

            }
        }
        return sw;
    }

    public void GetAllData(){

        AppSQLiteOpenHelper _SQLite = new AppSQLiteOpenHelper(this);
        SQLiteDatabase db= _SQLite.getWritableDatabase();
        dbCursor= null;

        try{
            dbCursor= db.rawQuery("SELECT * FROM info ORDER BY nombre", null);
            if(dbCursor !=null){
                dbCursor.moveToFirst();
            }
        }catch(Exception _ex){

        }
    }

    public Boolean setData(){
        Boolean sw= false;
        AppSQLiteOpenHelper _SQLite = new AppSQLiteOpenHelper(this);
        if (this.txtNombre.getText().toString() != ""&& this.txtPhone.getText().toString()!="" && this.txtEmail.getText().toString()!="" ){
            try{
                SQLiteDatabase db= _SQLite.getWritableDatabase();
                ContentValues rows= new ContentValues();
                rows.put("Nombre", this.txtNombre.getText().toString());
                rows.put("Telefono", this.txtPhone.getText().toString());
                rows.put("Email", this.txtEmail.getText().toString());
                db.insert("info",null,rows);
                Toast.makeText(this, "Usuario Agregado", Toast.LENGTH_SHORT).show();

                sw= true;

                this.txtNombre.setText("");
                this.txtPhone.setText("");
                this.txtEmail.setText("");
                this.GetAllData();
            } catch(Exception _ex){

            }
        }else{

        }
        return sw;

    }

}