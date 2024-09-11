package karol.train_waybill.front.admin.waybill;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.TrainCar;
import karol.train_waybill.database.TransportStatus;
import karol.train_waybill.database.Waybill;
import karol.train_waybill.front.MenuGUI;
import karol.train_waybill.repository.CompanyRepository;
import karol.train_waybill.repository.WaybillRepository;

@Route("/waybill/detail/:WaybillID")
public class WaybillDetail extends VerticalLayout implements BeforeEnterObserver {

	private Integer waybillID;
	
	private TransportStatus lastStatus;
	
	private Waybill list;
	
	private Paragraph detail;
	
	@Autowired
	WaybillRepository waybillRepo;
	
	WaybillDetail(CompanyRepository companyRepo)
	{
		MenuGUI menu = new MenuGUI(companyRepo);
		add(menu);
		
		Label info = new Label("Informacje o liście przewozowym:");
		
		detail = new Paragraph("*");
		detail.getStyle().set("white-space", "pre-line");
		
		add(info, detail);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// TODO Auto-generated method stub
		
		try 
	    {	
            waybillID = Integer.parseInt(event.getRouteParameters().get("WaybillID").get());
	    }
	    catch (NumberFormatException e) {
		    waybillID = 0;
	    }
		
		Waybill waybill = null;

		waybill = waybillRepo.findById(waybillID).orElse(null);
	    
	    if (waybill != null)
	    {
	    	list = waybill;
	    	
			String text = new String();	
	    	text = text + "\nLadunek: " + waybill.getLadunek();
	    	text = text + "\nWagon: " + waybill.getWagon().getCar_number();
	    	text = text + "\nUwagi: " + waybill.getUwagi();
	    	text = text + "\nStacja nadania: " + waybill.getSource_station_id();
	    	text = text + "\nStacja odbioru: " + waybill.getDest_station_id();
	    	text = text + "\nStatus listu: " + waybill.getStatus();
	    	
	    	detail.setText(text);
	    	
	    	lastStatus = waybill.getStatus();
	    	
	    	Button changeStatus;
			
			if (lastStatus == TransportStatus.Report)
			{
				Label info2 = new Label("Czy zatwierdzić ten list przewozowy?");
				
				changeStatus = new Button("Zatwierdz");
				
				changeStatus.addClickListener(clickEvent -> {
									
				list.setStatus(TransportStatus.Accept);
					
				waybillRepo.save(list);
				    
				Notification notification = Notification.show("Akceptacja listu została poprawnie wykonana!");
				});
				
				Button abort = new Button("Odrzuć");
				
				abort.addClickListener(clickEvent -> {
									
				list.setStatus(TransportStatus.Reject);
					
				waybillRepo.save(list);
				    
				Notification notification = Notification.show("Akceptacja listu została poprawnie wykonana!");
				});
				
				add(info2, changeStatus, abort);
			}
			else
			if (lastStatus == TransportStatus.Accept)
			{			
				changeStatus = new Button("Zamów wagon");
				
				changeStatus.addClickListener(clickEvent -> {
									
				list.setStatus(TransportStatus.InProgress);
					
				waybillRepo.save(list);
				    
				Notification notification = Notification.show("Zamówiono wagon do zamówienia!");
				});
				
				add(changeStatus);
			}
			    
	    }
	}
	
	
}
