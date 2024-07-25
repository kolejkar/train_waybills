package karol.train_waybill.front;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.TrainCar;
import karol.train_waybill.repository.TrainCarRepository;

@Route("traincar/add")
public class AddTrainCarGUI extends VerticalLayout {

	public AddTrainCarGUI(TrainCarRepository trainCarRepo)
	{
		Label info = new Label("Dodawanie wagonu kolejowego:");
		
		Label info1 = new Label("Numer wagonu:");
		TextField textNumer = new TextField("Numer:");
		Label info2 = new Label("Typ wagonu, np.: Eaos:");
		TextField textTyp = new TextField("Typ:");
		
		Button buttonRejestracja = new Button("Dodaj");
		buttonRejestracja.addClickListener(clickEvent -> {
			
			TrainCar car = new TrainCar();
			car.setCar_number(textNumer.getValue());
			car.setType(textTyp.getValue());
			
			trainCarRepo.save(car);
		    
		    Notification notification = Notification.show("Wagon został dodany.");
		});
		
		add(info, info1, textNumer, info2, textTyp);
		add(buttonRejestracja);
	}
}
