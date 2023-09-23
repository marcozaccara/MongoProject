package mongo.repository;

import mongo.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends MongoRepository<Customer, String> {

}
