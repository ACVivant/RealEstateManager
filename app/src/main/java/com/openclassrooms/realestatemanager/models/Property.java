package com.openclassrooms.realestatemanager.models;

import java.util.Date;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Entity(tableName = "property",
        foreignKeys = {
        @ForeignKey(entity = TypeOfProperty.class,
        parentColumns = "typeId",
        childColumns = "typeId"),
        @ForeignKey(entity = Status.class,
        parentColumns = "statusId",
        childColumns = "statusId"),
        @ForeignKey(entity = Address.class,
        parentColumns = "addressId",
        childColumns = "addressId"),
        @ForeignKey(entity = Agent.class,
        parentColumns = "agentId",
        childColumns = "agentId")
})
public class Property {
    @PrimaryKey(autoGenerate = true)
    private long propertyId;
    private double price;
    private  int rooms;
    private int bedrooms;
    private int bathroom;
    private String description;
    private int upForSaleDate;
    private int soldOnDate;
    private int surface;

    @Embedded
    private TypeOfProperty typeId;
    @Embedded
    public Address addressId;
    @Embedded
    private Agent agentId;
    @Embedded
    private Status statusId;

    public Property(double price, int rooms, int bedrooms, int bathroom, String description, int upForSaleDate, int soldOnDate, int surface, TypeOfProperty typeId, Address addressId, Agent agentId, Status statusId) {
        this.price = price;
        this.rooms = rooms;
        this.bedrooms = bedrooms;
        this.bathroom = bathroom;
        this.description = description;
        this.upForSaleDate = upForSaleDate;
        this.soldOnDate = soldOnDate;
        this.surface = surface;
        this.typeId = typeId;
        this.addressId = addressId;
        this.agentId = agentId;
        this.statusId = statusId;
    }

    // --- GETTER ---
    public long getPropertyId() {
        return propertyId;
    }
    public double getPrice() {
        return price;
    }
    public int getRooms() {
        return rooms;
    }
    public int getBedrooms() {
        return bedrooms;
    }
    public int getBathroom() {
        return bathroom;
    }
    public String getDescription() {
        return description;
    }
    public int getUpForSaleDate() {
        return upForSaleDate;
    }
    public int getSoldOnDate() {
        return soldOnDate;
    }
    public int getSurface() {
        return surface;
    }
    public TypeOfProperty getTypeId() {
        return typeId;
    }
    public Address getAddressId() {
        return addressId;
    }
    public Agent getAgentId() {
        return agentId;
    }
    public Status getStatusId() {
        return statusId;
    }

    // --- SETTER ---
    public void setPropertyId(long id) {
        this.propertyId = id;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setRooms(int rooms) {
        this.rooms = rooms;
    }
    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }
    public void setBathroom(int bathroom) {
        this.bathroom = bathroom;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setUpForSaleDate(int upForSaleDate) {
        this.upForSaleDate = upForSaleDate;
    }
    public void setSoldOnDate(int soldOnDate) {
        this.soldOnDate = soldOnDate;
    }
    public void setSurface(int surface) {
        this.surface = surface;
    }
    public void setTypeId(TypeOfProperty type) {
        this.typeId = type;
    }
    public void setAddressId(Address address) {
        this.addressId = address;
    }
    public void setAgentId(Agent agent) {
        this.agentId = agent;
    }
    public void setStatusId(Status status) {
        this.statusId = status;
    }

}
