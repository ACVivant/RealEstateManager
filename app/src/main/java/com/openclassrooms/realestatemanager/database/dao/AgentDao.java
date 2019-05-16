package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Agent;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Dao
public interface AgentDao {
    @Insert
    void insertAgent(Agent agent);

    @Update
    void updateAgent(Agent agent);

    @Query("SELECT * FROM agent WHERE agentId = :agentId")
    Agent getAgentFromId(int agentId);

}
