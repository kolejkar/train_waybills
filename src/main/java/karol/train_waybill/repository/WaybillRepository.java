package karol.train_waybill.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import karol.train_waybill.database.Waybill;

public interface WaybillRepository extends JpaRepository<Waybill, Integer> {

}
