/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.CustomConfig.Converters;

import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.GeometryGeoJSON;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.LineString;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.MultiLineString;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.MultiPoint;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.MultiPolygon;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.Point;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.Polygon;
import java.util.List;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * @ReadingConverter: Spring data mongodb annotation to enable the class to handle the mapping of DBObject into Java
 * Objects
 */
@ReadingConverter
public class GeometryGeoJSONReadConverter implements Converter<Document, GeometryGeoJSON> {

    /**
     * Map DBObject to GeometryGeoJSON inherited class according to the MongoDB document attributes
     * @param source MongoDB Document object
     * @return GeometryGeoJSON Object
     */
    @Override
    public GeometryGeoJSON convert(Document source) {
        String type = (String) source.get("type");
        switch (type) {
            case "Point":
                return new Point((List<Number>) source.get("coordinates"));
            case "LineString":
                return new LineString((List<List<Number>>) source.get("coordinates"));
            case "MultiPoint":
                return new MultiPoint((List<List<Number>>) source.get("coordinates"));
            case "MultiLineString":
                return new MultiLineString((List<List<List<Number>>>) source.get("coordinates"));
            case "Polygon":
                return new Polygon((List<List<List<Number>>>) source.get("coordinates"));
            case "MultiPolygon":
                return new MultiPolygon((List<List<List<List<Number>>>>) source.get("coordinates"));
            default : throw new IllegalArgumentException("invalid argument in GeohetryGeoJSON.");
        }
    }
}
