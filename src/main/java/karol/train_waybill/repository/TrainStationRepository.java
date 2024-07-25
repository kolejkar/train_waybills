package karol.train_waybill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import karol.train_waybill.database.TrainStation;

public interface TrainStationRepository extends JpaRepository<TrainStation, String> {
	
}
