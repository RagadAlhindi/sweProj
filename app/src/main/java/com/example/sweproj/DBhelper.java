package com.example.sweproj;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

    public class DBhelper extends SQLiteOpenHelper {
        public static final String VEHICLE_TABLE = "Vehicle_Table";
        public static final String COLUMN_VEHICLE_PLATE = "VEHICLE_PLATE";
        public static final String COLUMN_VEHICLE_MODEL = "VEHICLE_MODEL";

        public static final String COLUMN_VEHICLE_YEAR = "VEHICLE_YEAR";

        public static final String COLUMN_VEHICLE_TYPE = "VEHICLE_TYPE";

        public static final String COLUMN_VEHICLE_LOCATION= "VEHICLE_LOCATION";

        public static final String COLUMN_VEHICLE_DESCRIPTION= "VEHICLE_DESCRIPTION";

        public static final String COLUMN_VEHICLE_RENT= "VEHICLE_RENT";
        public static final String COLUMN_ID = "ID";

        public static final String COLUMN_img = "VEHICLE_IMAGE";

        public DBhelper(@Nullable Context context) {
            super(context, "vehicleDATABASE.db", null, 1);
        }


        // when creating the database
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String createTableStatement = "Create TABLE " + VEHICLE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_VEHICLE_PLATE + " TEXT, " + COLUMN_VEHICLE_MODEL + " TEXT, " + COLUMN_VEHICLE_YEAR + " INT, "+ COLUMN_VEHICLE_TYPE +" TEXT, " + COLUMN_VEHICLE_LOCATION + " TEXT, " + COLUMN_VEHICLE_DESCRIPTION + " TEXT, "  + COLUMN_VEHICLE_RENT + " INT, " + COLUMN_img + " BLOB)";
            sqLiteDatabase.execSQL(createTableStatement);
        }
        // when upgrading
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        public boolean addOne(VehicleModel VM){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_VEHICLE_PLATE, VM.getPlateNo());
            cv.put(COLUMN_VEHICLE_MODEL, VM.getModel());
            cv.put(COLUMN_VEHICLE_YEAR, VM.getYear());
            cv.put(COLUMN_VEHICLE_TYPE, VM.getType());
            cv.put(COLUMN_VEHICLE_LOCATION, VM.getCity());
            cv.put(COLUMN_VEHICLE_DESCRIPTION, VM.getDescription());
            cv.put(COLUMN_VEHICLE_RENT, VM.getRent());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            VM.getImg().compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imageBytes = stream.toByteArray();
            cv.put(COLUMN_img, imageBytes);

            long insert = db.insert(VEHICLE_TABLE, null, cv);



            db.close();
            if(insert == -1){
                return false;
            }
            else {
                return true;
            }
        }

        public boolean DeleteOne(VehicleModel VM){
            SQLiteDatabase db = this.getWritableDatabase();
            String queryString= "Delete From " + VEHICLE_TABLE + " WHERE " + COLUMN_ID + " = " + VM.getId() ;
            Cursor cursor = db.rawQuery(queryString, null);
            if(cursor.moveToFirst()){
                return true;
            } else{
                // nothing happens. no one is added.
                return false;
            }
            //close
        }
        public List<VehicleModel> getEveryone(){
            List<VehicleModel> returnList = new ArrayList<>();
            // get data from database
            String queryString = "Select * from "+ VEHICLE_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);
            if(cursor.moveToFirst()){
                // loop through cursor results
                do{
                    int SID = cursor.getInt(0); // vehicle ID
                    String VPlate = cursor.getString(1);
                    String VModel = cursor.getString(1);
                    int VYear = cursor.getInt(2);
                    String VType = cursor.getString(1);
                    String VLoc = cursor.getString(1);
                    String VDescription = cursor.getString(1);
                    int VRent = cursor.getInt(2);


                   // VehicleModel newVehicle = new VehicleModel(SID, VPlate, VModel, VYear, VType, VLoc, VDescription,VRent);
                   // returnList.add(newVehicle);
                }while (cursor.moveToNext());
            } else{
                // nothing happens. no one is added.
            }
            //close
            cursor.close();
            db.close();
            return returnList;
        }
        public Cursor getdata ()
        {
            SQLiteDatabase DB = this.getWritableDatabase();
            Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
            return cursor;
        }



}

