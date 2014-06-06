package soa.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import soa.common.model.PicPath;

//@RepositoryRestResource(exported = false)
public interface PicPathRepository extends CrudRepository<PicPath, Long> {

}
