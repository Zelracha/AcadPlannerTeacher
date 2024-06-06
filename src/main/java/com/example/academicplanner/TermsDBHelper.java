package com.example.academicplanner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TermsDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "TermsList.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TERMS = "my_terms";
    private static final String TERM_ID = "term_id";
    private static final String TERM_NAME = "term_name";

    public TermsDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_TERMS +
                " (" + TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TERM_NAME + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
        onCreate(db);
    }

    public void showDeleteConfirmationDialog(final String termName, final Terms.DeleteConfirmationCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete " + termName + "?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> callback.onDeleteConfirmed())
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    public String addTerm(String termName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TERM_NAME, termName);

        long result = db.insert(TABLE_TERMS, null, cv);
        if (result != -1) {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            return String.valueOf(result);
        } else {
            return null;
        }
    }

    public List<String> getAllTerms() {
        List<String> termsList = new ArrayList<>();
        String readTermsQuery = "SELECT * FROM " + TABLE_TERMS + " ORDER BY " + TERM_NAME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(readTermsQuery, null);
        }
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String termName = cursor.getString(cursor.getColumnIndex(TERM_NAME));
                termsList.add(termName);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return termsList;
    }

    public void updateTerm(String termId, String newTermName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TERM_NAME, newTermName);

        long result = db.update(TABLE_TERMS, cv, TERM_ID + "=?", new String[]{termId});
        if (result != -1) {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTerm(String termId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_TERMS, TERM_ID + "=?", new String[]{termId});
        if (result != -1) {
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    public String getTermId(String termName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {TERM_ID};
        String selection = TERM_NAME + "=?";
        String[] selectionArgs = {termName};
        Cursor cursor = db.query(TABLE_TERMS, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String termId = cursor.getString(cursor.getColumnIndex(TERM_ID));
            cursor.close();
            return termId;
        } else {
            cursor.close();
            return null;
        }
    }
}
