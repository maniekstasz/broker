package soa.premisebroker.repository;

import java.util.Date;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import soa.premisebroker.model.Bill;

@RepositoryRestResource(exported=false)
public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {

	@Query("select bill from Bill bill where bill.dueDate between :lastDate and :date and status = '1'")
	public List<Bill> getDueBills(@Param("date")Date date, @Param("lastDate")Date lastDate);
}
