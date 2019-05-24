package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Agent;

import java.util.List;

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
    LiveData<Agent> getAgentFromId(int agentId);

    @Query("SELECT * FROM agent WHERE agentName = :agentName")
    LiveData<Agent> getAgentFromName(String agentName);

    @Query("SELECT * FROM agent ORDER BY agentName ASC")
    LiveData<List<Agent>> getAllAgent();
}
