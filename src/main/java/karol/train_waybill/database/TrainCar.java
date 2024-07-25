package karol.train_waybill.database;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TrainCar {
	
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
	private String car_number;
	
	private String type;
}
