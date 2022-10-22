package com.example.prueba2.configuracion;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.prueba2.tablas.Transacciones;

public class SQLiteConexion extends SQLiteOpenHelper
{
    public SQLiteConexion (Context context,
                           String dbname,
                           SQLiteDatabase.CursorFactory factory,
                           int version)
    {
        super(context, dbname, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //creacion de las tablas de la bd
        db.execSQL(Transacciones.CTPersonas); //creando la tabla de personas
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        //eliminamos la data y las tablas de la aplicacion
        db.execSQL(Transacciones.DropTPersonas);
        onCreate(db);
    }

}
