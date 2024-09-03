package karol.train_waybill.front;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

import karol.train_waybill.UserDetails.CompanyDetails;
import karol.train_waybill.database.Company;
import karol.train_waybill.repository.CompanyRepository;

public class MenuGUI extends HorizontalLayout implements AfterNavigationObserver {

	private CompanyRepository companyRepo;
	
	Button mainMenu;
	
	public MenuGUI(CompanyRepository companyRepo) 
	{
		this.companyRepo = companyRepo;
		setJustifyContentMode(JustifyContentMode.END);
		
		mainMenu = new Button("Menu główne");		
		mainMenu.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/");
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

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		String email = getCompanyEmail();
		if (email != null && email != "")
		{
			Company company = companyRepo.findByEmail(email).get();
			Label user = new Label("Panel " + company.getName()); 
			Button logout = new Button("Wyloguj");
			
			logout.addClickListener(clickEvent -> {
				UI.getCurrent().getPage().setLocation("logout");
			});
			
			add(mainMenu, user, logout);
		}
		else
		{
			Button login = new Button("Zaloguj");
			
			login.addClickListener(clickEvent -> {
				UI.getCurrent().getPage().setLocation("login");
			});
			
			add(mainMenu, login);
		}
		
	}
}
