/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset;


import fr.theia_land.in_situ.dataportal.mdl.POJO.enumerations.EnumDocumentTypes;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.Document;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import java.util.List;

/**
 * Class representing all the metadata associated to a dataset
 * @see Dataset
 * @author coussotc
 */
public class Metadata {
    /**
     * Title of the dataset. It should be a unique name that can reference the geographical area covered.
     */
    private List<I18n> title;
    /**
     * Short description, abstract, description of the dataset. 
     */
    private List<I18n> description;
    /**
     * Description of the scientifique objectives of the dataset
     */
    private List<I18n> objective;
    /**
     * Description of the previous processing that the dataset underwent
     */
    private List<I18n> datasetLineage;
    /**
     * ordered list of contact for the dataset
     * @see Person
     */
    private List<Contact> contacts;
    /**
     * Ordered list of thematic catégories for the dataset. Each element of the list must
 be a value of the EnumTopicCategories enumeration.
     * @see EnumTopicCategories
     */
    private List<String> topicCategories;
    /**
     * Inspire theme for the dataset. It must be a value of the EnumInspireThemes enumeration
     * @see EnumInspireThemes
     */
    private String inspireTheme;
    /**
     * A list of Keyword objects for the dataset.
     * @see Keyword
     */
    private List<Keyword> keywords;
    /**
     * A list of Document objects for the dataset.The list can contain a maxium of 2 elements 
     * since only two types of documents are possible in the EnumDocumentTypes enumeration
     * @see Document
     * @see EnumDocumentTypes
     */
    private List<Document> documents;
    /**
     * A OnlineResource object representing the online resource for the dataset.
     * @see OnlineResource
     */
    private OnlineResource onlineResource;
    /**
     * A DataConstraint objet representing access and use constraint on the dataset.
     * @see DataConstraint
     */
    private DataConstraint dataConstraint;
    /**
     * A PortalSearchCriteria object that represents the some search criteria on the dataset to be used by the portal.
     * @see PortalSearchCriteria
     */
    private PortalSearchCriteria portalSearchCriteria;
    /**
     * The spatialExtent of the dataset. This must be a bounding box or several bouding boxes.
     * @see SpatialExtent
     */
    private SpatialExtent spatialExtent;
    
    private TemporalExtent temporalExtent;

    public TemporalExtent getTemporalExtent() {
        return temporalExtent;
    }

    public void setTemporalExtent(TemporalExtent temporalExtent) {
        this.temporalExtent = temporalExtent;
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

    public List<I18n> getObjective() {
        return objective;
    }

    public void setObjective(List<I18n> objective) {
        this.objective = objective;
    }

    public List<I18n> getDatasetLineage() {
        return datasetLineage;
    }

    public void setDatasetLineage(List<I18n> datasetLineage) {
        this.datasetLineage = datasetLineage;
    }



    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<String> getTopicCategories() {
        return topicCategories;
    }

    public void setTopicCategories(List<String> topicCategories) {
        this.topicCategories = topicCategories;
    }

    public String getInspireTheme() {
        return inspireTheme;
    }

    public void setInspireTheme(String inspireTheme) {
        this.inspireTheme = inspireTheme;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public OnlineResource getOnlineResource() {
        return onlineResource;
    }

    public void setOnlineResource(OnlineResource onlineResource) {
        this.onlineResource = onlineResource;
    }

    public DataConstraint getDataConstraint() {
        return dataConstraint;
    }

    public void setDataConstraint(DataConstraint dataConstraint) {
        this.dataConstraint = dataConstraint;
    }

    public PortalSearchCriteria getPortalSearchCriteria() {
        return portalSearchCriteria;
    }

    public void setPortalSearchCriteria(PortalSearchCriteria portalSearchCriteria) {
        this.portalSearchCriteria = portalSearchCriteria;
    }

    public SpatialExtent getSpatialExtent() {
        return spatialExtent;
    }

    public void setSpatialExtent(SpatialExtent spatialExtent) {
        this.spatialExtent = spatialExtent;
    }
}
