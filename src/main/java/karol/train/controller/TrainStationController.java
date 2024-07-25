package karol.train.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import karol.train_waybill.database.TrainStation;
import karol.train_waybill.repository.TrainStationRepository;

@Service
public class TrainStationController {

	@Autowired
	TrainStationRepository trainStationRepository;
	
	public List<TrainStation> AllTrainStations()
	{
		List<TrainStation> trainStation=new ArrayList<>();
		trainStationRepository.findAll().forEach(trainStation::add);
		return trainStation;
	}
	
	public TrainStation GetTrainStation(String id)
	{
		return trainStationRepository.findById(id).get();
	}
	
	public void AddTrainStation(TrainStation trainStation)
	{
		trainStationRepository.save(trainStation);
	}

	public void UpdateTrainStation(String id, TrainStation trainStation) {
		trainStationRepository.save(trainStation);
	}
	
	public void DeleteTrainStation(String id)
	{
		trainStationRepository.deleteById(id);
	}
}
