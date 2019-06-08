package Persistence;

import android.provider.BaseColumns;

public final class TarefaTags {
    public static final String Int_Type = " INTEGER";
    public static final String Text_Type = " TEXT";
    public static final String Sep = ", ";
    public static final String SQL_CREATE_TAREFA_TAGS = "CREATE TABLE " + tarefaTeags.TABLE_NAME + " (" +
            tarefaTeags._ID + Int_Type + " PRIMARY KEY AUTOINCREMENT" + Sep +
            tarefaTeags.COLUMN_NAME_TAG + Text_Type + Sep +
            tarefaTeags.COLUMN_NAME_TAREFA_ID + Int_Type + Sep +
            tarefaTeags.COLUMN_NAME_TAREFA + Text_Type + ")";
    public static final String SQL_DROP_TAREFA_TAGS = "DROP TABLE IF EXISTS " + tarefaTeags.TABLE_NAME;

    public TarefaTags(){

    }

    public static final class tarefaTeags implements BaseColumns{
        public static final String TABLE_NAME = "tarefa_tags";
        public static final String COLUMN_NAME_TAG = "tag";
        public static final String COLUMN_NAME_TAREFA = "tarefa_name";
        public static final String COLUMN_NAME_TAREFA_ID = "tarefa_id";
    }
}
