/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.CustomConfig.Converters;

import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.Contact;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.observation.I18n;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.Organisation;
import fr.theia_land.in_situ.dataportal.mdl.POJO.detail.dataset.Person;
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
public class ContactReadConverter implements Converter<Document, Contact> {
    /**
     * Map DBObject to Contact inherited class according to the MongoDB document attributes
     * @param source MongoDB Document object
     * @return Contact Object
     */
    @Override
    public Contact convert(Document source) {
        if (source.get("firstName") == null) {
            Organisation organisation = new Organisation();
            List<I18n> name = new ArrayList<>();
            List<Document> namesJson = source.get("name", List.class);
            namesJson.forEach(item -> {
                I18n tmp = new I18n();
                tmp.setLang(item.get("lang", String.class));
                tmp.setText(item.get("text", String.class));
                name.add(tmp);
            });
            organisation.setName(name);
            organisation.setAcronym(source.get("acronym", String.class));
            organisation.setRole(source.get("role", String.class));
            return organisation;
        }
        Person person = new Person();
        person.setFirstName(source.get("firstName", String.class));
        person.setLastName(source.get("lastName", String.class));
        person.setRole(source.get("role", String.class));
        person.setEmail(source.get("email", String.class));
        person.setOrcId(source.get("orcId", String.class));
        if (source.get("organisation") != null) {
            Document sourceOrg = source.get("organisation", Document.class);
            Organisation organisation = new Organisation();
            organisation.setAcronym(sourceOrg.get("acronym", String.class));
            organisation.setRole(sourceOrg.get("role", String.class));
            if (sourceOrg.get("name") != null) {
                List<I18n> name = new ArrayList<>();
                List<Document> namesJson = sourceOrg.get("name", List.class);
                namesJson.forEach(item -> {
                    I18n tmp = new I18n();
                    tmp.setLang(item.get("lang", String.class));
                    tmp.setText(item.get("text", String.class));
                    name.add(tmp);
                });
                organisation.setName(name);
            }
            person.setOrganisation(organisation);
        }
        return person;
    }
}
