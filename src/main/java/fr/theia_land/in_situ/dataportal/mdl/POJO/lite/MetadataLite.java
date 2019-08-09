/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.lite;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.SpatialExtent;
import java.util.List;

/**
 *
 * @author coussotc
 */
public class MetadataLite {
    /**
     * Title of the dataset. It should be a unique name that can reference the geographical area covered.
     */
    private List<I18n> title;
    /**
     * Short description, abstract, description of the dataset. 
     */
//    private List<I18nWeight1> description;
    private List<I18n> description;
    private SpatialExtent spatialExtent;

    public SpatialExtent getSpatialExtent() {
        return spatialExtent;
    }
    public List<I18n> getTitle() {
        return title;
    }
//    public List<I18nWeight1> getDescription() {
//        return description;
//    }

    public List<I18n> getDescription() {
        return description;
    }
}
