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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Persistence.BibliotecaDbHelper;
import Persistence.Tarefa;
import Persistence.TarefaTags;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_MAIN = 1;
    public Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);

        //SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
        //String select = Tarefa.tarefa._ID + " >= ?";
        //String[] selectArgs = {"0"};
        //db.delete(Tarefa.tarefa.TABLE_NAME, select, selectArgs);

        final List<TarefaClass> itens = new ArrayList<>();


        SQLiteDatabase dbR = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                Tarefa.tarefa._ID,
                Tarefa.tarefa.COLUMN_NAME_TITULO,
                Tarefa.tarefa.COLUMN_NAME_DESCRICAO,
                Tarefa.tarefa.COLUMN_NAME_LIMITE,
                Tarefa.tarefa.COLUMN_NAME_USADO,
                Tarefa.tarefa.COLUMN_NAME_GRAU_DIFICULDADE,
                Tarefa.tarefa.COLUMN_NAME_ESTADO,
                //TarefaTags.tarefaTeags.COLUMN_NAME_TAG,
        };
        String selecao = Tarefa.tarefa._ID + " >= ?";
        String[] args = {"0"};
        String sort = Tarefa.tarefa._ID + " DESC";
        c = dbR.query(Tarefa.tarefa.TABLE_NAME, visao, selecao, args, null, null  , sort);
        c.moveToFirst();

        Button btnCadastrar = findViewById(R.id.btnCadastar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, CadastarTarefa.class);
                startActivityForResult(intent, MainActivity.REQUEST_MAIN);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.rvTarefa);
        TarefaAdapter tarefaAdapter = new TarefaAdapter(c, this);
        recyclerView.setAdapter(tarefaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode == MainActivity.REQUEST_MAIN) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);
                SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Tarefa.tarefa.COLUMN_NAME_TITULO, bundle.get("titulo").toString());
                values.put(Tarefa.tarefa.COLUMN_NAME_DESCRICAO, bundle.get("descricao").toString());
                values.put(Tarefa.tarefa.COLUMN_NAME_LIMITE, bundle.get("limite").toString());
                values.put(Tarefa.tarefa.COLUMN_NAME_USADO, bundle.get("usado").toString());
                values.put(Tarefa.tarefa.COLUMN_NAME_GRAU_DIFICULDADE, Integer.parseInt(bundle.get("dificuldade").toString()));
                long id = db.insert(Tarefa.tarefa.TABLE_NAME, null, values);
            }

            RecyclerView recyclerView = findViewById(R.id.rvTarefa);
            TarefaAdapter tarefaAdapter = new TarefaAdapter(c, this);
            recyclerView.setAdapter(tarefaAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
