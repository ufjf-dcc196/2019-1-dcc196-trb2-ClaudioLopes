package ufjf.dcc196.trb2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import Persistence.BibliotecaDbHelper;
import Persistence.TagsBD;
import Persistence.TarefaTags;

public class CadastarTarefa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastar_tarefa);
        final Spinner etEstado = findViewById(R.id.plEstado);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etEstado.setAdapter(adapter);

        final EditText etTitulo = findViewById(R.id.editTextTitulo);
        final EditText etDescricao = findViewById(R.id.editTextDescricao);
        final EditText etGrauDificuldade = findViewById(R.id.editTextDificuldade);
        final EditText etDataLimite = findViewById(R.id.editTextDataLimite);

        final TextView tvTaga = findViewById(R.id.textViewTags);

        Button btnConfirmar = findViewById(R.id.btnConfirmar);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("titulo", etTitulo.getText());
                intent.putExtra("descricao", etDescricao.getText());
                intent.putExtra("dificuldade", etGrauDificuldade.getText());
                intent.putExtra("limite", etDataLimite.getText());
                intent.putExtra("estado", etEstado.getSelectedItemPosition());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

}
