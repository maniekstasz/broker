package soa.biddersystem.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import soa.biddersystem.model.Premise;

@RepositoryRestResource
public interface PremiseRepository extends PagingAndSortingRepository<Premise, Long> {
	
	public Premise findByForeignId(@Param("foreignId") Long foreignId);
}
