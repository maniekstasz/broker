package soa.premisebroker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import soa.premisebroker.model.User;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {

}
