package karol.train_waybill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import karol.train_waybill.CompanyViewImpl;
import karol.train_waybill.database.Company;
import karol.train_waybill.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Company company = companyRepo.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return CompanyViewImpl.build(company);
	}

}
