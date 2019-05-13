package com.openclassrooms.realestatemanager.models;

import java.util.Date;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = TypeOfProperty.class,
        parentColumns = "typeId",
        childColumns = "type"),
        @ForeignKey(entity = Status.class,
        parentColumns = "statusId",
        childColumns = "status"),
        @ForeignKey(entity = Address.class,
        parentColumns = "addressId",
        childColumns = "address"),
        @ForeignKey(entity = Agent.class,
        parentColumns = "agentId",
        childColumns = "agent")
})
public class Property {
    @PrimaryKey(autoGenerate = true)
    private long propertyId;
    private double price;
    private  int rooms;
    private int bedrooms;
    private int bathroom;
    private String description;
    private Date upForSaleDate;
    private Date soldOnDate;
    private int surface;

    @Embedded
    private TypeOfProperty type;
    @Embedded
    public Address address;
    @Embedded
    private Agent agent;
    @Embedded
    private Status status;

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
    public Date getUpForSaleDate() {
        return upForSaleDate;
    }
    public Date getSoldOnDate() {
        return soldOnDate;
    }
    public int getSurface() {
        return surface;
    }
    public TypeOfProperty getType() {
        return type;
    }
    public Address getAddress() {
        return address;
    }
    public Agent getAgent() {
        return agent;
    }
    public Status getStatus() {
        return status;
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
    public void setUpForSaleDate(Date upForSaleDate) {
        this.upForSaleDate = upForSaleDate;
    }
    public void setSoldOnDate(Date soldOnDate) {
        this.soldOnDate = soldOnDate;
    }
    public void setSurface(int surface) {
        this.surface = surface;
    }
    public void setType(TypeOfProperty type) {
        this.type = type;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setAgent(Agent agent) {
        this.agent = agent;
    }
    public void setStatus(Status status) {
        this.status = status;
    }


}
