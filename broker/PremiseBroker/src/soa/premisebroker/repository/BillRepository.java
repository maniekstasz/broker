package soa.premisebroker.repository;

import org.springframework.data.repository.CrudRepository;

import soa.premisebroker.model.Bill;

public interface BillRepository extends CrudRepository<Bill, Long> {


}
