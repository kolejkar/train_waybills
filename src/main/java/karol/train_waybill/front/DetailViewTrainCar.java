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
import karol.train_waybill.database.TransportStatus;
import karol.train_waybill.database.Waybill;
import karol.train_waybill.repository.CompanyRepository;
import karol.train_waybill.repository.TrainCarRepository;
import karol.train_waybill.repository.WaybillRepository;

@Route("traincar/view/detail/:trainID")
@RouteAlias("traincar/view/detail")
public class DetailViewTrainCar extends VerticalLayout implements BeforeEnterObserver {

	private String trainID;
	
	private Boolean carStatus;
	
	Grid<TrainCar> grid;
	TextField textCarNumber;
	
	Button find;
	
	Button changeStatus;
	
	private Waybill trainCar;
	
	private TransportStatus waybillStatus;
	
	@Autowired
	WaybillRepository waybillRepo;
	
	@Autowired
	TrainCarRepository trainCarRepo;
		
	DetailViewTrainCar(CompanyRepository companyRepo)
	{
		MenuGUI menu = new MenuGUI(companyRepo);
		add(menu);
		
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
	        	grid.removeColumnByKey("waybills");
	        	
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
	        		waybillStatus = trainCar.getStatus();
	        		carStatus = trainCar.getWagon().getEmpty();
	        		
	        		if (waybillStatus == TransportStatus.InProgress && carStatus == false)
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
	        		else
        			if (waybillStatus == TransportStatus.InProgress && carStatus == true)
	        		{
						grid.addColumn(p -> {
							return trainCar.getSource_station_id().getFirma();
						}).setHeader("Firma odbierająca");	
						grid.addColumn(p -> {
							return trainCar.getSource_station_id().getId();
						}).setHeader("Kod stacja docelowej");
						grid.addColumn(p -> {
							return trainCar.getSource_station_id().getNazwa_stacji();
						}).setHeader("Stacja docelowa");	
	        		}
        			else
    				if (waybillStatus == TransportStatus.Delivered)
	        		{
						grid.addColumn(p -> {
							return trainCar.getDest_station_id().getFirma();
						}).setHeader("Firma nadająca");
	        		}
	        		
	        		CarOperations();
	        	}
	        	else
	        	{
	        		grid.addColumn(p -> {
						return "Na stanie";
					}).setHeader("Status");	
	        	}
	        }
		}
		catch(NoSuchElementException e)
		{
		}
	}

	private void CarOperations()
	{
		if (waybillStatus == TransportStatus.InProgress && carStatus == false)
		{
			changeStatus = new Button("Rozładuj wagon na bocznicy");
			
			changeStatus.addClickListener(clickEvent -> {
				
				trainCar.setStatus(TransportStatus.Delivered);				
				TrainCar car = trainCar.getWagon();
				car.setEmpty(true);
				
				trainCarRepo.save(car);			
				waybillRepo.save(trainCar);				
				    
				Notification notification = Notification.show("Wagon został rozładowany na bocznicy!");
			});
			
			add(changeStatus);
		}
		else
		if (waybillStatus == TransportStatus.InProgress && carStatus == true)
		{
			changeStatus = new Button("Załaduj wagon na bocznicy");
			
			changeStatus.addClickListener(clickEvent -> {
								
				TrainCar car = trainCar.getWagon();
				car.setEmpty(false);
				
				trainCarRepo.save(car);
				//waybillRepo.save(trainCar);				
				    
				Notification notification = Notification.show("Wagon został zładowany na bocznicy!");
			});
			
			add(changeStatus);
		}
		if (waybillStatus == TransportStatus.Delivered)
		{
			changeStatus = new Button("Zwolnij wagon");
			
			changeStatus.addClickListener(clickEvent -> {
									
				waybillRepo.deleteById(trainCar.getId());				
				    
				Notification notification = Notification.show("Wagon został zwolniony i czeka na nowe zamówienie!");
				});
			
			add(changeStatus);
		}
	}
}
