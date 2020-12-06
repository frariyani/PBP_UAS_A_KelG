package com.calvindo.aldi.sutanto.tubes.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.calvindo.aldi.sutanto.tubes.models.Favorites;
import com.calvindo.aldi.sutanto.tubes.models.Kost;
import com.calvindo.aldi.sutanto.tubes.models.Transactions;

@Database(entities = {Favorites.class, Kost.class, Transactions.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavDAO favDAO();
    public abstract KostDAO kostDAO();
    public abstract TransactionsDAO transDAO();
}