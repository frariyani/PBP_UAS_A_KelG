package com.calvindo.aldi.sutanto.tubes.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.calvindo.aldi.sutanto.tubes.models.Favorites;
import com.calvindo.aldi.sutanto.tubes.models.Kost;

import java.util.List;

@Dao
public interface KostDAO {

    @Query("SELECT * FROM kost")
    List<Kost> getAll();

    @Query("SELECT * FROM kost WHERE status = 1")
    List<Kost> getAllFav();

    @Query("SELECT * FROM kost WHERE id LIKE :id")
    Kost findkostbyid(int id);

    @Insert
    void insert(Kost kost);

    @Insert
    void insertList(List<Kost> kosts);

    @Update
    void update(Kost kost);

    @Delete
    void delete(Kost kost);

}
