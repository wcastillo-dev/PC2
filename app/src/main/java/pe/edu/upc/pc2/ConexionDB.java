package pe.edu.upc.pc2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by itsoportetecnico on 12/12/2017.
 */

public class ConexionDB extends SQLiteOpenHelper {

        public static class DatosTabla implements BaseColumns {
        public static final String TABLE_NAME = "registro";
        public static final String COLUMN_FECHA = "fecha";
        public static final String COLUMN_TAREA = "tarea";

        private static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + DatosTabla.TABLE_NAME + " (" +
                        DatosTabla.COLUMN_FECHA + " TEXT," +
                        DatosTabla.COLUMN_TAREA + " TEXT)";

        private static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + DatosTabla.TABLE_NAME;
    }

    // Version de la Base de datos y nombre.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Calendario.db";

    public ConexionDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatosTabla.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DatosTabla.SQL_DELETE_TABLE);
        onCreate(db);
    }

    public ArrayList llenar_lv(String fecha){
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM registro where fecha='" + fecha + "'";
        Cursor registro = database.rawQuery(q,null);
        if(registro.moveToFirst()){
            do {
                lista.add(registro.getString(1));
            }while (registro.moveToNext());
        }
        return lista;
    }
}
