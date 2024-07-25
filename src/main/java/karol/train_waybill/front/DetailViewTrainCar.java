package karol.train_waybill.front;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import karol.train_waybill.database.TrainCar;
import karol.train_waybill.database.Waybill;
import karol.train_waybill.repository.TrainCarRepository;
import karol.train_waybill.repository.WaybillRepository;

@Route("traincar/view/detail/:trainID")
@RouteAlias("traincar/view/detail")
public class DetailViewTrainCar extends VerticalLayout implements BeforeEnterObserver {

	private String trainID;
	
	Grid<TrainCar> grid;
	TextField textCarNumber;
	
	Button find;
	
	Waybill trainCar;
	
	@Autowired
	WaybillRepository waybillRepo;
	
	@Autowired
	TrainCarRepository trainCarRepo;
	
	DetailViewTrainCar()
	{
		Label info = new Label("Wyszukiwarka wagonów kolejowych:");
		
		textCarNumber = new TextField("Numer wagonu: ");
		
		grid= new Grid<TrainCar>(TrainCar.class);
		
		find = new Button("Szukaj");
		
		find.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("traincar/view/detail/" + textCarNumber.getValue());
		});
				
		add(info, textCarNumber, find, grid);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// TODO Auto-generated method stub
		try
		{
			trainID = event.getRouteParameters().get("trainID").get();
        
	        if (trainCarRepo.findById(trainID) != null)
	        {
	        	grid.setItems(trainCarRepo.findById(trainID).get());
	        	
	        	trainCar = null;
	        	for (Waybill waybill : waybillRepo.findAll())
	        	{
	        		if (waybill.getWagon().getCar_number().equals(trainID))
	        		{
	        		trainCar = waybill;
	        		}
	        	}
	        
	        	if (trainCar != null)
	        	{		
					grid.addColumn(p -> {
						return trainCar.getSource_station_id().getFirma();
					}).setHeader("Firma nadająca");	
					grid.addColumn(p -> {
						return trainCar.getDest_station_id().getId();
					}).setHeader("Kod stacja docelowej");
					grid.addColumn(p -> {
						return trainCar.getDest_station_id().getNazwa_stacji();
					}).setHeader("Stacja docelowa");	
					grid.addColumn(p -> {
						return trainCar.getDest_station_id().getFirma();
					}).setHeader("Firma odbierająca");	
					grid.addColumn(p -> {
						return trainCar.getLadunek();
					}).setHeader("Ladunek wagonu");	
	        	}
	        }
		}
		catch(NoSuchElementException e)
		{
		}
	}

}
