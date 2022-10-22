package com.example.prueba2.tablas;

public class Transacciones
{
    //nombre de la bd
    public static final String NameDatabase = "PM01DB";

    //creacion de las tablas de bd
    public static final String TbPersonas ="personas";

    //campos de la tabla personas
    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = "edad";
    public static final String correo = "correo";

    //DDL
    public static final String CTPersonas = "CREATE TABLE personas (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombres TEXT, apellidos TEXT, edad INTEGER, correo TEXT)";

    public static final String GetPersonas = "SELECT * FROm " + Transacciones.TbPersonas;

    //borrar tabla
    public static final String DropTPersonas = "DROP TABLE IF EXISTS personas";

}
