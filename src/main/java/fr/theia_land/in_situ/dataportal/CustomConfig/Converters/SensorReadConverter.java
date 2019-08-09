/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.CustomConfig.Converters;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.PhysicalSensor;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.Sensor;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.TemporalExtent;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.VirtualSensor;
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
public class SensorReadConverter implements Converter<Document, Sensor> {

    /**
     * Map DBObject to Sensor inherited class according to the MongoDB document attributes
     *
     * @param source MongoDB Document object
     * @return Sensor Object
     */
    @Override
    public Sensor convert(Document source) {

        if (source.get("name") == null) {
            PhysicalSensor physicalSensor = new PhysicalSensor();
            physicalSensor.setModel(source.get("model", String.class));
            physicalSensor.setManufacturer(source.get("manufacturer", String.class));
            physicalSensor.setSerialNumber(source.get("serialNumber", String.class));
            if (source.get("sensorType", List.class) != null) {
                List<I18n> sensorType = new ArrayList<>();
                List<Document> sensorTypesJson = source.get("sensorType", List.class);
                sensorTypesJson.forEach(item -> {
                    I18n tmp = new I18n();
                    tmp.setLang(item.get("lang", String.class));
                    tmp.setText(item.get("text", String.class));
                    sensorType.add(tmp);
                });
                physicalSensor.setSensorType(sensorType);
            }
            if (source.get("calibration") != null) {
                List<I18n> calibration = new ArrayList<>();
                List<Document> calibrationsJson = source.get("calibration", List.class);
                calibrationsJson.forEach(item -> {
                    I18n tmp = new I18n();
                    tmp.setLang(item.get("lang", String.class));
                    tmp.setText(item.get("text", String.class));
                    calibration.add(tmp);
                });
                physicalSensor.setCalibration(calibration);
            }
            if (source.get("document") != null) {
                Document sourceDocument = source.get("document", Document.class);
                fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.Document document = new fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.Document();
                document.setType(sourceDocument.get("type", String.class));
                if (sourceDocument.get("url", Document.class) != null) {
                    List<I18n> url = new ArrayList<>();
                    List<Document> urlsJson = source.get("calibration", List.class);
                    urlsJson.forEach(item -> {
                        I18n tmp = new I18n();
                        tmp.setLang(item.get("lang", String.class));
                        tmp.setText(item.get("text", String.class));
                        url.add(tmp);
                    });
                    document.setUrl(url);
                }
                physicalSensor.setDocument(document);
            }
            if (source.get("activityPeriods") != null) {
                List<Document> sourceActivity = source.get("activityPeriods", List.class);
                List<TemporalExtent> temporalExtents = new ArrayList<>();
                for (Document d : sourceActivity) {
                    TemporalExtent te = new TemporalExtent();
                    te.setDateBeg(d.get("dateBeg", String.class));
                    te.setDateEnd(d.get("dateEnd", String.class));
                    temporalExtents.add(te);
                }
                physicalSensor.setActivityPeriods(temporalExtents);
            }
            return physicalSensor;
        } else {
            VirtualSensor virtualSensor = new VirtualSensor();
            if (source.get("activityPeriods") != null) {
                List<Document> sourceActivity = source.get("activityPeriods", List.class);
                List<TemporalExtent> temporalExtents = new ArrayList<>();
                for (Document d : sourceActivity) {
                    TemporalExtent te = new TemporalExtent();
                    te.setDateBeg(d.get("dateBeg", String.class));
                    te.setDateEnd(d.get("dateEnd", String.class));
                    temporalExtents.add(te);
                }
                virtualSensor.setActivityPeriods(temporalExtents);
            }
            if (source.get("document") != null) {
                Document sourceDocument = source.get("document", Document.class);
                fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.Document document = new fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.Document();
                document.setType(sourceDocument.get("type", String.class));
                if (sourceDocument.get("url", Document.class) != null) {
                    List<I18n> url = new ArrayList<>();
                    List<Document> urlsJson = source.get("calibration", List.class);
                    urlsJson.forEach(item -> {
                        I18n tmp = new I18n();
                        tmp.setLang(item.get("lang", String.class));
                        tmp.setText(item.get("text", String.class));
                        url.add(tmp);
                    });
                    document.setUrl(url);
                }
                virtualSensor.setDocument(document);
            }
            if (source.get("name") != null) {
                List<I18n> name = new ArrayList<>();
                List<Document> namesJson = source.get("name", List.class);
                namesJson.forEach(item -> {
                    I18n tmp = new I18n();
                    tmp.setLang(item.get("lang", String.class));
                    tmp.setText(item.get("text", String.class));
                    name.add(tmp);
                });
                virtualSensor.setName(name);
            }
            if (source.get("parametrisationDescription") != null) {
                List<I18n> parametrisationDescription = new ArrayList<>();
                List<Document> parametrisationDescriptionsJson = source.get("parametrisationDescription", List.class);
                parametrisationDescriptionsJson.forEach(item -> {
                    I18n tmp = new I18n();
                    tmp.setLang(item.get("lang", String.class));
                    tmp.setText(item.get("text", String.class));
                    parametrisationDescription.add(tmp);
                });
                virtualSensor.setParametrisationDescription(parametrisationDescription);
            }

            return virtualSensor;
        }
    }

}
