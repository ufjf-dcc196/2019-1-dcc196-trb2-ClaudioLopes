package ufjf.dcc196.trb2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Persistence.BibliotecaDbHelper;
import Persistence.TarefaBD;
import Persistence.TarefaTags;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_MAIN = 1;
    public static final int REQUEST_EDT = 2;
    public static final int REQUEST_TAG = 3;
    public Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);

        SQLiteDatabase dbR = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                TarefaBD.tarefa._ID,
                TarefaBD.tarefa.COLUMN_NAME_TITULO,
                TarefaBD.tarefa.COLUMN_NAME_DESCRICAO,
                TarefaBD.tarefa.COLUMN_NAME_LIMITE,
                TarefaBD.tarefa.COLUMN_NAME_USADO,
                TarefaBD.tarefa.COLUMN_NAME_GRAU_DIFICULDADE,
                TarefaBD.tarefa.COLUMN_NAME_ESTADO,
        };
        String selecao = TarefaBD.tarefa.COLUMN_NAME_ESTADO + " >= ?";
        String[] args = {"0"};
        String sort = TarefaBD.tarefa.COLUMN_NAME_ESTADO + " DESC";
        c = dbR.query(TarefaBD.tarefa.TABLE_NAME, visao, selecao, args, null, null  , sort);
        c.moveToFirst();

        Button btnTags = findViewById(R.id.btnTag);

        btnTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, Tags.class);
                startActivityForResult(intent, MainActivity.REQUEST_TAG);
            }
        });

        Button btnCadastrar = findViewById(R.id.btnCadastar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, CadastarTarefa.class);
                startActivityForResult(intent, MainActivity.REQUEST_MAIN);
            }
        });

        recycleView();

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        if (requestCode == MainActivity.REQUEST_MAIN) {
            if (resultCode == Activity.RESULT_OK) {
                Date date = new Date();
                Bundle bundle = data.getExtras();
                BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);
                SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(TarefaBD.tarefa.COLUMN_NAME_TITULO, bundle.get("titulo").toString());
                values.put(TarefaBD.tarefa.COLUMN_NAME_DESCRICAO, bundle.get("descricao").toString());
                values.put(TarefaBD.tarefa.COLUMN_NAME_LIMITE, bundle.get("limite").toString());
                values.put(TarefaBD.tarefa.COLUMN_NAME_USADO, formataData.format(date));
                values.put(TarefaBD.tarefa.COLUMN_NAME_GRAU_DIFICULDADE, Integer.parseInt(bundle.get("dificuldade").toString()));
                values.put(TarefaBD.tarefa.COLUMN_NAME_ESTADO, Integer.parseInt(bundle.get("estado").toString()));
                long id = db.insert(TarefaBD.tarefa.TABLE_NAME, null, values);
            }
        }
        if (requestCode == MainActivity.REQUEST_EDT) {
            if (resultCode == Activity.RESULT_OK) {
                Date date = new Date();
                Bundle bundle = data.getExtras();
                BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);

                ContentValues values = new ContentValues();
                try {
                    SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                    values.put(TarefaBD.tarefa.COLUMN_NAME_TITULO, bundle.get("titulo").toString());
                    values.put(TarefaBD.tarefa.COLUMN_NAME_DESCRICAO, bundle.get("descricao").toString());
                    values.put(TarefaBD.tarefa.COLUMN_NAME_LIMITE, bundle.get("limite").toString());
                    values.put(TarefaBD.tarefa.COLUMN_NAME_USADO, formataData.format(date));
                    values.put(TarefaBD.tarefa.COLUMN_NAME_GRAU_DIFICULDADE, Integer.parseInt(bundle.get("dificuldade").toString()));
                    values.put(TarefaBD.tarefa.COLUMN_NAME_ESTADO, Integer.parseInt(bundle.get("estado").toString()));
                    String select = TarefaBD.tarefa._ID + " = ?";
                    String[] selectArgs = {bundle.get("id").toString()};
                    db.update(TarefaBD.tarefa.TABLE_NAME, values, select, selectArgs);
                }catch (NullPointerException ex){
                    SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                    String select = TarefaBD.tarefa._ID + " = ?";
                    String[] selectArgs = {bundle.get("id").toString()};
                    db.delete(TarefaBD.tarefa.TABLE_NAME, select, selectArgs);
                }
            }
        }

        recycleView();
    }

    private void recycleView(){
        RecyclerView recyclerView = findViewById(R.id.rvTarefa);
        TarefaAdapter tarefaAdapter = new TarefaAdapter(c, this);
        recyclerView.setAdapter(tarefaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tarefaAdapter.setOnTarefaAdapterClickListener(new TarefaAdapter.OnTarefaAdapterClickListener() {
            @Override
            public void onTarefaAdapterClick(View v, int possition) {
                c.moveToPosition(possition);
                int idxid = c.getColumnIndexOrThrow(TarefaBD.tarefa._ID);
                int idxTitulo = c.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_TITULO);
                int idxDescricao = c.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_DESCRICAO);
                int idxLimite = c.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_LIMITE);
                int idxUsado = c.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_USADO);
                int idxGrauDificuldade = c.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_GRAU_DIFICULDADE);
                int idxEstado = c.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_ESTADO);


                Intent intent = new Intent(MainActivity.this, EditarTarefa.class);
                intent.putExtra("id", c.getInt(idxid));
                intent.putExtra("titulo", c.getString(idxTitulo));
                intent.putExtra("descricao", c.getString(idxDescricao));
                intent.putExtra("limite", c.getString(idxLimite));
                intent.putExtra("usado", c.getString(idxUsado));
                intent.putExtra("dificuldade", c.getInt(idxGrauDificuldade));
                intent.putExtra("estado", c.getInt(idxEstado));
                intent.putExtra("posicao", possition);
                intent.putExtra("tag", getTags(c.getString(idxTitulo)));
                startActivityForResult(intent, MainActivity.REQUEST_EDT);
            }
        });
    }

    private String getTags(String tarefa){
        String tags = "";
        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);
        SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
        String[] projection = {
                TarefaTags.tarefaTeags.COLUMN_NAME_TAG
        };
        String selection = TarefaTags.tarefaTeags.COLUMN_NAME_TAREFA + " = ?";
        String[] selectionArgs = { tarefa };
        String sortOrder = TarefaTags.tarefaTeags.COLUMN_NAME_TAG + " DESC";
        Cursor cursor = db.query(TarefaTags.tarefaTeags.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.moveToFirst();

        int idxTag = cursor.getColumnIndexOrThrow(TarefaTags.tarefaTeags.COLUMN_NAME_TAG);
        while (cursor.getPosition() <= cursor.getCount() - 1){
            tags += cursor.getString(idxTag) + ", ";
            cursor.moveToNext();
        }

        return tags;
    }
}
