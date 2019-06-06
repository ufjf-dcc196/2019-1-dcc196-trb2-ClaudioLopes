package Persistence;

import android.provider.BaseColumns;

public final class TarefaTags {
    public static final String Int_Type = " INTEGER";
    public static final String Sep = ",";
    public static final String SQL_CREATE_TAREFA_TAGS = "CREATE TABLE " + tarefaTeags.TABLE_NAME + " (" +
            tarefaTeags._ID + Int_Type + " PRIMARY KEY AUTOINCREMENT" + Sep +
            tarefaTeags.COLUMN_NAME_TAREFA + Int_Type + Sep +
            tarefaTeags.COLUMN_NAME_TAG + Int_Type + Sep +
            "FOREIGN KEY( " + tarefaTeags.COLUMN_NAME_TAREFA  + ") REFERENCES " + TarefaBD.tarefa.TABLE_NAME +"(" + TarefaBD.tarefa._ID + ") " + Sep +
            "FOREIGN KEY( " + tarefaTeags.COLUMN_NAME_TAG  + ") REFERENCES " + TagsBD.tags.TABLE_NAME +"(" + TagsBD.tags._ID + ") )";
    public static final String SQL_DROP_TAREFA_TAGS = "DROP TABLE IF EXISTIS" + tarefaTeags.TABLE_NAME;

    public TarefaTags(){

    }

    public static final class tarefaTeags implements BaseColumns{
        public static final String TABLE_NAME = "tarefa_tags";
        public static final String COLUMN_NAME_TAG = "tag_id";
        public static final String COLUMN_NAME_TAREFA = "tarefa_id";
    }
}
