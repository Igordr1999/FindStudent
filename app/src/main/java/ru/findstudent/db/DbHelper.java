package ru.findstudent.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.findstudent.TechItem;

import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 5;
    private static final String DB_NAME = "test123455";

    public static final String TABLE_SEARCH= "search";

    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_RECORD_CREATE_DATE = "record_create_date";
    public static final String COLUMN_RECORD_UPDATE_DATE = "record_update_date";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_SIMILARITY = "similarity";

    public static final int INDEX_CODE = 1;
    public static final int INDEX_RECORD_CREATE_DATE = 2;
    public static final int INDEX_RECORD_UPDATE_DATE = 3;
    public static final int INDEX_PHOTO = 4;
    public static final int INDEX_LAST_NAME = 5;
    public static final int INDEX_FIRST_NAME = 6;
    public static final int INDEX_USER_ID = 7;
    public static final int INDEX_SIMILARITY = 8;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE " + TABLE_SEARCH + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CODE + " TEXT,"
                + COLUMN_RECORD_CREATE_DATE + " TEXT,"
                + COLUMN_RECORD_UPDATE_DATE + " TEXT,"
                + COLUMN_PHOTO + " TEXT,"
                + COLUMN_LAST_NAME + " TEXT,"
                + COLUMN_FIRST_NAME + " TEXT,"
                + COLUMN_USER_ID + " Text,"
                + COLUMN_SIMILARITY + " TEXT)";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH);
        onCreate(db);
    }

    public boolean hasTechnologies() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE_SEARCH, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    public void insertTechnologies(List<TechItem> items) {
        SQLiteDatabase db = getWritableDatabase();
        for (TechItem item : items) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CODE, item.code);
            values.put(COLUMN_RECORD_CREATE_DATE, item.record_create_date);
            values.put(COLUMN_RECORD_UPDATE_DATE, item.record_update_date);
            values.put(COLUMN_PHOTO, item.photo);
            values.put(COLUMN_LAST_NAME, item.last_name);
            values.put(COLUMN_FIRST_NAME, item.first_name);
            values.put(COLUMN_USER_ID, item.user_id);
            values.put(COLUMN_SIMILARITY, item.similarity);
            db.insert(TABLE_SEARCH, null, values);
        }
        db.close();
    }

    public Cursor getTechnologies() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_SEARCH, null, null, null, null, null, null, null);
    }

    public void deleteTechnologies() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SEARCH, null, null);
        db.close();
    }
}
