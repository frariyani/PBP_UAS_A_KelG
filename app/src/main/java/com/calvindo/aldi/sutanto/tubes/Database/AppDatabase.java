package com.calvindo.aldi.sutanto.tubes.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.calvindo.aldi.sutanto.tubes.models.Favorites;

@Database(entities = {Favorites.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavDAO favDAO();

}