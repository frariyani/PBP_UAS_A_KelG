package com.calvindo.aldi.sutanto.tubes.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.calvindo.aldi.sutanto.tubes.models.Favorites;

import java.util.List;

@Dao
public interface FavDAO {

    @Query("SELECT * FROM favorites WHERE uid LIKE :uid")
    List<Favorites> getAll(String uid);

    @Query("SELECT * FROM favorites WHERE idkost LIKE :id AND  uid LIKE :uid")
    Favorites fav(int id, String uid);

    @Insert
    void insert(Favorites favorites);

    @Update
    void update(Favorites favorites);

    @Delete
    void delete(Favorites favorites);
}