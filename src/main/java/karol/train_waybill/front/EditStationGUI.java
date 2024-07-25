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

import karol.train_waybill.database.TrainStation;
import karol.train_waybill.repository.TrainStationRepository;

@Route("station/edit/:stationID")
public class EditStationGUI extends VerticalLayout implements BeforeEnterObserver {

	private String stationID;
	
	TextField textFirma;
	TextField textStacja;
	TextField textKod;
	Button buttonSave;
	
	@Autowired
	TrainStationRepository stationRepo;
	
	EditStationGUI()
	{
		Label info = new Label("Dodawanie bocznicy kolejowej:");
		
		Label info1 = new Label("Nazwa firmy obsługującej bocznicę:");
		textFirma = new TextField("Firma:");
		Label info2 = new Label("Nazwa najbliszej stacji:");
		textStacja = new TextField("Stacja:");
		Label info3 = new Label("Kod najbliszej stacji:");
		textKod = new TextField("Kod stacji:");
		
		buttonSave = new Button("Zapisz");
		
		add(info, info1, textFirma, info2, textStacja, info3, textKod);
		add(buttonSave);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// TODO Auto-generated method stub
        stationID = event.getRouteParameters().get("stationID").get();
        
	    TrainStation station = stationRepo.findById(stationID).get();
	    
	    if (station != null)
	    {
				
			textKod.setValue(station.getId());
			textFirma.setValue(station.getFirma());
			textStacja.setValue(station.getNazwa_stacji());
			
			buttonSave.addClickListener(clickEvent -> {
				
				TrainStation station1 = new TrainStation();
				station1.setId(textKod.getValue());
				station1.setFirma(textFirma.getValue());
				station1.setNazwa_stacji(textStacja.getValue());
				
				stationRepo.save(station1);
			    
			    Notification notification = Notification.show("Bocznica została zaktualizowana.");
			});
		}
	}

}
