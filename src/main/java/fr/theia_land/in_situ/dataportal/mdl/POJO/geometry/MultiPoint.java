/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.mdl.POJO.geometry;


import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author coussotc
 */
public class MultiPoint extends GeometryGeoJSON {
    
    private List<List<Number>> coordinates;
    private final String type = "MultiPoint";

    public MultiPoint(List<List<Number>> coordinates) {
        ListIterator<List<Number>> listIterator = coordinates.listIterator();
        while ( listIterator.hasNext()) {
            ListIterator<Number> listIterator1 = listIterator.next().listIterator();
            while(listIterator1.hasNext()) {
                Double d = listIterator1.next().doubleValue();
                listIterator1.set(d);
            }
        }
        this.coordinates = coordinates;
    }

    public List<List<Number>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<Number>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }   

}
