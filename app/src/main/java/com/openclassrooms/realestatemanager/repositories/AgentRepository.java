package com.openclassrooms.realestatemanager.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.database.dao.AgentDao;
import com.openclassrooms.realestatemanager.models.Agent;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */
public class AgentRepository {
    private AgentDao agentDao;
   // private LiveData<Agent> agent;

    public AgentRepository(AgentDao agentDao) {this.agentDao = agentDao;}

    public void insertAgent(Agent agent) {agentDao.insertAgent(agent);}

    public void updateAgent(Agent agent) {agentDao.updateAgent(agent);}

    // --- GET ---

    public LiveData<Agent> getAgentFromId(int agentId) { return this.agentDao.getAgentFromId(agentId);}

    public LiveData<Agent> getAgentFromName(String agentName) { return this.agentDao.getAgentFromName(agentName);}

    public LiveData<List<Agent>> getAllAgent() {
        return this.agentDao.getAllAgent();
    }

    /*public AgentRepository(Application application) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(application);
        agentDao = database.agentDao();
    }

    public void insertAgent(Agent agent) {
        new InsertAgentAsyncTask(agentDao).execute(agent);
    }

    public void updateAgent(Agent agent) {
        new UpdateAgentAsyncTask(agentDao).execute(agent);
    }

    public LiveData<Agent> getAgentFromId(int agentId) { return agentDao.getAgentFromId(agentId);}

    public LiveData<Agent> getAgentFromName(String agentName) { return agentDao.getAgentFromName(agentName);}

    public LiveData<List<Agent>> getAllAgent() {return agentDao.getAllAgent();}

    private static class InsertAgentAsyncTask extends AsyncTask<Agent, Void, Void> {
        private AgentDao agentDao;

        private InsertAgentAsyncTask(AgentDao agentDao){
            this.agentDao = agentDao;
        }

        @Override
        protected Void doInBackground(Agent... agents) {
            agentDao.insertAgent(agents[0]);
            return null;
        }
    }

    private static class UpdateAgentAsyncTask extends AsyncTask<Agent, Void, Void>{
        private AgentDao agentDao;

        private UpdateAgentAsyncTask(AgentDao agentDao){
            this.agentDao = agentDao;
        }

        @Override
        protected Void doInBackground(Agent... agents) {
            agentDao.updateAgent(agents[0]);
            return null;
        }
    }
*/
}
