package soa.premisebroker.repository;

import org.springframework.data.repository.CrudRepository;

import soa.premisebroker.model.PriceDictionary;
import soa.premisebroker.model.PriceDictionary.Product;

public interface PriceDictionaryRepository extends
		CrudRepository<PriceDictionary, Long> {
	public PriceDictionary findByProduct(Product product);
}
