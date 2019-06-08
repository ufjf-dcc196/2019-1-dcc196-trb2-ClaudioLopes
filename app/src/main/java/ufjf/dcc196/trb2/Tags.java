package ufjf.dcc196.trb2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import Persistence.BibliotecaDbHelper;
import Persistence.TagsBD;

public class Tags extends AppCompatActivity {
    public static final int REQUEST_MAIN = 1;
    public static final int REQUEST_EDT = 2;
    public Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

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

        Button btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Tags.this, CadastrarTag.class);
                startActivityForResult(intent, Tags.REQUEST_MAIN);
            }
        });

        recycleView();

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode == MainActivity.REQUEST_MAIN) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(this);
                SQLiteDatabase db = bibliotecaHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(TagsBD.tags.COLUMN_NAME_TAG, bundle.get("nome").toString());
                long id = db.insert(TagsBD.tags.TABLE_NAME, null, values);
            }
        }

        recycleView();
    }

    private void recycleView(){
        RecyclerView recyclerView = findViewById(R.id.rvTags);
        TagAdapter tagAdapter = new TagAdapter(c, this);
        recyclerView.setAdapter(tagAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tagAdapter.setOnTagAdapterClickListener(new TagAdapter.OnTagAdapterClickListener() {
            @Override
            public void onTagAdapterClick(View v, int possition) {
                c.moveToPosition(possition);
                int idxName = c.getColumnIndexOrThrow(TagsBD.tags.COLUMN_NAME_TAG);


                Intent intent = new Intent(Tags.this, ExibirTarefas.class);// mudar
                intent.putExtra("tag", c.getString(idxName));
                startActivityForResult(intent, Tags.REQUEST_EDT);
            }
        });
    }

}
