package Persistence;

import android.provider.BaseColumns;

public final class TarefaBD {
    public static final String Text_Type = " TEXT";
    public static final String Int_Type = " INTEGER";
    public static final String Data_Type = " DATE";
    public static final String Sep = ",";
    public static final String SQL_CREATE_TAREFA = "CREATE TABLE " + tarefa.TABLE_NAME + " (" +
            tarefa._ID + Int_Type + " PRIMARY KEY AUTOINCREMENT" + Sep +
            tarefa.COLUMN_NAME_TITULO + Text_Type + Sep +
            tarefa.COLUMN_NAME_DESCRICAO + Text_Type + Sep +
            tarefa.COLUMN_NAME_GRAU_DIFICULDADE + Int_Type + Sep +
            tarefa.COLUMN_NAME_LIMITE + Data_Type + Sep +
            tarefa.COLUMN_NAME_USADO + Data_Type + Sep +
            tarefa.COLUMN_NAME_ESTADO + Int_Type + ")";
    public static final String SQL_DROP_TAREFA = "DROP TABLE IF EXISTS " + tarefa.TABLE_NAME;

    public TarefaBD(){

    }

    public static final class tarefa implements BaseColumns{
        public static final String TABLE_NAME = "tarefa";
        public static final String COLUMN_NAME_TITULO = "titulo";
        public static final String COLUMN_NAME_DESCRICAO = "descricao";
        public static final String COLUMN_NAME_GRAU_DIFICULDADE = "grau_dificuldade";
        public static final String COLUMN_NAME_LIMITE = "limite";
        public static final String COLUMN_NAME_USADO = "usado";
        public static final String COLUMN_NAME_ESTADO = "estado";
    }

}


