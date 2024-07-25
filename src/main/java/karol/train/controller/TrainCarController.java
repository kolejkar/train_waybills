package karol.train.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import karol.train_waybill.database.TrainCar;
import karol.train_waybill.repository.TrainCarRepository;

@Service
public class TrainCarController {
	
	@Autowired
	TrainCarRepository trainCarRepository;
	
	public List<TrainCar> AllTrainCars()
	{
		List<TrainCar> trainCar=new ArrayList<>();
		trainCarRepository.findAll().forEach(trainCar::add);
		return trainCar;
	}
	
	public TrainCar GetTrainCar(String id)
	{
		return trainCarRepository.findById(id).get();
	}
	
	public void AddTrainCar(TrainCar trainCar)
	{
		trainCarRepository.save(trainCar);
	}

	public void UpdateTrainCar(String id, TrainCar trainCar) {
		trainCarRepository.save(trainCar);
	}
	
	public void DeleteTrainCar(String id)
	{
		trainCarRepository.deleteById(id);
	}
}
