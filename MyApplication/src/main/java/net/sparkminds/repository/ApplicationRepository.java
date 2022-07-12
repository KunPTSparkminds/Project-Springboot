package net.sparkminds.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.sparkminds.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
	@Query(value = "SELECT DISTINCT a.id, a.email_address, a.github_user, a.name FROM application a JOIN project b ON a.id = b.application_id where a.id = b.application_id and b.is_deleted = 0", nativeQuery = true)
	List<Application> getApplication();

	Optional<Application> findByEmailAdress(String email);
}
