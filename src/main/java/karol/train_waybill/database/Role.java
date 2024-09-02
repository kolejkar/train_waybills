package karol.train_waybill.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Enumerated;

@Entity
@Table(name = "roles")
public class Role {

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public ERole getName() {
		return name;
	}

	public String getStringName() {
		return name.toString();
	}

	public void setName(ERole name) {
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	private ERole name;
}
