package Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BibliotecaDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tarefas.db";

    public BibliotecaDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(Tarefa.SQL_CREATE_TAREFA);
        sqLiteDatabase.execSQL(Tags.SQL_CREATE_TAGS);
        sqLiteDatabase.execSQL(TarefaTags.SQL_CREATE_TAREFA_TAGS);
    }

    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL(Tarefa.SQL_DROP_TAREFA);
        sqLiteDatabase.execSQL(Tags.SQL_DROP_TAGS);
        sqLiteDatabase.execSQL(TarefaTags.SQL_DROP_TAREFA_TAGS);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
