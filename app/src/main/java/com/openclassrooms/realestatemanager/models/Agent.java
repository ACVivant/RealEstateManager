package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Entity(tableName = "agent")
public class Agent {

    @PrimaryKey(autoGenerate = true)
    private int agentId;
    private String agentName;

    public Agent() {}

    public Agent(String name) {
        this.agentName = name;
    }

    // --- GETTER ---
    public int getAgentId() {
        return agentId;
    }
    public String getAgentName() {
        return agentName;
    }

    // --- SETTER ---
    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }
    public void setAgentName(String name) {
        this.agentName = name;
    }


}
