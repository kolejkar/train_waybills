package karol.train_waybill.front.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.TrainStation;
import karol.train_waybill.repository.TrainStationRepository;

@Route("station/add")
public class AddStationGUI extends VerticalLayout {
	
	public AddStationGUI(TrainStationRepository stationRepo)
	{
		Label info = new Label("Dodawanie bocznicy kolejowej:");
		
		Label info1 = new Label("Nazwa firmy obsługującej bocznicę:");
		TextField textFirma = new TextField("Firma:");
		Label info2 = new Label("Nazwa najbliszej stacji:");
		TextField textStacja = new TextField("Stacja:");
		Label info3 = new Label("Kod najbliszej stacji:");
		TextField textKod = new TextField("Kod stacji:");
		
		Button buttonRejestracja = new Button("Dodaj");
		buttonRejestracja.addClickListener(clickEvent -> {
			
			TrainStation station = new TrainStation();
			station.setId(textKod.getValue());
			station.setFirma(textFirma.getValue());
			station.setNazwa_stacji(textStacja.getValue());
			
			stationRepo.save(station);
		    
		    Notification notification = Notification.show("Bocznica została dodana.");
		});
		
		add(info, info1, textFirma, info2, textStacja, info3, textKod);
		add(buttonRejestracja);
	}

}
