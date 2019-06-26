/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.model;

import fr.theia_land.in_situ.dataportal.mdl.POJO.litePopup.ObservationLitePopup;
import fr.theia_land.in_situ.dataportal.mdl.POJO.litePopup.ProducerLitePopup;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class PopupDocument {
    private List<String> documentIds;
    private ProducerLitePopup producer;
    private ObservationLitePopup observation;

    public List<String> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(List<String> documentIds) {
        this.documentIds = documentIds;
    }

    public ProducerLitePopup getProducer() {
        return producer;
    }

    public void setProducer(ProducerLitePopup producer) {
        this.producer = producer;
    }

    public ObservationLitePopup getObservation() {
        return observation;
    }

    public void setObservation(ObservationLitePopup observation) {
        this.observation = observation;
    }
    
    
    
}
