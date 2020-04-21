/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.model.entity;

import fr.theia_land.in_situ.dataportal.model.POJO.lite.DatasetLite;
import fr.theia_land.in_situ.dataportal.model.POJO.lite.ObservationLite;
import fr.theia_land.in_situ.dataportal.model.POJO.lite.ProducerLite;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Entity class to be mapped with MongoDB DOcument object
 * @author coussotc
 */
@Document(collection = "observationsLite")
public class ObservationDocumentLite {

    private ProducerLite producer;
    private DatasetLite dataset;
    private List<ObservationLite> observations;

    public ProducerLite getProducer() {
        return producer;
    }

    public DatasetLite getDataset() {
        return dataset;
    }

    public List<ObservationLite> getObservations() {
        return observations;
    }

    public void setObservations(List<ObservationLite> observations) {
        this.observations = observations;
    }


    public void setDataset(DatasetLite dataset) {
        this.dataset = dataset;
    }

    public void setProducer(ProducerLite producer) {
        this.producer = producer;
    }
}
