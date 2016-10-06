package com.example.hristijan.tabs2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hristijan.tabs2.dummy.NoteItem;
import com.example.hristijan.tabs2.items.Classroom;
import com.example.hristijan.tabs2.items.Consultation;
import com.example.hristijan.tabs2.items.Lecture;
import com.example.hristijan.tabs2.items.News;
import com.example.hristijan.tabs2.items.Staff;
import com.example.hristijan.tabs2.items.StaffCategory;
import com.example.hristijan.tabs2.items.Study;
import com.example.hristijan.tabs2.items.Subject;
import com.example.hristijan.tabs2.items.SubjectType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hristijan on 7/31/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FINKI";
    private static final String TABLE_STUDIES = "STUDIES";
    private static final String TABLE_NOTE = "NOTES";
    private static final String TABLE_STAFF_CATEGORY = "STAFF_CATEGORY";
    private static final String TABLE_STAFF = "STAFF";
    private static final String TABLE_CLASSROOM = "CLASSROOM";
    private static final String TABLE_SUBJECT_TYPE = "SUBJECT_TYPE";
    private static final String TABLE_SUBJECT = "SUBJECT";
    private static final String TABLE_MY_SUBJECT = "MY_SUBJECT";
    private static final String TABLE_LECTURE = "LECTURE";
    private static final String TABLE_CONSULTATION = "CONSULTATION";
    private static final String TABLE_NEWS = "NEWS";


    //TABLE STUDIES
    private static final String KEY_STUDY_ID = "STUDY_ID";
    private static final String KEY_STUDY_NAME = "STUDY_NAME";
    private static final String KEY_STUDY_DESCRIPTION = "STUDY_DESCRIPTION";
    private static final String KEY_STUDY_LEVEL = "STUDY_LEVEL";

    //TABLE NOTE
    private static final String KEY_NOTE_ID = "NOTE_ID";
    private static final String KEY_NOTE_TITLE = "NOTE_TITLE";
    private static final String KEY_NOTE_CONTENT = "NOTE_CONTENT";
    private static final String KEY_NOTE_DONE = "NOTE_DONE";
    private static final String KEY_NOTE_DATETIME = "NOTE_DATETIME";

    //TABLE STAFF CATEGORY
    private static final String KEY_STAFF_CATEGORY_ID = "STAFF_CATEGORY_ID";
    private static final String KEY_STAFF_CATEGORY_NAME = "STAFF_CATEGORY_NAME";

    //TABLE STAFF
    private static final String KEY_STAFF_LEVEL = "LEVEL";
    private static final String KEY_STAFF_FIRST_NAME = "FIRST_NAME";
    private static final String KEY_STAFF_LAST_NAME = "LAST_NAME";
    private static final String KEY_STAFF_IMAGE_URL = "IMAGE_URL";
    private static final String KEY_STAFF_EMAIL = "EMAIL";
    private static final String KEY_STAFF_RESUME = "RESUME";

    //TABLE CLASSROOM
    private static final String KEY_CLASSROOM_ID = "CLASSROOM_ID";
    private static final String KEY_CLASSROOM_NAME = "CLASSROOM_NAME";
    private static final String KEY_CLASSROOM_DESCRIPTIION = "DESCRIPTIION";

    //TABLE SUBJECT_TYPE
    private static final String KEY_SUBJECT_TYPE_ID = "SUBJECT_TYPE_ID";
    private static final String KEY_SUBJECT_TYPE_NAME = "SUBJECT_TYPE_NAME";

    //TABLE SUBJECT
    private static final String KEY_SUBJECT_ID = "SUBJECT_ID";
    private static final String KEY_SUBJECT_NAME = "SUBJECT_NAME";
    private static final String KEY_SUBJECT_SEMESTER = "SUBJECT_SEMESTER";
    private static final String KEY_SUBJECT_COMPULSORY = "SUBJECT_COMPULSORY";
    private static final String KEY_SUBJECT_DESCRIPTION = "SUBJECT_DESCRIPTION";
    private static final String KEY_SUBJECT_CREDITS = "SUBJECT_CREDITS";
    private static final String KEY_SUBJECT_CLASSES = "SUBJECT_CLASSES";

    //TABLE MY_SUBJECT
    private static final String KEY_MY_SUBJECTS_ID = "MY_SUBJECTS_ID";

    //TABLE LECTURE
    private static final String KEY_LECTURE_ID = "LECTURE_ID";
    private static final String KEY_LECTURE_STAFF_EMAIL = "LECTURE_STAFF_EMAIL";
    private static final String KEY_LECTURE_SUBJECT_ID = "LECTURE_SUBJECT_ID";
    private static final String KEY_LECTURE_CLASSROOM_ID = "LECTURE_CLASSROOM_ID";
    private static final String KEY_LECTURE_SUBJECT_TYPE_ID = "LECTURE_SUBJECT_TYPE_ID";
    private static final String KEY_LECTURE_DATETIME_FROM = "LECTURE_DATETIME_FROM";
    private static final String KEY_LECTURE_DATETIME_TO = "LECTURE_DATETIME_TO";
    private static final String KEY_LECTURE_DAY_IN_WEEK = "LECTURE_DAY_IN_WEEK";

    //TABLE CONSULTATION
    private static final String KEY_CONSULTATION_ID = "CONSULTATION_ID";
    private static final String KEY_CONSULTATION_STAFF_EMAIL = "CONSULTATION_STAFF_EMAIL";
    private static final String KEY_CONSULTATION_ROOM = "CONSULTATION_ROOM";
    private static final String KEY_CONSULTATION_DATETIME_FROM = "CONSULTATION_DATETIME_FROM";
    private static final String KEY_CONSULTATION_DATETIME_TO = "CONSULTATION_DATETIME_TO";
    private static final String KEY_CONSULTATION_DAY_IN_WEEK = "CONSULTATION_DAY_IN_WEEK";

    //TABLE NEWS
    private static final String KEY_NEWS_ID = "NEWS_ID";
    private static final String KEY_NEWS_TITLE = "NEWS_TITLE";
    private static final String KEY_NEWS_CONTENT = "NEWS_CONTENT";
    private static final String KEY_NEWS_DATETIME = "NEWS_DATETIME";


    // SQL CREATE TABLES

    //private static final String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "( ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, CONTENT TEXT, DATE_CREATED DATETIME, DONE BOOLEAN)";


    private static final String CREATE_TABLE_STUDIES = "CREATE TABLE " + TABLE_STUDIES + " ("
            + KEY_STUDY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_STUDY_NAME + " TEXT,"
            + KEY_STUDY_DESCRIPTION + " TEXT,"
            + KEY_STUDY_LEVEL + " INTEGER"
            + ")";

    private static final String CREATE_TABLE_NOTE = "CREATE TABLE " + TABLE_NOTE + " ("
            + KEY_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NOTE_TITLE + " TEXT,"
            + KEY_NOTE_CONTENT + " TEXT,"
            + KEY_NOTE_DONE + " BOOLEAN,"
            + KEY_NOTE_DATETIME + " DATETIME"
            + ")";

    private static final String CREATE_TABLE_STAFF_CATEGORY = "CREATE TABLE " + TABLE_STAFF_CATEGORY + " ("
            + KEY_STAFF_CATEGORY_ID + " TEXT PRIMARY KEY,"
            + KEY_STAFF_CATEGORY_NAME + " TEXT"
            + ")";

    private static final String CREATE_TABLE_STAFF = "CREATE TABLE " + TABLE_STAFF + " ("
            + KEY_STAFF_EMAIL + " TEXT PRIMARY KEY,"
            + KEY_STAFF_CATEGORY_ID + " TEXT,"
            + KEY_STAFF_LEVEL + " TEXT,"
            + KEY_STAFF_FIRST_NAME + " TEXT,"
            + KEY_STAFF_LAST_NAME + " TEXT,"
            + KEY_STAFF_IMAGE_URL + " TEXT,"
            + KEY_STAFF_RESUME + " TEXT"
            + ")";

    private static final String CREATE_TABLE_CLASSROOM = "CREATE TABLE " + TABLE_CLASSROOM + " ("
            + KEY_CLASSROOM_ID + " TEXT PRIMARY KEY,"
            + KEY_CLASSROOM_NAME + " TEXT,"
            + KEY_CLASSROOM_DESCRIPTIION + " TEXT"
            + ")";

    private static final String CREATE_TABLE_SUBJECT_TYPE = "CREATE TABLE " + TABLE_SUBJECT_TYPE + " ("
            + KEY_SUBJECT_TYPE_ID + " TEXT PRIMARY KEY,"
            + KEY_SUBJECT_TYPE_NAME + " TEXT"
            + ")";

    private static final String CREATE_TABLE_SUBJECT = "CREATE TABLE " + TABLE_SUBJECT + " ("
            + KEY_SUBJECT_ID + " TEXT PRIMARY KEY,"
            + KEY_SUBJECT_NAME + " TEXT,"
            + KEY_SUBJECT_SEMESTER + " INTEGER,"
            + KEY_SUBJECT_COMPULSORY + " BOOLEAN,"
            + KEY_SUBJECT_DESCRIPTION + " TEXT,"
            + KEY_SUBJECT_CREDITS + " INTEGER,"
            + KEY_SUBJECT_CLASSES + " TEXT"
            + ")";

    private static final String CREATE_TABLE_MY_SUBJECT = "CREATE TABLE " + TABLE_MY_SUBJECT + " ("
            + KEY_MY_SUBJECTS_ID + " TEXT PRIMARY KEY"
            + ")";


    private static final String CREATE_TABLE_LECTURE = "CREATE TABLE " + TABLE_LECTURE + " ("
            + KEY_LECTURE_ID + " TEXT PRIMARY KEY,"
            + KEY_LECTURE_STAFF_EMAIL + " TEXT,"
            + KEY_LECTURE_SUBJECT_ID + " TEXT,"
            + KEY_LECTURE_CLASSROOM_ID + " TEXT,"
            + KEY_LECTURE_SUBJECT_TYPE_ID + " TEXT,"
            + KEY_LECTURE_DATETIME_FROM + " TEXT,"
            + KEY_LECTURE_DATETIME_TO + " TEXT,"
            + KEY_LECTURE_DAY_IN_WEEK + " INTEGER"
            + ")";


    private static final String CREATE_TABLE_CONSULTATION = "CREATE TABLE " + TABLE_CONSULTATION + " ("
            + KEY_CONSULTATION_ID + " TEXT PRIMARY KEY,"
            + KEY_CONSULTATION_STAFF_EMAIL + " TEXT,"
            + KEY_CONSULTATION_ROOM + " TEXT,"
            + KEY_CONSULTATION_DATETIME_FROM + " TEXT,"
            + KEY_CONSULTATION_DATETIME_TO + " TEXT,"
            + KEY_CONSULTATION_DAY_IN_WEEK + " INTEGER"
            + ")";

    private static final String CREATE_TABLE_NEWS = "CREATE TABLE " + TABLE_NEWS + " ("
            + KEY_NEWS_ID + " TEXT PRIMARY KEY,"
            + KEY_NEWS_TITLE + " TEXT,"
            + KEY_NEWS_CONTENT + " TEXT,"
            + KEY_NEWS_DATETIME + " TEXT"
            + ")";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDIES);
        db.execSQL(CREATE_TABLE_NOTE);
        db.execSQL(CREATE_TABLE_STAFF_CATEGORY);
        db.execSQL(CREATE_TABLE_STAFF);
        db.execSQL(CREATE_TABLE_CLASSROOM);
        db.execSQL(CREATE_TABLE_SUBJECT_TYPE);
        db.execSQL(CREATE_TABLE_SUBJECT);
        db.execSQL(CREATE_TABLE_MY_SUBJECT);
        db.execSQL(CREATE_TABLE_LECTURE);
        db.execSQL(CREATE_TABLE_CONSULTATION);
        db.execSQL(CREATE_TABLE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_STUDIES);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_STAFF_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_STAFF);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CLASSROOM);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_SUBJECT_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_MY_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_LECTURE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_CONSULTATION);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_NEWS);
        onCreate(db);
    }

    public void addStudy(Study study) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STUDY_NAME, study.getName());
        values.put(KEY_STUDY_DESCRIPTION, study.getDescription());
        values.put(KEY_STUDY_LEVEL, study.getLevel());

        db.insert(TABLE_STUDIES, null, values);

        db.close();
    }

    public ArrayList<Study> getStudies(int level) {
        ArrayList<Study> studiesList = new ArrayList<Study>();

        String selectQuery = "SELECT  * FROM " + TABLE_STUDIES
                + " WHERE " + KEY_STUDY_LEVEL + "=" + level
                + " ORDER BY " + KEY_STUDY_NAME + " DESC;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Study study = new Study(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                studiesList.add(study);
            } while (cursor.moveToNext());
        }

        Log.i("GET", "Notes data returned!");
        return studiesList;
    }

    public void addNote(NoteItem note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_TITLE, note.getTitle());
        values.put(KEY_NOTE_CONTENT, note.getContent());
        values.put(KEY_NOTE_DONE, false);
        values.put(KEY_NOTE_DATETIME, System.currentTimeMillis());

        db.insert(TABLE_NOTE, null, values);

        db.close();
    }

    public NoteItem getNote(int ID) {
        NoteItem note = null;

        String selectQuery = "SELECT  * FROM " + TABLE_NOTE + " WHERE " + KEY_NOTE_ID + "=" + ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                note = new NoteItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), (cursor.getInt(3) == 1), new Date(cursor.getLong(4)));
            } while (cursor.moveToNext());
        }

        return note;
    }

    public ArrayList<NoteItem> getNotes() {
        ArrayList<NoteItem> notesList = new ArrayList<NoteItem>();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTE + " ORDER BY " + KEY_NOTE_DATETIME + " DESC;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NoteItem note = new NoteItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), (cursor.getInt(3) == 1), new Date(cursor.getLong(4)));
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        Log.i("GET", "Notes data returned!");
        return notesList;
    }

    public void deleteNote(NoteItem note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, KEY_NOTE_ID + " = ?", new String[] { String.valueOf(note.getID()) });
        db.close();
    }

    public void deleteLectures() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete = "DELETE FROM " + TABLE_LECTURE;
        db.rawQuery(delete, null);
        db.close();
    }

    public int updateNote(NoteItem note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_TITLE, note.getTitle());
        values.put(KEY_NOTE_CONTENT, note.getContent());
        values.put(KEY_NOTE_DONE, note.isDone());
        values.put(KEY_NOTE_DATETIME, System.currentTimeMillis());


        Log.i("UPDATE", "Note data updated!");
        return db.update(TABLE_NOTE, values, KEY_NOTE_ID + " = ?",
                new String[] { String.valueOf(note.getID()) });
    }

    public void addStaffCategory(StaffCategory staffCategory) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STAFF_CATEGORY_ID, staffCategory.getID());
        values.put(KEY_STAFF_CATEGORY_NAME, staffCategory.getName());

        db.insert(TABLE_STAFF_CATEGORY, null, values);

        db.close();
    }

    public void addStaff(Staff staff) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STAFF_EMAIL, staff.getEmail());
        values.put(KEY_STAFF_CATEGORY_ID, staff.getCategoryID());
        values.put(KEY_STAFF_LEVEL, staff.getLevel());
        values.put(KEY_STAFF_FIRST_NAME, staff.getFirstName());
        values.put(KEY_STAFF_LAST_NAME, staff.getLastName());
        values.put(KEY_STAFF_IMAGE_URL, staff.getImageUrl());
        values.put(KEY_STAFF_RESUME, staff.getResume());

        db.insert(TABLE_STAFF, null, values);

        db.close();
    }

    public void addClassroom(Classroom classroom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CLASSROOM_ID, classroom.getID());
        values.put(KEY_CLASSROOM_NAME, classroom.getName());
        values.put(KEY_CLASSROOM_DESCRIPTIION, classroom.getDescription());

        db.insert(TABLE_CLASSROOM, null, values);

        db.close();
    }

    public void addSubjectType(SubjectType subjectType) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBJECT_TYPE_ID, subjectType.getID());
        values.put(KEY_SUBJECT_TYPE_NAME, subjectType.getName());

        db.insert(TABLE_SUBJECT_TYPE, null, values);

        db.close();
    }

    public void addSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBJECT_ID, subject.getID());
        values.put(KEY_SUBJECT_NAME, subject.getName());
        values.put(KEY_SUBJECT_SEMESTER, subject.getSemester());
        values.put(KEY_SUBJECT_COMPULSORY, subject.isCompulsory());
        values.put(KEY_SUBJECT_DESCRIPTION, subject.getDescription());
        values.put(KEY_SUBJECT_CREDITS, subject.getCredits());
        values.put(KEY_SUBJECT_CLASSES, subject.getClasses());

        db.insert(TABLE_SUBJECT, null, values);

        db.close();
    }

    public void addMySubject(String subjectID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MY_SUBJECTS_ID, subjectID);

        db.insert(TABLE_MY_SUBJECT, null, values);

        Log.i("MY SUBJECT ADDED", subjectID);

        db.close();
    }

    public void addLecture(Lecture lecture) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LECTURE_ID, lecture.getID());
        values.put(KEY_LECTURE_STAFF_EMAIL, lecture.getStaffID());
        values.put(KEY_LECTURE_SUBJECT_ID, lecture.getSubjectID());
        values.put(KEY_LECTURE_CLASSROOM_ID, lecture.getClassroomID());
        values.put(KEY_LECTURE_SUBJECT_TYPE_ID, lecture.getSubjectTypeID());
        values.put(KEY_LECTURE_DATETIME_FROM, lecture.getDateFrom());
        values.put(KEY_LECTURE_DATETIME_TO, lecture.getDateTo());
        values.put(KEY_LECTURE_DAY_IN_WEEK, lecture.getDayInWeek());

        db.insert(TABLE_LECTURE, null, values);

        db.close();
    }

    public void addConsultation(Consultation consultation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONSULTATION_ID, consultation.getID());
        values.put(KEY_CONSULTATION_STAFF_EMAIL, consultation.getStaffID());
        values.put(KEY_CONSULTATION_ROOM, consultation.getRoomID());
        values.put(KEY_CONSULTATION_DATETIME_FROM, consultation.getDateFrom());
        values.put(KEY_CONSULTATION_DATETIME_TO, consultation.getDateTo());
        values.put(KEY_CONSULTATION_DAY_IN_WEEK, consultation.getDayInWeek());

        db.insert(TABLE_CONSULTATION, null, values);

        db.close();
    }

    public void addNews(News news) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NEWS_ID, news.getID());
        values.put(KEY_NEWS_TITLE, news.getTitle());
        values.put(KEY_NEWS_CONTENT, news.getContent());
        values.put(KEY_NEWS_DATETIME, news.getDate());

        db.insert(TABLE_NEWS, null, values);

        db.close();
    }

    public Staff getStaff(String ID) {
        Staff staff = null;

        String selectQuery = "SELECT * FROM " + TABLE_STAFF + " WHERE " + KEY_STAFF_EMAIL + "='" + ID + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                staff = new Staff(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            } while (cursor.moveToNext());
        }

        return staff;
    }

    public ArrayList<Staff> getAllStaff() {
        ArrayList<Staff> staffList = new ArrayList<Staff>();

        String selectQuery = "SELECT * FROM " + TABLE_STAFF + " ORDER BY " + KEY_STAFF_CATEGORY_ID + "," + KEY_STAFF_LAST_NAME + "," + KEY_STAFF_FIRST_NAME + " DESC;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                staffList.add(new Staff(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        return staffList;
    }

    public ArrayList<Staff> getStaffByCategory(String categoryID) {
        ArrayList<Staff> staffList = new ArrayList<Staff>();

        String selectQuery = "SELECT S.* FROM "
                + TABLE_STAFF + " S,"
                + TABLE_STAFF_CATEGORY + " SC"
                + " WHERE S." + KEY_STAFF_CATEGORY_ID + "=SC." + KEY_STAFF_CATEGORY_ID + " AND SC." + KEY_STAFF_CATEGORY_ID + "=" + categoryID
                + " ORDER BY " + KEY_STAFF_LAST_NAME + "," + KEY_STAFF_FIRST_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                staffList.add(new Staff(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        return staffList;
    }



    public Subject getSubject(String ID) {
        Subject subject = null;

        String selectQuery = "SELECT  * FROM " + TABLE_SUBJECT + " WHERE " + KEY_SUBJECT_ID + "=" + ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                subject = new Subject(cursor.getString(0), cursor.getString(1), cursor.getInt(2), (cursor.getInt(3)==1), cursor.getString(4), cursor.getInt(5), cursor.getString(6));
            } while (cursor.moveToNext());
        }

        return subject;
    }

    public ArrayList<Subject> getAllSubjects(boolean compulsory) {
        ArrayList<Subject> subjectList = new ArrayList<Subject>();

        int c;
        if(compulsory){
            c = 1;
        } else {
            c = 0;
        }

        String selectQuery = "SELECT * FROM " + TABLE_SUBJECT + " WHERE " + KEY_SUBJECT_COMPULSORY + "=" + c + " ORDER BY " + KEY_SUBJECT_SEMESTER + "," + KEY_SUBJECT_NAME + " ASC;";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                subjectList.add(new Subject(cursor.getString(0), cursor.getString(1), cursor.getInt(2), (cursor.getInt(3)==1), cursor.getString(4), cursor.getInt(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        Log.i("ALL MY SUBJECTS", subjectList.toString());
        return subjectList;
    }

    public ArrayList<Subject> getMySubjects() {
        ArrayList<Subject> subjectList = new ArrayList<Subject>();

        String selectQuery = "SELECT  S.* FROM " + TABLE_SUBJECT + " S, " + TABLE_MY_SUBJECT + " MS WHERE S." + KEY_SUBJECT_ID + "=MS." + KEY_MY_SUBJECTS_ID + " ORDER BY " + KEY_SUBJECT_SEMESTER + "," + KEY_SUBJECT_NAME + " ASC;";

//        String selectQuery = "SELECT  S." + KEY_SUBJECT_ID + ", S." + KEY_SUBJECT_NAME + ", S." + KEY_SUBJECT_SEMESTER + ", S." + KEY_SUBJECT_COMPULSORY + ", S." + KEY_SUBJECT_DESCRIPTION
//                + ", S." + KEY_SUBJECT_CREDITS + ", S." + KEY_SUBJECT_CLASSES
//                + " FROM " + TABLE_SUBJECT + " S, " + TABLE_MY_SUBJECT + " MS WHERE S." + KEY_SUBJECT_ID + "=MS." + KEY_MY_SUBJECTS_ID + " ORDER BY " + KEY_SUBJECT_SEMESTER + "," + KEY_SUBJECT_NAME + " ASC;";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                subjectList.add(new Subject(cursor.getString(0), cursor.getString(1), cursor.getInt(2), (cursor.getInt(3)==1), cursor.getString(4), cursor.getInt(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        }

        Log.i("MY SUBJECTS", subjectList.toString());

        return subjectList;
    }

    public void deleteSubject(Subject subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SUBJECT, KEY_SUBJECT_ID + " = ?", new String[] { String.valueOf(subject.getID()) });
        db.close();
    }

    public void deleteMySubject(String subjectID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MY_SUBJECT, KEY_MY_SUBJECTS_ID + " = ?", new String[] { subjectID });
        db.close();
    }

    public boolean isAlreadyAdded(String subjectID) {

        String selectQuery = "SELECT  * FROM " + TABLE_MY_SUBJECT + " WHERE " + KEY_MY_SUBJECTS_ID + "=" + subjectID + "";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        String subject = null;

        if (cursor.moveToFirst()) {
            do {
                subject = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        return subject!=null;
    }


    public ArrayList<Lecture> getTodaysLectures(int TODAY) {
        ArrayList<Lecture> lectureList = new ArrayList<Lecture>();

        String selectQuery = "SELECT L." + KEY_LECTURE_ID + ", SU." + KEY_SUBJECT_NAME + ", SUT." + KEY_SUBJECT_TYPE_NAME + ", ST." + KEY_STAFF_IMAGE_URL
                + ", ST." + KEY_STAFF_LAST_NAME + ", ST." + KEY_STAFF_FIRST_NAME + ", C." + KEY_CLASSROOM_NAME + ", C." + KEY_CLASSROOM_DESCRIPTIION
                + ", L." + KEY_LECTURE_DATETIME_FROM + ", L." + KEY_LECTURE_DATETIME_TO
                + " FROM "
                + TABLE_LECTURE + " L,"
                + TABLE_STAFF + " ST,"
                + TABLE_SUBJECT + " SU,"
                + TABLE_SUBJECT_TYPE + " SUT,"
                + TABLE_MY_SUBJECT + " MSU,"
                + TABLE_CLASSROOM + " C"
                + " WHERE L." + KEY_LECTURE_STAFF_EMAIL + "=ST." + KEY_STAFF_EMAIL + " AND L."
                + KEY_LECTURE_SUBJECT_ID + "=MSU." + KEY_MY_SUBJECTS_ID + " AND L."
                + KEY_LECTURE_SUBJECT_ID + "=SU." + KEY_SUBJECT_ID + " AND L."
                + KEY_LECTURE_SUBJECT_TYPE_ID + "=SUT." + KEY_SUBJECT_TYPE_ID + " AND L."
                + KEY_LECTURE_CLASSROOM_ID + "=C." + KEY_CLASSROOM_ID + " AND L."
                + KEY_LECTURE_DAY_IN_WEEK + "=" + TODAY
                + " ORDER BY L." + KEY_LECTURE_DATETIME_FROM;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                lectureList.add(new Lecture(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9)));
            } while (cursor.moveToNext());
        }

        Log.i("TODAY", lectureList.toString());

        return lectureList;
    }

    public ArrayList<Lecture> getLectures() {
        ArrayList<Lecture> lectureList = new ArrayList<Lecture>();

        String selectQuery = "SELECT * FROM " + TABLE_LECTURE
                + " ORDER BY " + KEY_LECTURE_DAY_IN_WEEK;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                lectureList.add(new Lecture(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7)));
            } while (cursor.moveToNext());
        }

        return lectureList;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA automatic_index = off;");
        }
    }

    public ArrayList<Consultation> getTodaysConsultations() {
        ArrayList<Consultation> consultationList = new ArrayList<Consultation>();

        String selectQuery = "SELECT DISTINCT C." + KEY_CONSULTATION_ID + ", S." + KEY_STAFF_IMAGE_URL
                + ", S." + KEY_STAFF_FIRST_NAME + ", S." + KEY_STAFF_LAST_NAME + ", R." + KEY_CLASSROOM_NAME + ", R." + KEY_CLASSROOM_DESCRIPTIION
                + ", C." + KEY_CONSULTATION_DATETIME_FROM + ", C." + KEY_CONSULTATION_DATETIME_TO + ", C." + KEY_CONSULTATION_STAFF_EMAIL
                + " FROM "
                + TABLE_CONSULTATION + " C,"
                + TABLE_STAFF + " S,"
                + TABLE_CLASSROOM + " R,"
                + TABLE_LECTURE + " L,"
                + TABLE_MY_SUBJECT + " MS"
                + " WHERE C." + KEY_CONSULTATION_STAFF_EMAIL + "=S." + KEY_STAFF_EMAIL + " AND C."
                + KEY_CONSULTATION_ROOM + "=R." + KEY_CLASSROOM_ID + " AND L."
                + KEY_LECTURE_STAFF_EMAIL + "=S." + KEY_STAFF_EMAIL + " AND L."
                + KEY_LECTURE_SUBJECT_ID + "=MS." + KEY_MY_SUBJECTS_ID
                + " ORDER BY C." + KEY_CONSULTATION_DATETIME_FROM;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                consultationList.add(new Consultation(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)));
            } while (cursor.moveToNext());
        }

        Log.i("TODAYCON", consultationList.toString());


        return consultationList;
    }

    public ArrayList<Consultation> getTodaysConsultations(int TODAY) {
        ArrayList<Consultation> consultationList = new ArrayList<Consultation>();

        String selectQuery = "SELECT DISTINCT C." + KEY_CONSULTATION_ID + ", S." + KEY_STAFF_IMAGE_URL
                + ", S." + KEY_STAFF_FIRST_NAME + ", S." + KEY_STAFF_LAST_NAME + ", R." + KEY_CLASSROOM_NAME + ", R." + KEY_CLASSROOM_DESCRIPTIION
                + ", C." + KEY_CONSULTATION_DATETIME_FROM + ", C." + KEY_CONSULTATION_DATETIME_TO + ", C." + KEY_CONSULTATION_STAFF_EMAIL
                + " FROM "
                + TABLE_CONSULTATION + " C,"
                + TABLE_STAFF + " S,"
                + TABLE_CLASSROOM + " R,"
                + TABLE_LECTURE + " L,"
                + TABLE_MY_SUBJECT + " MS"
                + " WHERE C." + KEY_CONSULTATION_STAFF_EMAIL + "=S." + KEY_STAFF_EMAIL + " AND C."
                + KEY_CONSULTATION_ROOM + "=R." + KEY_CLASSROOM_ID + " AND L."
                + KEY_LECTURE_STAFF_EMAIL + "=S." + KEY_STAFF_EMAIL + " AND L."
                + KEY_LECTURE_SUBJECT_ID + "=MS." + KEY_MY_SUBJECTS_ID + " AND C."
                + KEY_CONSULTATION_DAY_IN_WEEK + "=" + TODAY
                + " ORDER BY C." + KEY_CONSULTATION_DATETIME_FROM;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                consultationList.add(new Consultation(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8)));
            } while (cursor.moveToNext());
        }

        Log.i("TODAYCON", consultationList.toString());


        return consultationList;
    }

    public ArrayList<String> getSubjectsCons(String email){
        ArrayList<String> subjectsList = new ArrayList<>();

        String selectQuery = "SELECT DISTINCT S." + KEY_SUBJECT_NAME
                + " FROM "
                + TABLE_CONSULTATION + " C,"
                + TABLE_SUBJECT + " S,"
                + TABLE_MY_SUBJECT + " MS,"
                + TABLE_LECTURE + " L"
                + " WHERE L." + KEY_LECTURE_STAFF_EMAIL + "=C." + KEY_CONSULTATION_STAFF_EMAIL + " AND L."
                + KEY_LECTURE_SUBJECT_ID + "=MS." + KEY_MY_SUBJECTS_ID + " AND MS."
                + KEY_MY_SUBJECTS_ID + "=S." + KEY_SUBJECT_ID + " AND C."
                + KEY_CONSULTATION_STAFF_EMAIL + "='" + email + "'";


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                subjectsList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        Log.i("SUBCONS", subjectsList.toString());

        return subjectsList;
    }

//    KEY_CONSULTATION_ID + " TEXT PRIMARY KEY,"
//            + KEY_CONSULTATION_STAFF_ID + " TEXT,"
//            + KEY_CONSULTATION_ROOM + " TEXT,"
//            + KEY_CONSULTATION_DATETIME_FROM + " DATETIME,"
//            + KEY_CONSULTATION_DATETIME_TO + " DATETIME,"
//            + KEY_CONSULTATION_DAY_IN_WEEK + " INTEGER"


}
