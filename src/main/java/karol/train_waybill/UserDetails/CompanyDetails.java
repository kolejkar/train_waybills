package karol.train_waybill.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import karol.train_waybill.database.Company;
import karol.train_waybill.database.Role;

public class CompanyDetails implements UserDetails {

	 private Company company;
	      
	    public CompanyDetails(Company company) {
	        this.company = company;
	    }
	 
	    @Transactional
	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	         
	        return company.getRole().stream()
	        		 .map(Role::getStringName)
	                 .map(SimpleGrantedAuthority::new)
	                 .collect(Collectors.toSet());
	    }
	 
	    @Override
	    public String getPassword() {
	        return company.getPassword();
	    }
	 
	    @Override
	    public String getUsername() {
	        return company.getEmail();
	    }
	 
	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }
	 
	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }
	 
	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }
	 
	    @Override
	    public boolean isEnabled() {
	        return true;
	    }
}
