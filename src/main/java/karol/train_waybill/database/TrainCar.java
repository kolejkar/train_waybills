package karol.train_waybill.database;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class TrainCar {
	
	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public Set<Waybill> getWaybills() {
		return waybills;
	}

	public void setWaybills(Set<Waybill> waybills) {
		this.waybills = waybills;
	}

	public Boolean getEmpty() {
		return Empty;
	}

	public void setEmpty(Boolean empty) {
		Empty = empty;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCar_number() {
		return car_number;
	}

	public void setCar_number(String car_number) {
		this.car_number = car_number;
	}

	@Id
	@Column(name="car_number")
	private String car_number;
	
	private String type;
	
	private Boolean Empty;
	
	@OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"company"})
	private Set<Waybill> waybills = new HashSet();
	
	private String carrier;
}
