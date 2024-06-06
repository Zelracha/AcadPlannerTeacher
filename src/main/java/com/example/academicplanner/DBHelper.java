    package com.example.academicplanner;

    import android.annotation.SuppressLint;
    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.widget.Toast;

    import androidx.annotation.Nullable;

    import java.util.ArrayList;
    import java.util.List;

    public class DBHelper extends SQLiteOpenHelper {
        private Context context;
        private static final String DATABASE_NAME = "Academic_Planner.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_TEACHER = "my_teachers";
        private static final String TEACHER_ID = "teacher_id";
        private static final String TEACHER_NAME = "teacher_name";
        private static final String TEACHER_SURNAME = "teacher_surname";
        private static final String TEACHER_PHONE = "teacher_phone";
        private static final String TEACHER_EMAIL = "teacher_email";
        private static final String TEACHER_PIC_BLOB = "teacher_pic_blob";

        private static final String TABLE_SUBJECT = "my_subjects";
        public static final String SUBJECT_ID = "subject_id";
        public static final String SUBJECT_NAME = "subject_name";
        public static final String SUBJECT_NOTE = "teacher_notes";
        public static final String SUBJECT_COLOR = "subject_color";
        public static final String SELECTED_TEACHERS = "selected_teachers";
        public static final String SELECTED_TERMS = "selected_terms";

        public DBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String teacher = "CREATE TABLE " + TABLE_TEACHER +
                    " (" + TEACHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TEACHER_NAME + " TEXT, " + TEACHER_SURNAME + " TEXT, "
                    + TEACHER_PHONE + " LONG, " + TEACHER_EMAIL + " TEXT, " +
                    TEACHER_PIC_BLOB + " TEXT );";

            String subject = "CREATE TABLE " + TABLE_SUBJECT +
                    " (" + SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SUBJECT_NAME + " TEXT, " + SUBJECT_NOTE + " TEXT, " +
                    SUBJECT_COLOR + " TEXT, " + SELECTED_TEACHERS + " TEXT, " +
                    SELECTED_TERMS + " TEXT);";

            db.execSQL(teacher);
            db.execSQL(subject);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String teacher = "DROP TABLE IF EXISTS " + TABLE_TEACHER;
            db.execSQL(teacher);

            String subject = "DROP TABLE IF EXISTS " + TABLE_SUBJECT;
            db.execSQL(subject);
            onCreate(db);
        }

        void addTeacher(String name, String surname, Long phone, String email, String selectedImageUri) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(TEACHER_NAME, name);
            cv.put(TEACHER_SURNAME, surname);
            if (phone != null) cv.put(TEACHER_PHONE, phone);
            if (email != null) cv.put(TEACHER_EMAIL, email);
            if (selectedImageUri != null) cv.put(TEACHER_PIC_BLOB, selectedImageUri);

            long result = db.insert(TABLE_TEACHER, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        }

        public Cursor readTeacherData() {
            String readTeacher = "SELECT * FROM " + TABLE_TEACHER + " ORDER BY " + TEACHER_SURNAME + " ASC";
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;
            if (db != null) {
                cursor = db.rawQuery(readTeacher, null);
            }
            return cursor;
        }

        void updateTeacherData(String teacher_row_id, String name, String surname, Long phone, String email, String imageUri) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(TEACHER_NAME, name);
            cv.put(TEACHER_SURNAME, surname);
            if (phone != null) cv.put(TEACHER_PHONE, phone);
            if (email != null) cv.put(TEACHER_EMAIL, email);
            if (imageUri != null) cv.put(TEACHER_PIC_BLOB, imageUri);

            long result = db.update(TABLE_TEACHER, cv, "teacher_id=?", new String[]{teacher_row_id});
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        }

        void deleteTeacherRow(String teacher_row_id) {
            SQLiteDatabase db = this.getWritableDatabase();
            long result = db.delete(TABLE_TEACHER, "teacher_id=?", new String[]{teacher_row_id});
            if (result == -1) {
                Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
            }
        }

        public List<String> getAllTeachers() {
            List<String> teacherList = new ArrayList<>();
            String readTeacher = "SELECT * FROM " + TABLE_TEACHER + " ORDER BY " + TEACHER_SURNAME + " ASC";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            if (db != null) {
                cursor = db.rawQuery(readTeacher, null);
            }
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(TEACHER_NAME));
                    @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(TEACHER_SURNAME));
                    String fullName = firstName + " " + lastName;
                    teacherList.add(fullName);
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return teacherList;
        }

        void addSubject(String subName, String subNote, String subjectColor, String selectedTeachers, String selectedTerms) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(SUBJECT_NAME, subName);
            cv.put(SUBJECT_NOTE, subNote);
            cv.put(SUBJECT_COLOR, subjectColor);
            cv.put(SELECTED_TEACHERS, selectedTeachers);
            cv.put(SELECTED_TERMS, selectedTerms);

            long result = db.insert(TABLE_SUBJECT, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        }

        public Cursor readSubjectData() {
            String readSubject = "SELECT * FROM " + TABLE_SUBJECT + " ORDER BY " + SUBJECT_NAME + " ASC";
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;
            if (db != null) {
                cursor = db.rawQuery(readSubject, null);
            }
            return cursor;
        }

        void updateSubjectData(String subject_row_id, String subName, String subNote, String subjectColor, String selectedTeachers, String selectedTerms) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(SUBJECT_NAME, subName);
            cv.put(SUBJECT_NOTE, subNote);
            cv.put(SUBJECT_COLOR, subjectColor);
            cv.put(SELECTED_TEACHERS, selectedTeachers);
            cv.put(SELECTED_TERMS, selectedTerms);

            long result = db.update(TABLE_SUBJECT, cv, "subject_id=?", new String[]{subject_row_id});
            if (result == -1) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        }

        void deleteSubjectRow(String subject_row_id) {
            SQLiteDatabase db = this.getWritableDatabase();
            long result = db.delete(TABLE_SUBJECT, "subject_id=?", new String[]{subject_row_id});
            if (result == -1) {
                Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
            }
        }
    }
