package karol.train_waybill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import karol.train.controller.WaybillController;
import karol.train_waybill.database.Waybill;

@RestController
public class WaybillService {
	
	@Autowired
	private WaybillController waybillController;
	
	@RequestMapping("/waybill")
	public List<Waybill> GetAllWAybills()
	{
		return waybillController.AllWaybills();
	}
	
	@RequestMapping("/waybill/{id}")
	public Waybill GetWaybill(@PathVariable Integer id)
	{
		return waybillController.GetWaybill(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/waybill")
	public void addWaybill(@RequestBody Waybill waybill)
	{
		waybillController.AddWaybill(waybill);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/waybill/{id}")
	public void updateWaybill(@PathVariable   Integer id, @RequestBody Waybill waybill)
	{
		waybillController.UpdateWaybill(id, waybill);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/waybill/{id}")
	public void deleteWaybill(@PathVariable Integer id)
	{
		waybillController.DeleteWaybill(id);
	}

}
