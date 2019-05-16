package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */

@Entity(tableName = "attracting_property_join",
        primaryKeys = { "attractingId", "propertyId" },
        foreignKeys = {
                @ForeignKey(entity = AttractingPoint.class,
                        parentColumns = "attractId",
                        childColumns = "attractingId"),
                @ForeignKey(entity = Property.class,
                        parentColumns = "propertyId",
                        childColumns = "propertyId")
        })
public class AttractingPropertyJoin {
    public int attractingId;
    public int propertyId;

    public AttractingPropertyJoin() {}

    public AttractingPropertyJoin(int attractingId, int propertyId) {
        this.attractingId = attractingId;
        this.propertyId = propertyId;
    }
}

