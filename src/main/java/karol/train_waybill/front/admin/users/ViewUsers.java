package karol.train_waybill.front.admin.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
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

import karol.train_waybill.UserDetails.CompanyDetails;
import karol.train_waybill.database.Company;
import karol.train_waybill.database.Waybill;
import karol.train_waybill.repository.CompanyRepository;
import karol.train_waybill.repository.WaybillRepository;

@Route("users/view")
public class ViewUsers extends VerticalLayout implements BeforeEnterObserver {

	Grid<Company> grid;
	
	Button edit;
	
	Button delete;
	
	@Autowired
	private CompanyRepository companyRepo;
		
	public ViewUsers()
	{
		Label info = new Label("Company view:");		
		
		grid= new Grid<Company>(Company.class);
						
		edit = new Button("Edit");
				
		delete = new Button("Delete");				
				
		add(info, edit, delete, grid);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		
		List<Company> company = companyRepo.findAll();
		
		grid.setItems(company);
		
		SingleSelect<Grid<Company>, Company> companySelect =
		        grid.asSingleSelect();
		
		edit.addClickListener(clickEvent -> {
			Company firma = companySelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/users/edit/" + firma.getId());
		});
				
		delete.addClickListener(clickEvent -> {
			Company firma = companySelect.getValue();
					//grid.getSelectedRow().first();
			companyRepo.deleteById(firma.getId());
		});
	}
}
