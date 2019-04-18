/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.enumerations;

/**
 *
 * @author coussotc
 */
public enum EnumGeoJSONTypes {
    FEATURECOLLECTION("FeatureCollection"),
    FEATURE("Feature"),
    POINT("Point"),
    MULTIPOINT("MultiPoint"),
    LINESTRING("LineString"),
    MULTILINESTRING("MultiLineString"),
    POLYGON("Polygon"),
    MULTIPOLYGON("MultiPolygon");
    
    private String enumGeoJSONType;

    private EnumGeoJSONTypes(String enumGeoJSONType) {
        this.enumGeoJSONType = enumGeoJSONType;
    }
    
    @Override
    public String toString() {
        return this.enumGeoJSONType;
    }
    
}
