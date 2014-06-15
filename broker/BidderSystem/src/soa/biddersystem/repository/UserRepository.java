package soa.biddersystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import soa.biddersystem.model.User;



@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long>{
}
