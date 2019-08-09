/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.DAO;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.SpatialExtent;

/**
 * Interface for custom method definition
 */
public interface CustomObservationDocumentRepository {

    SpatialExtent findDatasetSpatialExtent(String datasetId);
    
}