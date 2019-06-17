/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.model;

import fr.theia_land.in_situ.dataportal.mdl.POJO.lite.DatasetLite;
import fr.theia_land.in_situ.dataportal.mdl.POJO.lite.ObservationLite;
import fr.theia_land.in_situ.dataportal.mdl.POJO.lite.ProducerLite;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity class to be mapped with MongoDB DOcument object
 * @author coussotc
 */
@Document(collection = "observationsLite")
public class ObservationDocumentLite {

    private List<String> documentIds = new ArrayList<>();
    private ProducerLite producer;
    private DatasetLite dataset;
    private ObservationLite observation;

    public List<String> getDocumentIds() {
        return documentIds;
    }

    public ProducerLite getProducer() {
        return producer;
    }

    public DatasetLite getDataset() {
        return dataset;
    }

    public ObservationLite getObservation() {
        return observation;
    }

    public void setDataset(DatasetLite dataset) {
        this.dataset = dataset;
    }

    public void setDocumentIds(List<String> documentIds) {
        this.documentIds = documentIds;
    }

    public void setObservation(ObservationLite observation) {
        this.observation = observation;
    }

    public void setProducer(ProducerLite producer) {
        this.producer = producer;
    }
}
