package karol.train_waybill;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import karol.train_waybill.database.Company;

public class CompanyViewImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Company company;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public CompanyViewImpl(Integer id, Company company, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.company = company;
		this.password = password;
		this.authorities = authorities;
	}

	public static CompanyViewImpl build(Company company) {
		List<GrantedAuthority> authorities = company.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new CompanyViewImpl(
				company.getId(), 
				company,
				company.getPassword(), 
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CompanyViewImpl user = (CompanyViewImpl) o;
		return Objects.equals(id, user.id);
	}
}
