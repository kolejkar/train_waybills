package karol.train_waybill.front;

import java.util.List;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.TrainCar;
import karol.train_waybill.database.TrainStation;
import karol.train_waybill.database.Waybill;
import karol.train_waybill.repository.TrainCarRepository;
import karol.train_waybill.repository.TrainStationRepository;
import karol.train_waybill.repository.WaybillRepository;

@Route("waybill/add")
public class AddWaybillGUI extends VerticalLayout {
	
	public AddWaybillGUI(TrainCarRepository trainCarRepo, TrainStationRepository trainStationRepo, 
			WaybillRepository waybillRepo)
	{
		Label info = new Label("Tworzenie listu przewozowego:");
		
		Label info1 = new Label("Wprowadz ladunek do transportu:");
		TextField textLadunek = new TextField("ladunek:");
		
		Label labelCar = new Label("Wprowadz kod wagonu:");
		TextField textCar = new TextField("Wagon:");
		
		Label labelUwagi = new Label("Informacje dodatkowe:");
		TextField textUwagi = new TextField("Uwagi:");

		Label info2 = new Label("Firma nadająca:");
		
		ComboBox<TrainStation> comboTrainStationNad = new ComboBox<TrainStation>("Uzywane bocznice kolejowe");
		comboTrainStationNad.setItems(trainStationRepo.findAll());
		comboTrainStationNad.setItemLabelGenerator(TrainStation::getFirma);
		
		Label info3 = new Label("Firma odbierajaca:");
		
		ComboBox<TrainStation> comboTrainStationOdb = new ComboBox<TrainStation>("Uzywane bocznice kolejowe");
		comboTrainStationOdb.setItems(trainStationRepo.findAll());
		comboTrainStationOdb.setItemLabelGenerator(TrainStation::getFirma);
		
		Button buttonRejestracja = new Button("Dodaj");
		buttonRejestracja.addClickListener(clickEvent -> {
								
			List<TrainCar> trainCars = trainCarRepo.findAll();
			
			Waybill waybill = new Waybill();
			waybill.setLadunek(textLadunek.getValue());
			waybill.setUwagi(textUwagi.getValue());
			waybill.setWagon(trainCars.stream().filter(c -> 
					textCar.getValue().equals(c.getCar_number())).findAny().orElse(null));
			
			waybill.setSource_station_id(comboTrainStationNad.getValue());
			waybill.setDest_station_id(comboTrainStationOdb.getValue());
					
			waybillRepo.save(waybill);
					    
		    Notification notification = Notification.show("List przewozowy dodany!");
		    UI.getCurrent().getPage().setLocation("/waybill/view");
		});
		
		add(info, info1, textLadunek, labelCar, textCar, labelUwagi, textUwagi, info2, comboTrainStationNad,
				info3, comboTrainStationOdb);
		add(buttonRejestracja);
	}
}
