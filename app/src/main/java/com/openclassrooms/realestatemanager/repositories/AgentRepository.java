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
    private LiveData<Agent> agent;

    public AgentRepository(Application application) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(application);
        agentDao = database.agentDao();
    }

    public void insertAgent(Agent agent) {
        new InsertAgentAsyncTask(agentDao).execute(agent);
    }

    public void updateAgent(Agent agent) {
        new UpdateAgentAsyncTask(agentDao).execute(agent);
    }

    public void getAgent(int agentId) { agentDao.getAgentFromId(agentId);}

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

}
