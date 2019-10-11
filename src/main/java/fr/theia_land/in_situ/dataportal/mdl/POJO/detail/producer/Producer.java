/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.producer;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.Contact;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.OnlineResource;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a provider of datasets and all the metadata associated to this provider
 *
 * @author coussotc
 */
public class Producer {

    /**
     * ID of the Producer as defined by Theia/OZCAR comity
     */
    private String producerId;
    /**
     * Acronym of the Producer. ex AMMA-CATCH
     */
    private List<I18n> name;

    /**
     * Title of the Producer
     */
    private List<I18n> title;
    /**
     * Short description of the Producer
     */
    private List<I18n> description;
    
    /**
     * Short description of the objectives of the producer
     */
    private List<I18n> objectives;
    /**
     * Short descruiption of the variables measured by the producer
     */
    private List<I18n> measuredVariables;

    
    /**
     * Mail adresse for the Producer
     */
    private String email;
    /**
     * List of funders for the Producer
     */
    private List<Funding> fundings = new ArrayList<>();
    /**
     * List of Contact - project leader and Data manager
     */
    private List<Contact> contacts = new ArrayList<>();

    /**
     * Online resources of the producer
     */
    private OnlineResource onlineResource;

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public List<I18n> getName() {
        return name;
    }

    public void setName(List<I18n> name) {
        this.name = name;
    }

    public List<I18n> getTitle() {
        return title;
    }

    public void setTitle(List<I18n> title) {
        this.title = title;
    }

    public List<I18n> getDescription() {
        return description;
    }

    public void setDescription(List<I18n> description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Funding> getFundings() {
        return fundings;
    }

    public void setFundings(List<Funding> fundings) {
        this.fundings = fundings;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public OnlineResource getOnlineResource() {
        return onlineResource;
    }

    public void setOnlineResource(OnlineResource onlineResource) {
        this.onlineResource = onlineResource;
    }

    public List<I18n> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<I18n> objectives) {
        this.objectives = objectives;
    }

    public List<I18n> getMeasuredVariables() {
        return measuredVariables;
    }

    public void setMeasuredVariables(List<I18n> measuredVariables) {
        this.measuredVariables = measuredVariables;
    }
    
    

//    @Override
//    public String toString() {
//        String tmp = "{ " + this.name.toString() + ", " + this.description.toString() + ", " + this.title.toString();
//        for (Contact c : contacts) {
//            tmp = tmp + ", " + c.toString();
//        }
//        tmp = tmp + " }";
//        return tmp;
//    }

}
