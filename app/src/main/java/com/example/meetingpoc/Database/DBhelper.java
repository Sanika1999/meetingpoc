package com.example.meetingpoc.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.meetingpoc.Models.RecordingItem;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME="savedrecordings.db";
    private static final int DATABASE_VERSION=1;


    public static final String TABLE_NAME="recording_table";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String COLUMN_NAME="name";
    public static final String COLUMN_PATH="path";
    public static final String COLUMN_LENGTH="length";
    public static final String COLUMN_TIMEADDED="time_added";
    public static final String COMA_SEPARATION=",";
    private static final String SQLITE_CREATE_TABLE="CREATE TABLE "+ TABLE_NAME+"("+"id INTEGER PRIMARY KEY"+"AUTOINCREMENT"+COMA_SEPARATION+
            COLUMN_NAME+"TEXT"+COMA_SEPARATION+
            COLUMN_PATH+"TEXT"+COMA_SEPARATION+
            COLUMN_LENGTH+"INTEGER"+COMA_SEPARATION+
            COLUMN_TIMEADDED+"INTEGER"+")";
    public DBhelper(Context context){
      super(context,DATABASE_NAME,null,DATABASE_VERSION);
      this.context=context;
    }


    private static String createRecordingTableQuery() {
        // Database creation SQL statement
        String DATABASE_CREATE = "create table if not exists "
                + TABLE_NAME
                + "("
                + TABLE_COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_NAME + " text not null, "
                + COLUMN_PATH + " text not null,"
                + COLUMN_LENGTH + " integer,"
                + COLUMN_TIMEADDED + " integer"
                + ");";

        return DATABASE_CREATE;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(SQLITE_CREATE_TABLE);
        db.execSQL(createRecordingTableQuery());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public boolean addRecording(RecordingItem recordingItem){
        try{SQLiteDatabase db= getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(COLUMN_NAME,recordingItem.getName());
            contentValues.put(COLUMN_PATH,recordingItem.getPath());
            contentValues.put(COLUMN_LENGTH,recordingItem.getLength());
            contentValues.put(COLUMN_TIMEADDED,recordingItem.getTime_added());
            db.insert(TABLE_NAME,null,contentValues);
            return true;}
        catch (Exception e){
            e.printStackTrace();
            return false;

        }



    }
    public ArrayList<RecordingItem> getAudios()
    {
        ArrayList<RecordingItem> arrayList= new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor= db.rawQuery("select * FROM "+ TABLE_NAME,null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                String name=cursor.getString(1);
                String path=cursor.getString(2);
                int length=(int)(cursor.getLong(3));
                long  timeadded=cursor.getLong(4);
                RecordingItem recordingItem=new RecordingItem(name,path,length,timeadded);
                arrayList.add(recordingItem);



            }
            cursor.close();
            return arrayList;
        }
        else{
            return null;
        }
    }


}
