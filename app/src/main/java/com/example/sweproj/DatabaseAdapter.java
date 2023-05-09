package com.example.sweproj;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {


        DBhelper helper;
        SQLiteDatabase db;
        List<VehicleModel> VehiclesList = new ArrayList<>();

        public DatabaseAdapter(Context context) {
            helper = new DBhelper(context);
            db = helper.getWritableDatabase();
        }

        public List<VehicleModel> getAllContacts() {
            String columns[] = {DBhelper.COLUMN_ID, DBhelper.COLUMN_VEHICLE_PLATE, DBhelper.COLUMN_VEHICLE_MODEL};
            Cursor cursor = db.query(DBhelper.VEHICLE_TABLE, columns, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int index1 = cursor.getColumnIndex(DBhelper.COLUMN_ID);
                int rowid = cursor.getInt(index1);
                int index2 = cursor.getColumnIndex(DBhelper.COLUMN_VEHICLE_PLATE);
                String plate = cursor.getString(index2);
                int index3 = cursor.getColumnIndex(DBhelper.COLUMN_VEHICLE_MODEL);
                String model = cursor.getString(index3);
                int index4 = cursor.getColumnIndex(DBhelper.COLUMN_VEHICLE_YEAR);
                int year = cursor.getInt(index4);
                int index5 = cursor.getColumnIndex(DBhelper.COLUMN_VEHICLE_TYPE);
                String type = cursor.getString(index5);
                int index6 = cursor.getColumnIndex(DBhelper.COLUMN_VEHICLE_LOCATION);
                String loc = cursor.getString(index6);
                int index7 = cursor.getColumnIndex(DBhelper.COLUMN_VEHICLE_DESCRIPTION);
                String desc = cursor.getString(index7);
                int index8 = cursor.getColumnIndex(DBhelper.COLUMN_VEHICLE_RENT);
                int rent = cursor.getInt(index8);

             //   VehicleModel contact = new VehicleModel(rowid, plate, model, year, type, loc, desc, rent);
                //VehiclesList.add(contact);
            }
            return VehiclesList;
        }

    }

