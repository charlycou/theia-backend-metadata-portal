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
public class MultiPolygon extends GeometryGeoJSON {

    private List<List<List<List<Number>>>> coordinates;
    private final String type = "MultiPolygon";

    public MultiPolygon(List<List<List<List<Number>>>> coordinates) {
        ListIterator<List<List<List<Number>>>> listIterator = coordinates.listIterator();
        while (listIterator.hasNext()) {
            ListIterator<List<List<Number>>> listIterator1 = listIterator.next().listIterator();
            while (listIterator1.hasNext()) {
                ListIterator<List<Number>> listIterator2 = listIterator1.next().listIterator();
                while (listIterator2.hasNext()) {
                    ListIterator<Number> listIterator3 = listIterator2.next().listIterator();
                    while (listIterator3.hasNext()) {
                        Double d = listIterator3.next().doubleValue();
                        listIterator3.set(d);
                    }
                }
            }
        }
        this.coordinates = coordinates;

    }

    public List<List<List<List<Number>>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<List<List<List<Number>>>> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

}
