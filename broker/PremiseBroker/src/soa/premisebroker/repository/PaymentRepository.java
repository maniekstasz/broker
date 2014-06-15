package soa.premisebroker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import soa.premisebroker.model.Payment;

@RepositoryRestResource(exported=false)
public interface PaymentRepository extends CrudRepository<Payment, Long>  {

}
