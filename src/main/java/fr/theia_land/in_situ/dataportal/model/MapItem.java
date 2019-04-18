/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.model;

import fr.theia_land.in_situ.dataportal.mdl.POJO.SamplingFeature;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class MapItem {

    private List<List<String>> documentId = new ArrayList<>();
    private String producerId;
    private SamplingFeature samplingFeature;

    public List<List<String>> getDocumentId() {
        return documentId;
    }

    public void setDocumentId(List<List<String>> documentId) {
        this.documentId = documentId;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public SamplingFeature getSamplingFeature() {
        return samplingFeature;
    }

    public void setSamplingFeature(SamplingFeature samplingFeature) {
        this.samplingFeature = samplingFeature;
    }
   
}
