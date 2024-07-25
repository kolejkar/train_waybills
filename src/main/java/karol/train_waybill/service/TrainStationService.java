package karol.train_waybill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import karol.train.controller.TrainStationController;
import karol.train_waybill.database.TrainStation;

@RestController
public class TrainStationService {
	
	@Autowired
	private TrainStationController stationController;
	
	@RequestMapping("/station")
	public List<TrainStation> GetAllStations()
	{
		return stationController.AllTrainStations();
	}
	
	@RequestMapping("/station/{id}")
	public TrainStation GetStation(@PathVariable String id)
	{
		return stationController.GetTrainStation(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/station")
	public void addTrainStation(@RequestBody TrainStation station)
	{
		stationController.AddTrainStation(station);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/station/{id}")
	public void updateStation(@PathVariable   String id, @RequestBody TrainStation station)
	{
		stationController.UpdateTrainStation(id, station);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/station/{id}")
	public void deleteStation(@PathVariable String id)
	{
		stationController.DeleteTrainStation(id);
	}

}
