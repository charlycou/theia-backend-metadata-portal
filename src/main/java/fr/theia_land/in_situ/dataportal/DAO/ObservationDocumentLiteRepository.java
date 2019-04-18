package fr.theia_land.in_situ.dataportal.DAO;


import fr.theia_land.in_situ.dataportal.model.ObservationDocumentLite;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface used by Spring IoC to inject ObservationDocumentLiteRepository implementation when needed
 * simple CRUD operations are defined in MongoRepository interface
 * Custom operation are defined in CustomObservationDocumentLiteRepository interface
 * 
 * The interface extends MongoRepository that extends PagingAndSortingRepository that extends CRUDRepository that extends Repository.
 * The @ComponentScan annotation at the root package will scan all sub-package for interfaces extending Repository or one of its sub-interfaces.
 * For each interface found it will register the persistence technology specific FactoryBean to create the according proxies that handle invocations of the query methods. 
 * We could achieve the same by using @Component or @Repository annotation
 * Spring data will try to defined ObservationDocumentLiteRepositoryImpl.class that will be injected and instanciated by classes 
 * needing it. However, this class is already defined in the classpath and Spring will use it for dependency injection and 
 * instanciation.
 * @author coussotc
 */

public interface ObservationDocumentLiteRepository extends MongoRepository<ObservationDocumentLite, ObjectId>,
        CustomObservationDocumentLiteRepository {
}
