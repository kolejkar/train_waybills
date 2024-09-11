package karol.train_waybill.front.company;

import java.util.List;

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
import karol.train_waybill.front.MenuGUI;
import karol.train_waybill.repository.CompanyRepository;
import karol.train_waybill.repository.TrainCarRepository;
import karol.train_waybill.repository.TrainStationRepository;
import karol.train_waybill.repository.WaybillRepository;

@Route("company/waybill/add")
public class CompanyWaybillAdd extends VerticalLayout  implements BeforeEnterObserver{
	
	MenuGUI menu;
	
	Label info;
	Label info1;
	Label info2;
	Label info3;
	Label labelCar;
	Label labelUwagi;
	
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
	
	public CompanyWaybillAdd(TrainCarRepository trainCarRepo, TrainStationRepository trainStationRepo, CompanyRepository companyRepo)
	{
		MenuGUI menu = new MenuGUI(companyRepo);
		add(menu);
		
		info = new Label("Tworzenie listu przewozowego:");
		
		info1 = new Label("Wprowadz ladunek do transportu:");
		textLadunek = new TextField("ladunek:");
		
		labelCar = new Label("Wprowadz kod wagonu:");
		textCar = new TextField("Wagon:");
		
		labelUwagi = new Label("Informacje dodatkowe:");
		textUwagi = new TextField("Uwagi:");

		info2 = new Label("Firma nadająca:");
		
		comboTrainStationNad = new ComboBox<TrainStation>("Uzywane bocznice kolejowe");
		comboTrainStationNad.setItems(trainStationRepo.findAll());
		comboTrainStationNad.setItemLabelGenerator(TrainStation::getFirma);
		
		info3 = new Label("Firma odbierajaca:");
		
		comboTrainStationOdb = new ComboBox<TrainStation>("Uzywane bocznice kolejowe");
		comboTrainStationOdb.setItems(trainStationRepo.findAll());
		comboTrainStationOdb.setItemLabelGenerator(TrainStation::getFirma);
		
		buttonRejestracja = new Button("Dodaj");
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		
		menu = new MenuGUI(companyRepo);
		
		buttonRejestracja.addClickListener(clickEvent -> {
			
			if (CheckTrainCar(textCar.getValue()) == true)
			{
				Notification notification = Notification.show("Podany wagon jest zajęty! Podaj inny wagon.");
			}
			else
			{			
				Waybill waybill = new Waybill();
				waybill.setLadunek(textLadunek.getValue());
				waybill.setUwagi(textUwagi.getValue());				
				waybill.setWagon(GetTrainCar(textCar.getValue()));
				
				waybill.setSource_station_id(comboTrainStationNad.getValue());
				waybill.setDest_station_id(comboTrainStationOdb.getValue());
				
				waybill.setStatus(TransportStatus.Report);
				
				if(getCompanyEmail().length() > 0)
				{
					Company company = companyRepo.findByEmail(getCompanyEmail()).get();
					waybill.setCompany(company);
					waybillRepo.save(waybill);
				    
				    Notification notification = Notification.show("List przewozowy dodany!");
				    UI.getCurrent().getPage().setLocation("/company/view");
				}
			}
		});
		
		add(menu);
		add(info, info1, textLadunek, labelCar, textCar, labelUwagi, textUwagi, info2, comboTrainStationNad,
				info3, comboTrainStationOdb);
		add(buttonRejestracja);
	}
	
	private TrainCar GetTrainCar(String carNumber)
	{
		List<TrainCar> trainCars = trainCarRepo.findAll();
		
		TrainCar trainCar = null;
		for (TrainCar tc : trainCars)
		{
			if (tc.getCar_number().equals(carNumber))
			{
				trainCar = tc;
				break;
			}
		}
		return trainCar;
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
	
	private String getCompanyEmail()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken))
		{
			CompanyDetails company = (CompanyDetails) authentication.getPrincipal();
			return company.getUsername();
		}
		return "";
	}
}
