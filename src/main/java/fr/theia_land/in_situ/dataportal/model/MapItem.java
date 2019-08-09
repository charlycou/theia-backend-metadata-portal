/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.model;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.SamplingFeature;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author coussotc
 */
@Document(collection = "mapItems")
public class MapItem {

    private List<String> observationIds;
    private String producerId;
    private String datasetId;
    private SamplingFeature samplingFeature;

    public List<String> getObservationIds() {
        return observationIds;
    }

    public void setObservationIds(List<String> observationIds) {
        this.observationIds = observationIds;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }
    

    public SamplingFeature getSamplingFeature() {
        return samplingFeature;
    }

    public void setSamplingFeature(SamplingFeature samplingFeature) {
        this.samplingFeature = samplingFeature;
    }

}
