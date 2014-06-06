package soa.common.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import soa.common.model.User;

@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	@Query("select user from User user where user.credentials.username =:login or user.credentials.mail =:login")
	public User findByCredentialsUsernameOrCredentialsMail(
			@Param("login") String login);
}
