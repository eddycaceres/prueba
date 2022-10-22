package com.example.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prueba2.configuracion.SQLiteConexion;
import com.example.prueba2.tablas.Personas;
import com.example.prueba2.tablas.Transacciones;

import java.util.ArrayList;;

public class ActivityList extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listperson;
    ArrayList<Personas> lista;
    ArrayList<String> listaconcatenada;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);

        listperson = (ListView) findViewById(R.id.listperson);

        GetListPerson();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaconcatenada );
        listperson.setAdapter(adp);

        listperson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Toast.makeText(getApplicationContext(), listaconcatenada.get(i).toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), listaconcatenada.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void GetListPerson()
    {
        SQLiteDatabase db = conexion.getReadableDatabase(); // Base de datos en modo de lectura
        Personas listpersonas = null;

        lista = new ArrayList<Personas>();  // Lista de Objetos del tipo personas

        Cursor cursor = db.rawQuery(Transacciones.GetPersonas,null);

        while(cursor.moveToNext())
        {
            listpersonas = new Personas();
            listpersonas.setId(cursor.getInt(0));
            listpersonas.setNombres(cursor.getString(1));
            listpersonas.setApellidos(cursor.getString(2));
            listpersonas.setEdad(cursor.getInt(3));
            listpersonas.setCorreo(cursor.getString(4));

            lista.add(listpersonas);
        }

        cursor.close();

        LLenarLista();

    }
    private void LLenarLista()
    {
        listaconcatenada = new ArrayList<String>();

        for(int i =0;  i < lista.size(); i++)
        {
            listaconcatenada.add(lista.get(i).getNombres() + " " +
                    lista.get(i).getApellidos() + " - " +
                    lista.get(i).getCorreo());
        }
    }
}
