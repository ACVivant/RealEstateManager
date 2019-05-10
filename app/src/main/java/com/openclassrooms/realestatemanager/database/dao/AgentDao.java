package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Agent;

import androidx.room.Insert;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
public interface AgentDao {
    @Insert
    long insertAgent(Agent agent);

    @Update
    int updateAgent(Agent agent);

}
