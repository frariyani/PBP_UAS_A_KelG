package com.calvindo.aldi.sutanto.tubes.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.calvindo.aldi.sutanto.tubes.models.Transactions;

import java.util.List;

@Dao
public interface TransactionsDAO {
    @Query("SELECT * FROM transactions WHERE uid like :uid")
    List<Transactions> getAll(String uid);

    @Query("SELECT * FROM transactions WHERE idkost LIKE :id AND uid LIKE :uid")
    Transactions transactions(int id, String uid);

    @Query("SELECT * FROM transactions WHERE id LIKE :id")
    Transactions findtransbyid(String id);

    @Insert
    void insert(Transactions transactions);

    @Update
    void update(Transactions transactions);

    @Delete
    void delete(Transactions transactions);
}
