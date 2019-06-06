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

import Persistence.BibliotecaDbHelper;
import Persistence.TagsBD;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder>{

    private Cursor items;
    private Context contexto;
    private OnTagAdapterClickListener listener;

    public TagAdapter(Cursor items, Context context){
        this.items = items;
        this.contexto = context;
    }

    public void setOnTagAdapterClickListener(OnTagAdapterClickListener listener){
        this.listener = listener;
    }

    public TagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inlf = LayoutInflater.from(context);
        View linha = inlf.inflate(R.layout.activity_tag_adapter, viewGroup, false);
        ViewHolder vh = new ViewHolder(linha);
        return vh;
    }

    public void onBindViewHolder(@NonNull TagAdapter.ViewHolder viewHolder, int i) {
        BibliotecaDbHelper bibliotecaHelper = new BibliotecaDbHelper(contexto);
        SQLiteDatabase dbR = bibliotecaHelper.getReadableDatabase();
        String[] visao = {
                TagsBD.tags._ID,
                TagsBD.tags.COLUMN_NAME_TAG,
        };
        String selecao = TagsBD.tags._ID + " >= ?";
        String[] args = {"0"};
        String sort = TagsBD.tags._ID + " DESC";
        items = dbR.query(TagsBD.tags.TABLE_NAME, visao, selecao, args, null, null  , sort);

        int idxNome = items.getColumnIndexOrThrow(TagsBD.tags.COLUMN_NAME_TAG);

        items.moveToPosition(i);
        viewHolder.textNome.setText(items.getString(idxNome));
    }

    public int getItemCount(){
        return items.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textNome;

        public void onClick(View v) {
            int possition = getAdapterPosition();
            if(possition != RecyclerView.NO_POSITION){
                listener.onTagAdapterClick(v, possition);
            }
        }

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.textViewTag);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onTagAdapterClick(v, getAdapterPosition());
                    }
                }
            });

        }
    }

    public interface OnTagAdapterClickListener{
        public void onTagAdapterClick(View v, int possition);
    }
}
