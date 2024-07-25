package karol.train_waybill.front;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.TrainCar;
import karol.train_waybill.database.TrainStation;
import karol.train_waybill.database.Waybill;
import karol.train_waybill.repository.TrainCarRepository;
import karol.train_waybill.repository.TrainStationRepository;
import karol.train_waybill.repository.WaybillRepository;

@Route("waybill/edit/:WaybillID")
public class EditWaybillGUI extends VerticalLayout implements BeforeEnterObserver {

	private Integer waybillID;
	
	TextField textLadunek;
	TextField textCar;
	TextField textUwagi;
	ComboBox<TrainStation> comboTrainStationNad;
	ComboBox<TrainStation> comboTrainStationOdb;
	Button buttonRejestracja;
	
	@Autowired
	TrainStationRepository trainStationRepo;
	
	@Autowired
	TrainCarRepository trainCarRepo;
	
	@Autowired
	WaybillRepository waybillRepo;
	
	public EditWaybillGUI()
	{
		Label info = new Label("Edycja listu przewozowego:");
		
		Label info1 = new Label("Wprowadz ladunek do transportu:");
		textLadunek = new TextField("ladunek:");
		
		Label labelCar = new Label("Wprowadz kod wagonu:");
		textCar = new TextField("Wagon:");
		
		Label labelUwagi = new Label("Informacje dodatkowe:");
		textUwagi = new TextField("Uwagi:");

		Label info2 = new Label("Firma nadająca:");
		
		comboTrainStationNad = new ComboBox<TrainStation>("Uzywane bocznice kolejowe");
		
		Label info3 = new Label("Firma odbierajaca:");
		
		comboTrainStationOdb = new ComboBox<TrainStation>("Uzywane bocznice kolejowe");
		
		Button buttonRejestracja = new Button("Dodaj");
		
		buttonRejestracja.addClickListener(clickEvent -> {
			
			List<TrainCar> trainCars = trainCarRepo.findAll();
			
			Waybill waybill1 = new Waybill();
			waybill1.setId(waybillID);
			waybill1.setLadunek(textLadunek.getValue());
			waybill1.setUwagi(textUwagi.getValue());
			waybill1.setWagon(trainCars.stream().filter(c -> 
					textCar.getValue().equals(c.getCar_number())).findAny().orElse(null));
			
			waybill1.setSource_station_id(comboTrainStationNad.getValue());
			waybill1.setDest_station_id(comboTrainStationOdb.getValue());
			
			waybillRepo.save(waybill1);
		    
		    Notification notification = Notification.show("Zmiany zapisane!");
		});
		
		add(info, info1, textLadunek, labelCar, textCar, labelUwagi, textUwagi, info2, comboTrainStationNad,
				info3, comboTrainStationOdb);
		add(buttonRejestracja);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// TODO Auto-generated method stub
		
		comboTrainStationNad.setItems(trainStationRepo.findAll());
		comboTrainStationNad.setItemLabelGenerator(TrainStation::getFirma);
		
		comboTrainStationOdb.setItems(trainStationRepo.findAll());
		comboTrainStationOdb.setItemLabelGenerator(TrainStation::getFirma);
		
		try 
	    {	
            waybillID = Integer.parseInt(event.getRouteParameters().get("WaybillID").get());
	    }
	    catch (NumberFormatException e) {
		    waybillID = 0;
	    }
		
	    Waybill waybill = waybillRepo.findById(waybillID).orElse(null);
	    
	    if (waybill != null)
	    {
				
	    	textLadunek.setValue(waybill.getLadunek());
	    	textCar.setValue(waybill.getWagon().getCar_number());
	    	textUwagi.setValue(waybill.getUwagi());
	    	comboTrainStationNad.setValue(waybill.getSource_station_id());
	    	comboTrainStationOdb.setValue(waybill.getDest_station_id());
			    
	    }
	}
}
