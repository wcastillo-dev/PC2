package pe.edu.upc.pc2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLData;
import java.util.ArrayList;


public class AddDatos extends AppCompatActivity{

    Button btnAgregar, btnBuscar, btnEliminar, btnActualizar;
    EditText txtFecha, txtTarea;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertar_datos);

        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        txtFecha = (EditText) findViewById(R.id.txtFecha);
        txtTarea = (EditText) findViewById(R.id.txtTarea);

        final ConexionDB conexionDB = new ConexionDB(getApplicationContext());

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = conexionDB.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(ConexionDB.DatosTabla.COLUMN_FECHA, txtFecha.getText().toString());
                valores.put(ConexionDB.DatosTabla.COLUMN_TAREA, txtTarea.getText().toString());

                Long rowGuardado = db.insert(ConexionDB.DatosTabla.TABLE_NAME, null, valores);
                Toast.makeText(getApplicationContext(), "Se guardo el dato: " + rowGuardado, Toast.LENGTH_LONG).show();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = conexionDB.getReadableDatabase();
                String[] argsel = {txtFecha.getText().toString()};
                String[] projection= {ConexionDB.DatosTabla.COLUMN_TAREA};

                Cursor c = db.query(ConexionDB.DatosTabla.TABLE_NAME, projection, ConexionDB.DatosTabla.COLUMN_FECHA+"=?",argsel,null,null,null);
                c.moveToFirst();
                txtTarea.setText(c.getString(0));
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = conexionDB.getWritableDatabase();
                String selection = ConexionDB.DatosTabla.COLUMN_FECHA+"=?";
                String[] argsel = {txtFecha.getText().toString()};

                db.delete(ConexionDB.DatosTabla.TABLE_NAME, selection, argsel);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = conexionDB.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(ConexionDB.DatosTabla.COLUMN_TAREA, txtTarea.getText().toString());
                String[] argsel = {txtFecha.getText().toString()};
                String selection = ConexionDB.DatosTabla.COLUMN_FECHA+"=?";

                int count = db.update(ConexionDB.DatosTabla.TABLE_NAME, valores, selection, argsel);
            }
        });

    }
}
