package edu.usfca.cs.echan13;

// Ellen Chan
// CS 514
// Homework 7

import java.util.Date;

public class Entity {
    protected String name;
    protected static int counter = 0;
    protected int entityID;
    protected Date dateCreated;

    public Entity() {
        this.name = "";
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    public Entity(String name) {
        this.name = name;
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public boolean equals(Entity otherEntity) {
        return this.entityID == otherEntity.entityID;
    }

    public String toString() {
        return "Name: " + this.name + ", ID: " + this.entityID;
    }
}
