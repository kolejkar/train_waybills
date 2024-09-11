package karol.train_waybill.front.admin;

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
import karol.train_waybill.database.Waybill;
import karol.train_waybill.front.MenuGUI;
import karol.train_waybill.repository.CompanyRepository;
import karol.train_waybill.repository.TrainCarRepository;

@Route("traincar/edit/:carID")
public class EditTrainCarGUI extends VerticalLayout implements BeforeEnterObserver {

	String car_number;
	
	TextField textNumer;
	TextField textTyp;
	TextField textCarrier;
	ComboBox<Boolean> comboEmpty;
	
	@Autowired
	TrainCarRepository trainCarRepo;
	
	EditTrainCarGUI(CompanyRepository companyRepo)
	{
		MenuGUI menu = new MenuGUI(companyRepo);
		add(menu);
		
		Label info = new Label("Dodawanie wagonu kolejowego:");
		
		Label info1 = new Label("Numer wagonu:");
		textNumer = new TextField("Numer:");
		Label info2 = new Label("Typ wagonu, np.: Eaos:");
		textTyp = new TextField("Typ:");
		Label info4 = new Label("Przewoznik, np.: PKP:");
		textCarrier = new TextField("Przewoznik:");
		Label info3 = new Label("Czy nie przewozi ładunku?");
		comboEmpty = new ComboBox<Boolean>();
		
		comboEmpty.setItems(true, false);
		
		Button buttonRejestracja = new Button("Zapisz");
		buttonRejestracja.addClickListener(clickEvent -> {
			
			TrainCar car = new TrainCar();
			car.setCar_number(textNumer.getValue());
			car.setType(textTyp.getValue());
			car.setEmpty(comboEmpty.getValue());
			car.setCarrier(textCarrier.getValue());
			
			trainCarRepo.save(car);
		    
		    Notification notification = Notification.show("Zmiany zostały zapisane.");
		});
		
		add(info, info1, textNumer, info2, textTyp, info4, textCarrier, info3, comboEmpty);
		add(buttonRejestracja);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// TODO Auto-generated method stub
		car_number = event.getRouteParameters().get("carID").get();
		
		TrainCar trainCar = trainCarRepo.findById(car_number).orElse(null);
		
		if (trainCar != null)
		{
			textNumer.setValue(trainCar.getCar_number());
			textTyp.setValue(trainCar.getType());
			if (trainCar.getCarrier() != null)
			{
				textCarrier.setValue(trainCar.getCarrier());
			}
			if (trainCar.getEmpty() != null)
			{
				comboEmpty.setValue(trainCar.getEmpty());
			}
			else
			{
				comboEmpty.setValue(false);
			}
		}
	}

}
