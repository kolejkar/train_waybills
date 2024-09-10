package karol.train_waybill.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.Company;
import karol.train_waybill.repository.CompanyRepository;

@Route("")
public class MainGUI extends VerticalLayout implements BeforeEnterObserver {

	@Autowired
	private CompanyRepository companyRepo;
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		CheckRoles();		
	}
		
	private void CheckRoles()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMPANY")))
		{
			UI.getCurrent().getPage().setLocation("/company/view");
		}
		else
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_RAILWAY")))
		{
			UI.getCurrent().getPage().setLocation("/traincar/view/detail");
		}
		else
		if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
		{
			UI.getCurrent().getPage().setLocation("/admin");
		}
		else
		{
			MainPage();
		}
	}
	
	private void MainPage()
	{
		MenuGUI menu = new MenuGUI(companyRepo);
		Label info = new Label("Witamy na stronie!");
		
		Button addCompany = new Button("Rejestracja firmy");		
		addCompany.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/register");
		});
		
		add(menu, info, addCompany);
	}
}
