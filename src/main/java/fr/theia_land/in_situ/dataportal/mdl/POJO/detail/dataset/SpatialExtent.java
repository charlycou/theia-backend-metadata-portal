/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset;

import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.GeometryGeoJSON;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.MultiPolygon;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.Polygon;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.Properties;



/**
 * Class representing the spatial extent of a dataset. This spatial extent is either a 
 * bounding box or several bounding boxes
 * @author coussotc
 */
public class SpatialExtent {
    /**
     * The geometry of the spatial extent. It can be either a Polygon Object 
     * or a MultiPolygon object
     * @see GeometryGeoJSON
     * @see Polygon
     * @see MultiPolygon
     */
    private GeometryGeoJSON geometry;
    /**
     * a GeoJSON mandatoruy field
     */
    private Properties properties;
    
    /**
     * a GeoJSON mandatoruy field
     */
    private String type;

    public GeometryGeoJSON getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryGeoJSON geometry) {
        this.geometry = geometry;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    

  
}
