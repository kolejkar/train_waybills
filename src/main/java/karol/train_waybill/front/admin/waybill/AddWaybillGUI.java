package karol.train_waybill.front.admin.waybill;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import karol.train_waybill.UserDetails.CompanyDetails;
import karol.train_waybill.database.Company;
import karol.train_waybill.database.TrainCar;
import karol.train_waybill.database.TrainStation;
import karol.train_waybill.database.TransportStatus;
import karol.train_waybill.database.Waybill;
import karol.train_waybill.repository.CompanyRepository;
import karol.train_waybill.repository.TrainCarRepository;
import karol.train_waybill.repository.TrainStationRepository;
import karol.train_waybill.repository.WaybillRepository;

@Route("waybill/add")
public class AddWaybillGUI extends VerticalLayout  implements BeforeEnterObserver {
	
	Button buttonRejestracja;
	
	TextField textLadunek;
	TextField textCar;
	TextField textUwagi;
	
	ComboBox<TrainStation> comboTrainStationNad;	
	ComboBox<TrainStation> comboTrainStationOdb;
	
	@Autowired
	private TrainCarRepository trainCarRepo;
	
	@Autowired
	WaybillRepository waybillRepo;
	
	@Autowired
	private CompanyRepository companyRepo;
	
	public AddWaybillGUI(TrainCarRepository trainCarRepo, TrainStationRepository trainStationRepo)
	{
		Label info = new Label("Tworzenie listu przewozowego:");
		
		Label info1 = new Label("Wprowadz ladunek do transportu:");
		textLadunek = new TextField("ladunek:");
		
		Label labelCar = new Label("Wprowadz kod wagonu:");
		textCar = new TextField("Wagon:");
		
		Label labelUwagi = new Label("Informacje dodatkowe:");
		textUwagi = new TextField("Uwagi:");

		Label info2 = new Label("Firma nadająca:");
		
		comboTrainStationNad = new ComboBox<TrainStation>("Uzywane bocznice kolejowe");
		comboTrainStationNad.setItems(trainStationRepo.findAll());
		comboTrainStationNad.setItemLabelGenerator(TrainStation::getFirma);
		
		Label info3 = new Label("Firma odbierajaca:");
		
		comboTrainStationOdb = new ComboBox<TrainStation>("Uzywane bocznice kolejowe");
		comboTrainStationOdb.setItems(trainStationRepo.findAll());
		comboTrainStationOdb.setItemLabelGenerator(TrainStation::getFirma);
		
		buttonRejestracja = new Button("Dodaj");
		
		add(info, info1, textLadunek, labelCar, textCar, labelUwagi, textUwagi, info2, comboTrainStationNad,
				info3, comboTrainStationOdb);
		add(buttonRejestracja);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		
		buttonRejestracja.addClickListener(clickEvent -> {
			
			if (CheckTrainCar(textCar.getValue()) == true)
			{
				Notification notification = Notification.show("Podany wagon jest zajęty! Podaj inny wagon.");
			}
			else
			{
				List<TrainCar> trainCars = trainCarRepo.findAll();
			
				Waybill waybill = new Waybill();
				waybill.setLadunek(textLadunek.getValue());
				waybill.setUwagi(textUwagi.getValue());			
				waybill.setWagon(trainCars.stream().filter(c -> 
				textCar.getValue().equals(c.getCar_number())).findAny().orElse(null));
				
				waybill.setSource_station_id(comboTrainStationNad.getValue());
				waybill.setDest_station_id(comboTrainStationOdb.getValue());
				
				waybill.setStatus(TransportStatus.Report);
				
				waybillRepo.save(waybill);
						    
			    Notification notification = Notification.show("List przewozowy dodany!");
			    UI.getCurrent().getPage().setLocation("/waybill/view");
			}
		});
	}
	
	private Boolean CheckTrainCar(String carNumber)
	{
		List<TrainCar> trainCars = trainCarRepo.findAll();
		
		for (TrainCar car : trainCars)
		{
			if (car.getCar_number().equals(carNumber) && car.getEmpty() == true)
			{
				List<Waybill> waybills = waybillRepo.findAll();
				
				for (Waybill w : waybills)
				{
					if (w.getWagon() == car)
					{
						return true;
					}
				}
				return false;
			}
		}
		return true;
	}
}
