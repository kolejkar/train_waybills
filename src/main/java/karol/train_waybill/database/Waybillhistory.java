package karol.train_waybill.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import karol.train_waybill.database.adnotation.Status;

@Entity
public class Waybillhistory {

    @Id
    @Column(name="history_id")
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
	
	//Dodać więcej wagonów 
	@OneToOne
	@JoinColumn(name = "car_number")
	private TrainCar wagon;
	
	@ManyToOne
	@JoinColumn(name="company_id")
	private Company company;

    @Status(status=TransportStatus.Archive)
    @Status(status=TransportStatus.Reject)
    private TransportStatus status;
}
