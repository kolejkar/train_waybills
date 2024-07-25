package karol.train_waybill.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import karol.train_waybill.database.Company;
import karol.train_waybill.database.ERole;
import karol.train_waybill.database.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByName(ERole role);

}
