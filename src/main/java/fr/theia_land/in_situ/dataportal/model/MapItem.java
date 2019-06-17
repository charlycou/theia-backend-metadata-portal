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

    private List<String> documentIds = new ArrayList<>();
    private String producerId;
    private SamplingFeature samplingFeature;

    public List<String> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(List<String> documentIds) {
        this.documentIds = documentIds;
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
