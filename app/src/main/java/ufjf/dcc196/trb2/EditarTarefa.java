package ufjf.dcc196.trb2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditarTarefa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarefa);

        final EditText etTitulo = findViewById(R.id.editTextTitulo);
        final EditText etDescricao = findViewById(R.id.editTextDescricao);
        final EditText etGrauDificuldade = findViewById(R.id.editTextDificuldade);
        final EditText etDataLimite = findViewById(R.id.editTextDataLimite);
        final EditText etDataAtualizacao = findViewById(R.id.editTextDataAtualizacao);
        final EditText etEstado = findViewById(R.id.editTextEstado);

        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        try {
            etTitulo.setText(bundle.get("titulo").toString());
            etDescricao.setText(bundle.get("descricao").toString());
            etGrauDificuldade.setText(bundle.get("dificuldade").toString());
            etDataLimite.setText(bundle.get("limite").toString());
            etDataAtualizacao.setText(bundle.get("usado").toString());
            etEstado.setText(bundle.get("estado").toString());
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
                intent1.putExtra("usado", etDataAtualizacao.getText());
                intent1.putExtra("dificuldade", etGrauDificuldade.getText());
                intent1.putExtra("estado", etEstado.getText());
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
    }


}
