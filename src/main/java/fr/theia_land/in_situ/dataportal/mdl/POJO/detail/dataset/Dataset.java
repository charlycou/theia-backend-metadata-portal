/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset;


/**
 * Class representing a dataset of a provider
 *
 * @author coussotc
 */
public class Dataset {

    /**
     * ID of the dataset. trigramme of the provider plus the number of the dataset separated with underscore ex: AMA_1
     */
    private String datasetId;
    /**
     * Metadata object representing the metadata information of the dataset
     *
     * @see Metadata
     */
    private Metadata metadata;

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    

}
