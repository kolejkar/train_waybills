package karol.train_waybill.front.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.TrainCar;
import karol.train_waybill.front.MenuGUI;
import karol.train_waybill.repository.CompanyRepository;
import karol.train_waybill.repository.TrainCarRepository;

@Route("traincar/add")
public class AddTrainCarGUI extends VerticalLayout {

	public AddTrainCarGUI(TrainCarRepository trainCarRepo, CompanyRepository companyRepo)
	{
		MenuGUI menu = new MenuGUI(companyRepo);
		add(menu);
		
		Label info = new Label("Dodawanie wagonu kolejowego:");
		
		Label info1 = new Label("Numer wagonu:");
		TextField textNumer = new TextField("Numer:");
		Label info2 = new Label("Typ wagonu, np.: Eaos:");
		TextField textTyp = new TextField("Typ:");
		Label info4 = new Label("Przewoznik, np.: PKP:");
		TextField textCarrier = new TextField("Przewoznik:");
		
		Button buttonRejestracja = new Button("Dodaj");
		buttonRejestracja.addClickListener(clickEvent -> {
			
			TrainCar car = new TrainCar();
			car.setCar_number(textNumer.getValue());
			car.setType(textTyp.getValue());
			car.setEmpty(true);
			car.setCarrier(textCarrier.getValue());
			
			trainCarRepo.save(car);
		    
		    Notification notification = Notification.show("Wagon został dodany.");
		});
		
		add(info, info1, textNumer, info2, info4, textCarrier, textTyp);
		add(buttonRejestracja);
	}
}
