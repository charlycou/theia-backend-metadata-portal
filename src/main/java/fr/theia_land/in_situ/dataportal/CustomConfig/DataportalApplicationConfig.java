/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.theia_land.in_situ.dataportal.CustomConfig;

import fr.theia_land.in_situ.dataportal.CustomConfig.Converters.GeometryGeoJSONReadConverter;
import fr.theia_land.in_situ.dataportal.CustomConfig.Converters.ContactReadConverter;
import fr.theia_land.in_situ.dataportal.CustomConfig.Converters.SensorReadConverter;
import fr.theia_land.in_situ.dataportal.CustomConfig.Converters.SamplingFeatureReadConverter;
import com.mongodb.client.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClients;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

/**
 * @Configuration: allow to register extra Spring beans in the context or import additional configuration classes
 */
@Configuration
public class DataportalApplicationConfig extends AbstractMongoClientConfiguration {

    //@Value: inject property values into components
    @Value("${spring.data.mongodb.uri}")
    private String uri;
    @Value("${spring.data.mongodb.database}")
    private String database;

    /**
     * Configure the MongoClient with the uri
     *
     * @return MongoClient.class
     */
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(uri);
    }

    /**
     * Database name getter
     *
     * @return the database the query will be performed
     */
    @Override
    protected String getDatabaseName() {
        return database;
    }

    /**
     * @Bean: explicitly declare that a method produces a Spring bean to be managed by the Spring container.
     * Configuration of the custom converter defined for the entity schema.
     * @return MongoCustomConversions.class
     */
    @Bean
    @Override
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new ContactReadConverter());
        converterList.add(new GeometryGeoJSONReadConverter());
        converterList.add(new SamplingFeatureReadConverter());
        converterList.add(new SensorReadConverter());
        return new MongoCustomConversions(converterList);
    }

    /**
     * @Bean: explicitly declare that a method produces a Spring bean to be managed by the Spring container.
     * Configuration of the MongoTemplate with the newly defined custom converters. The MongoTemplate class is the
     * central class of Spring’s MongoDB support and provides a rich feature set for interacting with the database. The
     * template offers convenience operations to create, update, delete, and query MongoDB documents and provides a
     * mapping between your domain objects and MongoDB documents.
     *
     * @return MongoTemplate.class
     */
    @Bean
    @Override
    public MongoTemplate mongoTemplate() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), getDatabaseName());
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
        mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        mongoTemplate.setWriteConcern(WriteConcern.MAJORITY);
        mongoMapping.setCustomConversions(customConversions()); // tell mongodb to use the custom converters
        mongoMapping.afterPropertiesSet();
        return mongoTemplate;
    }
}
