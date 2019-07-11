package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TypeOfPropertyDao {
    @Insert
    void insertType(TypeOfProperty type);

    @Update
    void updateType(TypeOfProperty type);

    @Query("SELECT * FROM type_of_property WHERE typeId = :typeId")
    LiveData<TypeOfProperty> getTypeFromId(int typeId);

    @Query("SELECT * FROM type_of_property WHERE typeText = :typeName")
    LiveData<TypeOfProperty> getTypeFromName(String typeName);

    @Query("SELECT * FROM type_of_property ORDER BY typeId ASC")
    LiveData<List<TypeOfProperty>> getAllTypes();
}
