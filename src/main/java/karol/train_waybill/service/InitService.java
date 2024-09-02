package karol.train_waybill.service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import karol.train_waybill.database.Company;
import karol.train_waybill.database.ERole;
import karol.train_waybill.database.Role;
import karol.train_waybill.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InitService {

	@Autowired
	CompanyRepository companyRepo;
	
	@PostConstruct
	public void Init()
	{		
		Company company = new Company();
		
		Role role = new Role();
		role.setName(ERole.ROLE_ADMIN);				
		
		Set<Role> roles =new HashSet();
		roles.add(role);
		company.setId(1);
		company.setName("Train Cargo");
		company.setEmail("admin@cargo.pl");
		company.setRole(roles);
		company.setPassword(BCrypt.hashpw("admin@01", BCrypt.gensalt()));
		companyRepo.save(company);
		log.debug("Adding admin.");
	}
}
