package edu.usfca.cs.echan13;

import java.util.Date;

/**
 *  The Entity class represents an entity.
 *  It stores values for the entity's name, total number
 *  of entities created, the entity's unique ID, and
 *  the date it was created.
 *
 *  @author Ellen Chan
 *
 */
public class Entity {
    protected String name;
    protected static int counter = 0;
    protected int entityID;
    protected Date dateCreated;

    /**
     * Creates a blank Entity object where the ID
     * is the number of Entity objects at creation.
     */
    public Entity() {
        this.name = "";
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    /**
     * Creates an Entity object using the entity's name
     * where the ID is the number of Entity objects at creation.
     *
     * @param name the name of the entity
     */
    public Entity(String name) {
        this.name = name;
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    /**
     * Returns the date the Entity object was created.
     *
     * @return the date of the creation
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * Sets the date the Entity object was created.
     *
     * @param dateCreated the date the entity was created
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Returns the name of the Entity.
     *
     * @return the entity's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Entity.
     *
     * @param name the entity's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the ID of the Entity.
     *
     * @return the entity's ID
     */
    public int getEntityID() {
        return entityID;
    }

    /**
     * Sets the Entity's ID
     *
     * @param entityID
     */
    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    /**
     * Compares this entity to the specified entity.
     *
     * @param otherEntity the other entity to compare to
     * @return true if the entities' IDs are the same, false if not
     */
    public boolean equals(Entity otherEntity) {
        return this.entityID == otherEntity.entityID;
    }

    /**
     * Returns a String object representing the entity.
     *
     * @return a String object describing the entity
     */
    public String toString() {
        return "Name: " + this.name + ", ID: " + this.entityID;
    }
}
