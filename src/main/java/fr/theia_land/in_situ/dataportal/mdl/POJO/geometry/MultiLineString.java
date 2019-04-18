/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.geometry;


import java.util.List;
import java.util.ListIterator;

/**
 * Class representing MultiLineString geometrical object according to GeoJSON Specification
 * @author coussotc
 */
public class MultiLineString extends GeometryGeoJSON {
    /**
     * Ordered list of ordered list of double[2] or double[3] that represent coordinates of 
     * MultiLineString according to GeoJSON specification
     * ArrayList<double[]> is similar to LineString coordinates and must follow the same specifications
     * @see LineString
     * 
     */
    private List<List<List<Number>>> coordinates;
    /**
     * GeoJSON mandatory field
     */
    private final String type = "MultiLineString";

    public MultiLineString(List<List<List<Number>>> coordinates) {
        ListIterator<List<List<Number>>> listIterator = coordinates.listIterator();
        while (listIterator.hasNext()) {
            ListIterator<List<Number>> listIterator1 = listIterator.next().listIterator();
            while (listIterator1.hasNext()) {
                ListIterator<Number> listIterator2 = listIterator1.next().listIterator();
                while(listIterator2.hasNext()) {
                    Double d = listIterator2.next().doubleValue();
                    listIterator2.set(d);
                }                
            }
        }
        this.coordinates = coordinates;
    }

    public List<List<List<Number>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<Number>>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }    
}
