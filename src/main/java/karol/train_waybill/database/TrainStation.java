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
public class TrainStation {

	public Set<Waybill> getWaybills() {
		return waybills;
	}

	public void setWaybills(Set<Waybill> waybills) {
		this.waybills = waybills;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNazwa_stacji() {
		return nazwa_stacji;
	}

	public void setNazwa_stacji(String nazwa_stacji) {
		this.nazwa_stacji = nazwa_stacji;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	@Id
	@Column(name="station_id")
	private String id;
	
	private String nazwa_stacji;
	
	private String firma;
	
	@OneToMany(mappedBy = "company", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"company"})
	private Set<Waybill> waybills = new HashSet();
}
