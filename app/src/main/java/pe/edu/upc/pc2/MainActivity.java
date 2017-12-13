package pe.edu.upc.pc2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnCalendario,btnAgregar;
    TextView txtCalendario;
    ListView lstLista;
    ConexionDB db;
    String fecha;

    ArrayList<String> lista;
    ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCalendario = (Button)findViewById(R.id.btnCalendario);
        btnAgregar = (Button)findViewById(R.id.btnAgregar);
        txtCalendario = (TextView)findViewById(R.id.txtCalendario);
        lstLista = (ListView)findViewById(R.id.lstLista);

        Intent intentRetorno = getIntent();
        String date = intentRetorno.getStringExtra("date");
        txtCalendario.setText(date);

        if(txtCalendario.getText().toString()==""){
            Toast.makeText(getApplicationContext(), "holaaaaa", Toast.LENGTH_SHORT).show();
        }else{
            MostrarLista();
        }

        //
        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDatos.class);
                intent.putExtra("FECHA", txtCalendario.getText().toString());
                startActivity(intent);
            }
        });

    }

    private void MostrarLista(){
        final ConexionDB db = new ConexionDB(getApplicationContext());
        lista = db.llenar_lv(txtCalendario.getText().toString());
        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);
        lstLista.setAdapter(adaptador);
    }

    @Override
    public void onClick(View view) {
    }
}
