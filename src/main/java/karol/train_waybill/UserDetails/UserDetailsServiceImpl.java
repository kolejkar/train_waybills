package karol.train_waybill.UserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import karol.train_waybill.database.Company;
import karol.train_waybill.repository.CompanyRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    private CompanyRepository companyRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Company company = companyRepository.findByEmail(username).get();
         
        if (company == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new CompanyDetails(company);
    }
}
