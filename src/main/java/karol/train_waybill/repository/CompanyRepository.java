package karol.train_waybill.repository;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import karol.train_waybill.database.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Cacheable
	Optional<Company> findByEmail(String email);
}
