package karol.train_waybill.database;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Waybill {
	
	public TransportStatus getStatus() {
		return status;
	}

	public void setStatus(TransportStatus status) {
		this.status = status;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TrainStation getDest_station_id() {
		return dest_station;
	}

	public void setDest_station_id(TrainStation dest_station_id) {
		this.dest_station = dest_station_id;
	}

	public TrainStation getSource_station_id() {
		return source_station;
	}

	public void setSource_station_id(TrainStation source_station_id) {
		this.source_station = source_station_id;
	}

	public String getLadunek() {
		return ladunek;
	}

	public void setLadunek(String ladunek) {
		this.ladunek = ladunek;
	}

	public String getUwagi() {
		return uwagi;
	}

	public void setUwagi(String uwagi) {
		this.uwagi = uwagi;
	}

	public String getTrasa() {
		return trasa;
	}

	public void setTrasa(String trasa) {
		this.trasa = trasa;
	}

	public TrainCar getWagon() {
		return wagon;
	}

	public void setWagon(TrainCar wagon) {
		this.wagon = wagon;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="waybill_id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="dest_station_id")
	private TrainStation dest_station;
	
	@ManyToOne
	@JoinColumn(name="source_station_id")
	private TrainStation source_station;
	
	private String ladunek;
	
	private String uwagi;
	
	private String trasa;
	
	@ManyToOne
	@JoinColumn(name = "car_number")
	private TrainCar wagon;
	
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	private TransportStatus status;
}
