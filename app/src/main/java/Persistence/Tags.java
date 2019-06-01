package Persistence;

import android.provider.BaseColumns;

public final class Tags {
    public static final String Text_Type = " TEXT";
    public static final String Int_Type = " INTEGER";
    public static final String Sep = ",";
    public static final String SQL_CREATE_TAGS = "CREATE TABLE " + tags.TABLE_NAME + " (" +
            tags._ID + Int_Type + " PRIMARY KEY AUTOINCREMENT" + Sep +
            tags.COLUMN_NAME_TAG + Text_Type + ")";
    public static final String SQL_DROP_TAGS = "DROP TABLE IF EXISTIS" + tags.TABLE_NAME;

    public Tags(){

    }

    public static final class tags implements BaseColumns{
        public static final String TABLE_NAME = "tags";
        public static final String COLUMN_NAME_TAG = "tag";
    }
}
