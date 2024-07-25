package karol.train_waybill.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Waybill {
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TrainStation getDest_station_id() {
		return dest_station_id;
	}

	public void setDest_station_id(TrainStation dest_station_id) {
		this.dest_station_id = dest_station_id;
	}

	public TrainStation getSource_station_id() {
		return source_station_id;
	}

	public void setSource_station_id(TrainStation source_station_id) {
		this.source_station_id = source_station_id;
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
	
	@OneToOne
	private TrainStation dest_station_id;
	
	@OneToOne
	private TrainStation source_station_id;
	
	private String ladunek;
	
	private String uwagi;
	
	private String trasa;
	
	@OneToOne
	private TrainCar wagon;
}
