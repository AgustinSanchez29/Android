package com.example.parcial1;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class AppSQLiteOpenHelper  extends  SQLiteOpenHelper{

    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME= "ejemplo.db";
    public static final String TABLA_NOMBRES= "info";
    public static final String COLUMNA_ID= "id";
    public static final String COLUMNA_NOMBRE= "nombre";


    String _SQL= " CREATE TABLE " + TABLA_NOMBRES+ "(_id integer primary key autoincrement, nombre text not null,telefono text, email not null)";

    public AppSQLiteOpenHelper(Context context){
        super(context,DATABASE_NAME,  null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        try{
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME + "");
        }
        catch(SQLException e){
            //exepciones
        }
    }

}
