package com.example.newsapp;

import android.provider.BaseColumns;

public final class NewsDBContract {
    private NewsDBContract() {
    }

    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_LastName = "Lastname";
        public static final String COLUMN_Name = "Name";
        public static final String COLUMN_IsAdmin = "IsAdmin";
        public static final String COLUMN_Login = "Login";
        public static final String COLUMN_Password = "Password";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " +
                        TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_LastName + " TEXT NOT NULL, " +
                        COLUMN_Name + " TEXT NOT NULL, " +
                        COLUMN_IsAdmin + " INTEGER NOT NULL, " +
                        COLUMN_Login + " TEXT NOT NULL UNIQUE, " +
                        COLUMN_Password + " TEXT NOT NULL);";

        public static final String SQL_CREATE_ADMIN =
                "INSERT INTO " + TABLE_NAME + " (" +
                        COLUMN_LastName + ", " +
                        COLUMN_Name + ", " +
                        COLUMN_IsAdmin + ", " +
                        COLUMN_Login + ", " +
                        COLUMN_Password + ") VALUES " +
                        "('Админов', 'Админ', 1, 'admin', 'admin');";
    }

    public static class News implements BaseColumns {

        public static final String TABLE_NAME = "News";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_TEXT = "News_text";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " +
                        TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_TEXT + " TEXT NOT NULL);";
    }
}
