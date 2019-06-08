package ufjf.dcc196.trb2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import Persistence.BibliotecaDbHelper;
import Persistence.TagsBD;
import Persistence.TarefaTags;

public class EditarTarefa extends AppCompatActivity {

    private String tags = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarefa);

        final EditText etTitulo = findViewById(R.id.editTextTitulo);
        final EditText etDescricao = findViewById(R.id.editTextDescricao);
        final EditText etGrauDificuldade = findViewById(R.id.editTextDificuldade);
        final EditText etDataLimite = findViewById(R.id.editTextDataLimite);
        final Spinner etEstado = findViewById(R.id.plEstado);
        final TextView tvTag = findViewById(R.id.textViewTags);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etEstado.setAdapter(adapter);

        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        try {
            etTitulo.setText(bundle.get("titulo").toString());
            etDescricao.setText(bundle.get("descricao").toString());
            etGrauDificuldade.setText(bundle.get("dificuldade").toString());
            etDataLimite.setText(bundle.get("limite").toString());
            etEstado.setSelection(Integer.parseInt(bundle.get("estado").toString()));
        }catch (NullPointerException e){

        }

        Button btnEditar = findViewById(R.id.btnConfirmar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("id", bundle.get("id").toString());
                intent1.putExtra("titulo", etTitulo.getText());
                intent1.putExtra("descricao", etDescricao.getText());
                intent1.putExtra("limite", etDataLimite.getText());
                intent1.putExtra("dificuldade", etGrauDificuldade.getText());
                intent1.putExtra("estado", etEstado.getSelectedItemPosition());
                intent1.putExtra("posicao", bundle.get("posicao").toString());
                setResult(Activity.RESULT_OK, intent1);
                finish();
            }
        });

        Button btnExcluir = findViewById(R.id.btnExcluir);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("id", bundle.get("id").toString());
                setResult(Activity.RESULT_OK, intent1);
                finish();
            }
        });

        Cursor c;
        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);
        SQLiteDatabase dbR = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                TagsBD.tags._ID,
                TagsBD.tags.COLUMN_NAME_TAG,
        };
        String selecao = TagsBD.tags._ID + " >= ?";
        String[] args = {"0"};
        String sort = TagsBD.tags._ID + " DESC";
        c = dbR.query(TagsBD.tags.TABLE_NAME, visao, selecao, args, null, null  , sort);
        c.moveToFirst();
        int idxNome = c.getColumnIndexOrThrow(TagsBD.tags.COLUMN_NAME_TAG);

        String[] itens = new String[c.getCount()];
        int i = 0;
        while (i < c.getCount()){
            itens[i] = (c.getString(idxNome));
            i ++;
            c.moveToNext();
        }
        final Spinner etTag = findViewById(R.id.plTag);
        ArrayAdapter adapterTag = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, itens);
        adapterTag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etTag.setAdapter(adapterTag);

        Button btnTags = findViewById(R.id.btnTags);

        btnTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tags += etTag.getSelectedItem().toString() + ", ";
                if(tags != null) {
                    tvTag.setText(tags);

                }
                criaTarefaTag((Integer) bundle.get("id"), etTitulo.getText().toString(), etTag.getSelectedItem().toString());
            }
        });
    }

    private void criaTarefaTag (int tarefaId, String tarefa, String tag){
        if(tag != null) {
            BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);
            SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TarefaTags.tarefaTeags.COLUMN_NAME_TAREFA_ID, tarefaId);
            values.put(TarefaTags.tarefaTeags.COLUMN_NAME_TAREFA, tarefa);
            values.put(TarefaTags.tarefaTeags.COLUMN_NAME_TAG, tag);
            long id = db.insert(TarefaTags.tarefaTeags.TABLE_NAME, null, values);
        }
    }


}
