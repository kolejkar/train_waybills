package karol.train_waybill.front.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

@Route("company/waybill/edit/:WaybillID")
public class CompanyWaybillEdit extends VerticalLayout implements BeforeEnterObserver{

	private Integer waybillID;
	
	private Waybill waybill;
	
	MenuGUI menu;
	
	Label info;
	Label info1;
	Label info2;
	Label info3;
	Label labelCar;
	Label labelUwagi;
	
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
	
	@Autowired
	private CompanyRepository companyRepo;
	
	public CompanyWaybillEdit(CompanyRepository companyR)
	{
		MenuGUI menu = new MenuGUI(companyR);
		add(menu);
		
		info = new Label("Edycja listu przewozowego:");
		
		info1 = new Label("Wprowadz ladunek do transportu:");
		textLadunek = new TextField("ladunek:");
		
		labelCar = new Label("Wprowadz kod wagonu:");
		textCar = new TextField("Wagon:");
		
		labelUwagi = new Label("Informacje dodatkowe:");
		textUwagi = new TextField("Uwagi:");

		info2 = new Label("Firma nadająca:");
		
		comboTrainStationNad = new ComboBox<TrainStation>("Uzywane bocznice kolejowe");
		
		info3 = new Label("Firma odbierajaca:");
		
		comboTrainStationOdb = new ComboBox<TrainStation>("Uzywane bocznice kolejowe");
		
		buttonRejestracja = new Button("Dodaj");
		
		buttonRejestracja.addClickListener(clickEvent -> {
			
			List<TrainCar> trainCars = trainCarRepo.findAll();
			
			waybill.setId(waybillID);
			waybill.setLadunek(textLadunek.getValue());
			waybill.setUwagi(textUwagi.getValue());
			waybill.setWagon(trainCars.stream().filter(c -> 
					textCar.getValue().equals(c.getCar_number())).findAny().orElse(null));
			
			waybill.setSource_station_id(comboTrainStationNad.getValue());
			waybill.setDest_station_id(comboTrainStationOdb.getValue());
			
			if (waybill.getStatus() == TransportStatus.Reject)
			{
				waybill.setStatus(TransportStatus.Report);
			}
						
			waybillRepo.save(waybill);
		    
		    Notification notification = Notification.show("Zmiany zapisane!");
		});
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		menu = new MenuGUI(companyRepo);
		
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
		
		waybill = null;
		
		if(getCompanyEmail().length() > 0)
		{
			Company company = companyRepo.findByEmail(getCompanyEmail()).get();
			for (Waybill w : company.getWaybills())
			{
				if (w.getId() == waybillID)
				{
					waybill = w;
					break;
				}
			}
		}
	    
	    if (waybill != null)
	    {    	
	    	textLadunek.setValue(waybill.getLadunek());
	    	textCar.setValue(waybill.getWagon().getCar_number());
	    	textUwagi.setValue(waybill.getUwagi());
	    	comboTrainStationNad.setValue(waybill.getSource_station_id());
	    	comboTrainStationOdb.setValue(waybill.getDest_station_id());			    
	    }
	    
		add(menu);
		add(info, info1, textLadunek, labelCar, textCar, labelUwagi, textUwagi, info2, comboTrainStationNad,
				info3, comboTrainStationOdb);
		add(buttonRejestracja);
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
