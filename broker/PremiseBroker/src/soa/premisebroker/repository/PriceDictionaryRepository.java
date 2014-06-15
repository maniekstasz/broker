package soa.premisebroker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import soa.premisebroker.model.PriceDictionary;
import soa.premisebroker.model.PriceDictionary.Product;

@RepositoryRestResource(exported=false)
public interface PriceDictionaryRepository extends
		CrudRepository<PriceDictionary, Long> {
	public PriceDictionary findByProduct(Product product);
}
