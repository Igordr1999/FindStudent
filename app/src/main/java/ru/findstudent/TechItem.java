package ru.findstudent;

import android.database.Cursor;

import ru.findstudent.db.DbHelper;

public class TechItem {
    public String code;
    public String record_create_date;
    public String record_update_date;
    public String photo;
    public String last_name;
    public String first_name;
    public String user_id;
    public String similarity;

    public TechItem() {}

    public TechItem(Cursor cursor) {
        code = cursor.getString(DbHelper.INDEX_CODE);
        record_create_date = cursor.getString(DbHelper.INDEX_RECORD_CREATE_DATE);
        record_update_date = cursor.getString(DbHelper.INDEX_RECORD_UPDATE_DATE);
        photo = cursor.getString(DbHelper.INDEX_PHOTO);
        last_name = cursor.getString(DbHelper.INDEX_LAST_NAME);
        first_name = cursor.getString(DbHelper.INDEX_FIRST_NAME);
        user_id = cursor.getString(DbHelper.INDEX_USER_ID);
        similarity = cursor.getString(DbHelper.INDEX_SIMILARITY);
    }

    public String getPictureUrl() {
        return photo != null ? photo : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechItem that = (TechItem) o;
        return code == that.code;
    }


    @Override
    public String toString() {
        return "TechItem{" +
                "code=" + code +
                ", photo='" + photo + '\'' +
                '}';
    }
}
