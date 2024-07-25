package karol.train_waybill.database;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TrainStation {

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
	private String id;
	
	private String nazwa_stacji;
	
	private String firma;
}
