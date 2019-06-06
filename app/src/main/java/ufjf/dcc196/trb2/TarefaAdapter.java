package ufjf.dcc196.trb2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Persistence.BibliotecaDbHelper;
import Persistence.TarefaBD;

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
                TarefaBD.tarefa._ID,
                TarefaBD.tarefa.COLUMN_NAME_TITULO,
                TarefaBD.tarefa.COLUMN_NAME_DESCRICAO,
                TarefaBD.tarefa.COLUMN_NAME_LIMITE,
                TarefaBD.tarefa.COLUMN_NAME_USADO,
                TarefaBD.tarefa.COLUMN_NAME_GRAU_DIFICULDADE,
                TarefaBD.tarefa.COLUMN_NAME_ESTADO,
                //TarefaTags.tarefaTeags.COLUMN_NAME_TAG,
        };
        String selecao = TarefaBD.tarefa._ID + " >= ?";
        String[] args = {"0"};
        String sort = TarefaBD.tarefa._ID + " DESC";
        items = dbR.query(TarefaBD.tarefa.TABLE_NAME, visao, selecao, args, null, null  , sort);

        int idxTitulo = items.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_TITULO);
        int idxDescricao = items.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_DESCRICAO);
        int idxLimite = items.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_LIMITE);
        int idxUsado = items.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_USADO);
        int idxGrauDificuldade = items.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_GRAU_DIFICULDADE);
        int idxEstado = items.getColumnIndexOrThrow(TarefaBD.tarefa.COLUMN_NAME_ESTADO);
        //int idxTag = items.getColumnIndexOrThrow(TarefaTags.tarefaTeags.COLUMN_NAME_TAG);

        items.moveToPosition(i);
        viewHolder.textTitulo.setText(items.getString(idxTitulo));
        viewHolder.textDescricao.setText(items.getString(idxDescricao));
        viewHolder.textLimite.setText(items.getString(idxLimite));
        viewHolder.textUltimaAtualizacao.setText(items.getString(idxUsado));
        viewHolder.textEstado.setText(items.getString(idxUsado));
        if(items.getInt(idxGrauDificuldade) > 5){
            viewHolder.textGrauDificuldade.setText("5");
        }else{
            viewHolder.textGrauDificuldade.setText(String.valueOf(items.getInt(idxGrauDificuldade)));
        }
        if(items.getInt(idxEstado) == 0){
            viewHolder.textEstado.setText("A fazer");
        }
        if(items.getInt(idxEstado) == 1){
            viewHolder.textEstado.setText("Em execução");
        }
        if(items.getInt(idxEstado) == 2){
            viewHolder.textEstado.setText("Bloqueada");
        }
        if(items.getInt(idxEstado) == 3){
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
