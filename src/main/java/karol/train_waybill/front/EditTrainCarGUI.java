package karol.train_waybill.front;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.TrainCar;
import karol.train_waybill.database.Waybill;
import karol.train_waybill.repository.TrainCarRepository;

@Route("traincar/edit/:carID")
public class EditTrainCarGUI extends VerticalLayout implements BeforeEnterObserver {

	String car_number;
	
	TextField textNumer;
	TextField textTyp;
	
	@Autowired
	TrainCarRepository trainCarRepo;
	
	EditTrainCarGUI()
	{
		Label info = new Label("Dodawanie wagonu kolejowego:");
		
		Label info1 = new Label("Numer wagonu:");
		textNumer = new TextField("Numer:");
		Label info2 = new Label("Typ wagonu, np.: Eaos:");
		textTyp = new TextField("Typ:");
		
		Button buttonRejestracja = new Button("Zapisz");
		buttonRejestracja.addClickListener(clickEvent -> {
			
			TrainCar car = new TrainCar();
			car.setCar_number(textNumer.getValue());
			car.setType(textTyp.getValue());
			
			trainCarRepo.save(car);
		    
		    Notification notification = Notification.show("Zmiany zostały zapisane.");
		});
		
		add(info, info1, textNumer, info2, textTyp);
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
		}
	}

}
