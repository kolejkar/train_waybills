package karol.train_waybill.front.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import karol.train_waybill.front.MenuGUI;
import karol.train_waybill.repository.CompanyRepository;

@Route("admin")
public class AdminView extends VerticalLayout{

	AdminView(CompanyRepository companyRepo)
	{
		MenuGUI menu = new MenuGUI(companyRepo);
		add(menu);
		
		Label info = new Label("Witamy w panelu administracyjnym!");
		
		Button viewCar = new Button("Zarządanie wagonami towarowymi");
		viewCar.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/traincar/view");
		});
		
		Button viewStation = new Button("Zarządanie bocznicami");
		viewStation.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/station/view");
		});
		
		Button viewWaybill = new Button("Zarządanie listami przewozowymi");
		viewWaybill.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/waybill/view");
		});
		
		Button viewUsers = new Button("Zarządanie firmami i pracownikami");
		viewUsers.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/users/view");
		});
		
		add(info, viewCar, viewStation, viewWaybill, viewUsers);
	}
}
