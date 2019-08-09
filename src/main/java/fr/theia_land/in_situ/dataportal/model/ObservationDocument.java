/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.model;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.Dataset;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.Observation;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.producer.Producer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author coussotc
 */
@Document(collection = "observations")
public class ObservationDocument {

    private ObjectId _id;

    private String documentId;
    
    private String version;

    private Producer producer;
    
    private Dataset dataset;
    
    private Observation observation;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }   
}
