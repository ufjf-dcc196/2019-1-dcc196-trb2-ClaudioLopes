package ufjf.dcc196.trb2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import Persistence.BibliotecaDbHelper;
import Persistence.TagsBD;
import Persistence.TarefaBD;
import Persistence.TarefaTags;

public class ExibirTarefas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_tarefas);

        TextView tvListaTarefas = findViewById(R.id.textViewListaTarefas);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Cursor c;
        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);
        SQLiteDatabase dbR = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                TarefaTags.tarefaTeags._ID,
                TarefaTags.tarefaTeags.COLUMN_NAME_TAG,
                TarefaTags.tarefaTeags.COLUMN_NAME_TAREFA,
                TarefaTags.tarefaTeags.COLUMN_NAME_TAREFA_ID
        };
        String selecao = TarefaTags.tarefaTeags.COLUMN_NAME_TAG + " = ?";
        String[] args = {bundle.getString("tag")};
        String sort = TarefaTags.tarefaTeags._ID + " ASC";
        c = dbR.query(TarefaTags.tarefaTeags.TABLE_NAME, visao, selecao, args, null, null  , sort);

        int idxTitulo = c.getColumnIndexOrThrow(TarefaTags.tarefaTeags.COLUMN_NAME_TAREFA);

        c.moveToFirst();
        String tarefas = "";
        while (c.getPosition() <= c.getCount() - 1){
            tarefas += c.getString(idxTitulo) + ", ";
            c.moveToNext();
        }

        tvListaTarefas.setText(tarefas);

    }
}
