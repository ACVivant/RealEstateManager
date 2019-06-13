package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.nesertplusariennormalement.AttractingPointDao;
import com.openclassrooms.realestatemanager.nesertplusariennormalement.AttractingPoint;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */

public class AttractingPointRepository {
    private AttractingPointDao attractingPointDao;

    public AttractingPointRepository(AttractingPointDao attractingPointDao) {this.attractingPointDao = attractingPointDao;}

    public void insertAttractingPoint(AttractingPoint attractingPoint) {attractingPointDao.insertAttractingPoint(attractingPoint);}

    public void updateAttractingPoint(AttractingPoint attractingPoint) {attractingPointDao.updateAttractingPoint(attractingPoint);}

}