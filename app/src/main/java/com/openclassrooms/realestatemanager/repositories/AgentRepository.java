package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.AgentDao;
import com.openclassrooms.realestatemanager.models.Agent;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Agent Repository
 */

public class AgentRepository {
    private AgentDao agentDao;

    public AgentRepository(AgentDao agentDao) {this.agentDao = agentDao;}

    public void insertAgent(Agent agent) {agentDao.insertAgent(agent);}

    public void updateAgent(Agent agent) {agentDao.updateAgent(agent);}

    // --- GET ---

    public LiveData<Agent> getAgentFromId(int agentId) { return this.agentDao.getAgentFromId(agentId);}

    public LiveData<Agent> getAgentFromName(String agentName) { return this.agentDao.getAgentFromName(agentName);}

    public LiveData<List<Agent>> getAllAgent() {
        return this.agentDao.getAllAgent();
    }

}
