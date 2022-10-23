package com.example.prueba2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.prueba2.configuracion.SQLiteConexion;
import com.example.prueba2.tablas.Personas;
import com.example.prueba2.tablas.Transacciones;

import java.util.ArrayList;

public class ActivityCombo extends AppCompatActivity {
    /*Variables Globales*/

    SQLiteConexion conexion;
    Spinner sppersonas;
    EditText nombres, apellidos, correo;

    ArrayList<Personas> lista;
    ArrayList<String> listString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        sppersonas = (Spinner) findViewById(R.id.sppersonas);
        nombres = (EditText) findViewById(R.id.txtnombres_ac);
        apellidos = (EditText) findViewById(R.id.txtapellidos_ac);
        correo = (EditText) findViewById(R.id.txtcorreo_ac);

        ObtenerPersonas();

        ArrayAdapter<CharSequence> adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listString);
        sppersonas.setAdapter(adp);

        sppersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                try
                {
                    nombres.setText(lista.get(i).getNombres());
                    apellidos.setText(lista.get(i).getApellidos());
                    correo.setText(lista.get(i).getCorreo());
                }
                catch (Exception ex)
                {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void ObtenerPersonas()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas listapersonas = null;
        lista = new ArrayList<Personas>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Transacciones.TbPersonas, null);

        while(cursor.moveToNext())
        {
            listapersonas = new Personas();
            listapersonas.setId(cursor.getInt(0));
            listapersonas.setNombres(cursor.getString(1));
            listapersonas.setApellidos(cursor.getString(2));
            listapersonas.setEdad(cursor.getInt(3));
            listapersonas.setCorreo(cursor.getString(4));

            lista.add(listapersonas);
        }

        cursor.close();

        fillcombo();
    }

    private void fillcombo()
    {
        listString = new ArrayList<String>();

        for(int i = 0; i< lista.size(); i++)
        {
            listString.add(lista.get(i).getNombres() + " | "+
                    lista.get(i).getApellidos() + " | "+
                    lista.get(i).getCorreo());
        }
    }
}