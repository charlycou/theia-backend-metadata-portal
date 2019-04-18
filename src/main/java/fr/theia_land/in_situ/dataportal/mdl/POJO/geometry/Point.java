
package fr.theia_land.in_situ.dataportal.mdl.POJO.geometry;

import java.util.List;
import java.util.ListIterator;



/**
 *
 * @author coussotc
 */
public class Point extends GeometryGeoJSON {

    private List<Number> coordinates;
    private final String type = "Point";

    public Point(List<Number> coordinates) {
        ListIterator<Number> listIterator = coordinates.listIterator();
        while (listIterator.hasNext()) {
            Double d = listIterator.next().doubleValue();
            listIterator.set(d);
        }
        this.coordinates = coordinates;
    }
    

    public List<Number> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Number> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }    
}
