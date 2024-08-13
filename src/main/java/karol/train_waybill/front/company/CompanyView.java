package karol.train_waybill.front.company;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
//import com.vaadin.flow.spring.security.AuthenticationContext;

import karol.train_waybill.UserDetails.CompanyDetails;
import karol.train_waybill.database.Company;
import karol.train_waybill.database.TrainStation;
import karol.train_waybill.database.Waybill;
import karol.train_waybill.repository.CompanyRepository;
import karol.train_waybill.repository.WaybillRepository;

@Route("company/view")
public class CompanyView extends VerticalLayout implements BeforeEnterObserver {
	
	Grid<Waybill> grid;
	
	Button edit;
	
	Button delete;
	
	@Autowired
	private CompanyRepository companyRepo;
	
	@Autowired
	private WaybillRepository waybillRepo;
	
	public CompanyView()
	{
		Label info = new Label("Listy przewozowe:");		
		
		grid= new Grid<Waybill>(Waybill.class);
						
		edit = new Button("Edit");
				
		delete = new Button("Delete");				
		
		Button add = new Button("Add waybill");
		
		add.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/waybill/add");
		});
		
		add(info, add, edit, delete, grid);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		
		Company company = companyRepo.findByEmail(getCompanyEmail()).get();
		
		grid.setItems(company.getWaybills());
		grid.removeColumnByKey("dest_station_id");
		grid.removeColumnByKey("source_station_id");
		grid.removeColumnByKey("wagon");
		grid.removeColumnByKey("company");
		
		grid.addColumn(p -> {
			return p.getSource_station_id().getFirma();
		}).setHeader("Stacja nadania");
		
		grid.addColumn(p -> {
			return p.getDest_station_id().getFirma();
		}).setHeader("Stacja docelowa");
		
		grid.addColumn(p -> {
			return p.getWagon().getCar_number();
		}).setHeader("Wagon");
		
		SingleSelect<Grid<Waybill>, Waybill> waybillSelect =
		        grid.asSingleSelect();
		
		edit.addClickListener(clickEvent -> {
			Waybill waybill = waybillSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/waybill/edit/" + waybill.getId());
		});
		
		delete.addClickListener(clickEvent -> {
			Waybill waybill = waybillSelect.getValue();
					//grid.getSelectedRow().first();
			waybillRepo.deleteById(waybill.getId());
		});
	}
	
	private String getCompanyEmail()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken))
		{
			CompanyDetails company = (CompanyDetails) authentication.getPrincipal();
			return company.getUsername();
		}
		return "";
	}
}
