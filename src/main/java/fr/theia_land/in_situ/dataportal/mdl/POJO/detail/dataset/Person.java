/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset;


/**
 * Class representing a COntact of a dataset
 * @author coussotc
 */
public class Person extends Contact{
    /**
     * The first name of the Person
     */
    private String firstName;
    /**
     * The last name of the Person
     */
    private String lastName;
    /**
     * The email address of the Person
     */
    private String email;
    /**
     * the role of the person within a dataset
     */
    private String role;
    
    /**
     * The ORC ID of the Person
     */
    private String orcId;
    
    /**
     * the organisation where the Person belongs
     */
    private Organisation organisation;       
    
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOrcId() {
        return orcId;
    }

    public void setOrcId(String orcId) {
        this.orcId = orcId;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
    
    @Override
    public String toString() {
        return this.firstName + this.lastName + this.email ;
    }
}
