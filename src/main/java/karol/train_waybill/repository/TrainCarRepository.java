package karol.train_waybill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import karol.train_waybill.database.TrainCar;

public interface TrainCarRepository extends JpaRepository<TrainCar, String> {

}
