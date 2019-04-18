/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.geometry;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Class representing Polygon geometry according to GeoJSON Specification
 *
 * @author coussotc
 */
public class Polygon extends GeometryGeoJSON {

    /**
     * Ordered list of ordered list of double[2] or double[3] that represent coordinates of Polygon according to GeoJSON
     * specification The ordered list must contain only 1 ArrayList<double[]> object. The ArrayList<double[]> object
     * must contain at least 4 double[]. The first and the last index of the ArrayList<double[]> must be the same object
     */
    private List<List<List<Number>>> coordinates = new ArrayList<>();
    /**
     * GeoJSON mandatory field
     */
    private final String type = "Polygon";

    public Polygon(List<List<List<Number>>> coordinates) {
        ListIterator<List<List<Number>>> listIterator = coordinates.listIterator();
        while (listIterator.hasNext()) {
            ListIterator<List<Number>> listIterator1 = listIterator.next().listIterator();
            while (listIterator1.hasNext()) {
                ListIterator<Number> listIterator2 = listIterator1.next().listIterator();
                while (listIterator2.hasNext()) {
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
