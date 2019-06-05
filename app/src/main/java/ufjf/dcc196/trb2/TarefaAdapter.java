package ufjf.dcc196.trb2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Persistence.BibliotecaDbHelper;
import Persistence.Tarefa;
import Persistence.TarefaTags;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.ViewHolder> {

    private Cursor items;
    private Context contexto;
    private OnTarefaAdapterClickListener listener;

    public TarefaAdapter(Cursor items, Context context){
            this.items = items;
            this.contexto = context;
    }

    public void setOnTarefaAdapterClickListener(OnTarefaAdapterClickListener listener){
        this.listener = listener;
    }
    public TarefaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inlf = LayoutInflater.from(context);
        View linha = inlf.inflate(R.layout.activity_tarefa_adapter, viewGroup, false);
        ViewHolder vh = new ViewHolder(linha);
        return vh;
    }

    public void onBindViewHolder(@NonNull TarefaAdapter.ViewHolder viewHolder, int i) {
        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(contexto);
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
        items = dbR.query(Tarefa.tarefa.TABLE_NAME, visao, selecao, args, null, null  , sort);

        int idxTitulo = items.getColumnIndexOrThrow(Tarefa.tarefa.COLUMN_NAME_TITULO);
        int idxDescricao = items.getColumnIndexOrThrow(Tarefa.tarefa.COLUMN_NAME_DESCRICAO);
        int idxLimite = items.getColumnIndexOrThrow(Tarefa.tarefa.COLUMN_NAME_LIMITE);
        int idxUsado = items.getColumnIndexOrThrow(Tarefa.tarefa.COLUMN_NAME_USADO);
        int idxGrauDificuldade = items.getColumnIndexOrThrow(Tarefa.tarefa.COLUMN_NAME_GRAU_DIFICULDADE);
        int idxEstado = items.getColumnIndexOrThrow(Tarefa.tarefa.COLUMN_NAME_ESTADO);
        //int idxTag = items.getColumnIndexOrThrow(TarefaTags.tarefaTeags.COLUMN_NAME_TAG);

        items.moveToPosition(i);
        viewHolder.textTitulo.setText(items.getString(idxTitulo));
        viewHolder.textDescricao.setText(items.getString(idxDescricao));
        viewHolder.textLimite.setText(items.getString(idxLimite));
        viewHolder.textEstado.setText(items.getString(idxUsado));
        if(items.getInt(idxGrauDificuldade) > 5){
            viewHolder.textGrauDificuldade.setText("5");
        }
        viewHolder.textGrauDificuldade.setText(String.valueOf(items.getInt(idxGrauDificuldade)));
        if(items.getInt(idxEstado) == 0){
            viewHolder.textEstado.setText("A fazer");
        }
        if(items.getInt(idxEstado) == 1){
            viewHolder.textEstado.setText("Em execução");
        }
        if(items.getInt(idxEstado) == 0){
            viewHolder.textEstado.setText("Bloqueada");
        }
        if(items.getInt(idxEstado) == 0){
            viewHolder.textEstado.setText("Concluida");
        }
        //viewHolder.textTags.setText(items.getString(idxTag));
    }

    public int getItemCount(){
        return items.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textTitulo;
        public TextView textDescricao;
        public TextView textLimite;
        public TextView textUltimaAtualizacao;
        public TextView textGrauDificuldade;
        public TextView textEstado;
        public TextView textTags;

        public void onClick(View v) {
            int possition = getAdapterPosition();
            if(possition != RecyclerView.NO_POSITION){
                listener.onTarefaAdapterClick(v, possition);
            }
        }

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textTitulo = itemView.findViewById(R.id.textViewTitulo);
            textDescricao = itemView.findViewById(R.id.textViewDescricao);
            textLimite = itemView.findViewById(R.id.textViewDataLimite);
            textUltimaAtualizacao = itemView.findViewById(R.id.textViewDataUpdate);
            textGrauDificuldade = itemView.findViewById(R.id.textViewGrauDificuldade);
            textEstado = itemView.findViewById(R.id.textViewestado);
            textTags = itemView.findViewById(R.id.textViewTags);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onTarefaAdapterClick(v, getAdapterPosition());
                    }
                }
            });

        }
    }

    public interface OnTarefaAdapterClickListener{
        public void onTarefaAdapterClick(View v, int possition);
    }

}
