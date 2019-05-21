package com.openclassrooms.realestatemanager.models;

import java.util.Date;

import androidx.lifecycle.LiveData;
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
    private int propertyId;
    private int price;
    private  int rooms;
    private int bedrooms;
    private int bathroom;
    private String description;
    private int upForSaleDate;
    private int soldOnDate;
    private int surface;
    private String mainPhoto;
    private Boolean shop;
    private Boolean school;
    private Boolean museum;
    private Boolean park;

    @Embedded
    private TypeOfProperty type;
    @Embedded
    private Address address;
    @Embedded
    private Agent agent;
    @Embedded
    private Status status;

 /*   @Embedded
    private Address address;
    private int typeId;
    private int agentId;
    private int statusId;*/

    public Property() {}

    public Property(int price, int rooms, int bedrooms, int bathroom, String description, int upForSaleDate, int soldOnDate, int surface, Boolean shop, Boolean school, Boolean museum, Boolean park ,TypeOfProperty type, Address address, Agent agent, Status status, String mainPhoto) {
        this.price = price;
        this.rooms = rooms;
        this.bedrooms = bedrooms;
        this.bathroom = bathroom;
        this.description = description;
        this.upForSaleDate = upForSaleDate;
        this.soldOnDate = soldOnDate;
        this.surface = surface;
        this.school = school;
        this.shop = shop;
        this.park = park;
        this.museum = museum;
        this.type = type;
        this.address = address;
        this.agent = agent;
        this.status = status;
        this.mainPhoto = mainPhoto;
    }

 /*   public Property(int price, int rooms, int bedrooms, int bathroom, String description, int upForSaleDate, int soldOnDate, int surface, int type, Address address, int agent, int status, String mainPhoto) {
        this.price = price;
        this.rooms = rooms;
        this.bedrooms = bedrooms;
        this.bathroom = bathroom;
        this.description = description;
        this.upForSaleDate = upForSaleDate;
        this.soldOnDate = soldOnDate;
        this.surface = surface;
        this.typeId = type;
        this.address = address;
        this.agentId = agent;
        this.statusId = status;
        this.mainPhoto = mainPhoto;
    }*/

    // --- GETTER ---
    public int getPropertyId() {
        return propertyId;
    }
    public int getPrice() {
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
    public Boolean getShop() {return shop;}
    public Boolean getSchool() {return school;}
    public Boolean getPark() {return park;}
    public Boolean getMuseum() {return museum;}
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

 /*   public int getTypeId() {
        return typeId;
    }
    public Address getAddress() {
        return address;
    }
    public int getAgentId() {
        return agentId;
    }
    public int getStatusId() {
        return statusId;
    }*/

    public String getMainPhoto() { return mainPhoto;}

    // --- SETTER ---
    public void setPropertyId(int id) {
        this.propertyId = id;
    }
    public void setPrice(int price) {
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
    public void setShop(Boolean shop) { this.shop = shop;}
    public void setSchool(Boolean school) { this.school = school;}
    public void setMuseum(Boolean museum) { this.museum = museum;}
    public void setPark(Boolean park) { this.park = park;}
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

 /*   public void setTypeId(int type) {
    this.typeId = type;
}
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setAgentId(int agent) {
        this.agentId = agent;
    }
    public void setStatusId(int status) {
        this.statusId = status;
    }*/

    public void setMainPhoto(String mainPhoto) {this.mainPhoto = mainPhoto;}

}
