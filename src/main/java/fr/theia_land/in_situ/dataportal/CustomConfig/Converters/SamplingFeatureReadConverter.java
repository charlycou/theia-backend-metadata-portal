/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.CustomConfig.Converters;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.SamplingFeature;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.SamplingGeometry;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.SamplingSpecimen;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.GeometryGeoJSON;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.LineString;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.MultiLineString;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.MultiPoint;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.MultiPolygon;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.Point;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.Polygon;
import fr.theia_land.in_situ.dataportal.mdl.POJO.geometry.Properties;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * @ReadingConverter: Spring data mongodb annotation to enable the class to handle the mapping of DBObject into Java
 * Objects
 */
@ReadingConverter
public class SamplingFeatureReadConverter implements Converter< Document, SamplingFeature> {

    /**
     * Map DBObject to SamplingFeature inherited class according to the MongoDB document attributes
     *
     * @param source MongoDB Document object
     * @return SamplingFeature Object
     */
    @Override
    public SamplingFeature convert(Document source) {
        if (source.get("geometry") == null) {
            return new SamplingSpecimen();
        }
        SamplingGeometry samplingGeometry = new SamplingGeometry();
        samplingGeometry.setType(source.get("type", String.class));
        samplingGeometry.setProperties(new Properties());
        Document sourceGeo = source.get("geometry", Document.class);
        String type = sourceGeo.get("type", String.class);
        GeometryGeoJSON geom;
        switch (type) {
            case "Point":
                List<Number> coordinatesTmp = sourceGeo.get("coordinates", List.class);
//                List<Double> coordinates = new ArrayList<>();
//                for (int i=0; i<coordinatesTmp.size(); i++) {
//                    coordinates.add(coordinatesTmp.get(i).doubleValue());
//                }
                geom = new Point(coordinatesTmp);
                break;
                case "LineString":
                geom = new LineString((List<List<Number>>) sourceGeo.get("coordinates"));
                break;
            case "MultiPoint":
                geom = new MultiPoint((List<List<Number>>) sourceGeo.get("coordinates"));
                break;
            case "MultiLineString":
                geom = new MultiLineString((List<List<List<Number>>>) sourceGeo.get("coordinates"));
                break;
            case "Polygon":
                geom = new Polygon((List<List<List<Number>>>) sourceGeo.get("coordinates"));
                break;
            case "MultiPolygon":
                geom = new MultiPolygon((List<List<List<List<Number>>>>) sourceGeo.get("coordinates"));
                break;
            default:
                throw new IllegalArgumentException("invalid argument in GeohetryGeoJSON.");
        }
        samplingGeometry.setGeometry(geom);
        if (source.get("name") != null) {
            List<I18n> name = new ArrayList<>();
            List<Document> namesJson = source.get("name", List.class);
            namesJson.forEach(item -> {
                I18n tmp = new I18n();
                tmp.setLang(item.get("lang", String.class));
                tmp.setText(item.get("text", String.class));
                name.add(tmp);
            });
            samplingGeometry.setName(name);
        }
        return samplingGeometry;
    }

}
