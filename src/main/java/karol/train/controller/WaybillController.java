package karol.train.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import karol.train_waybill.database.Waybill;
import karol.train_waybill.repository.WaybillRepository;

@Service
public class WaybillController {

	@Autowired
	WaybillRepository waybillRepository;
	
	public List<Waybill> AllWaybills()
	{
		List<Waybill> waybill=new ArrayList<>();
		waybillRepository.findAll().forEach(waybill::add);
		return waybill;
	}
	
	public Waybill GetWaybill(Integer id)
	{
		return waybillRepository.findById(id).get();
	}
	
	public void AddWaybill(Waybill waybill)
	{
		waybillRepository.save(waybill);
	}

	public void UpdateWaybill(Integer id, Waybill waybill) {
		waybillRepository.save(waybill);
	}
	
	public void DeleteWaybill(Integer id)
	{
		waybillRepository.deleteById(id);
	}
}
