package karol.train_waybill.front.admin.users;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.Company;
import karol.train_waybill.database.ERole;
import karol.train_waybill.database.Role;

import karol.train_waybill.repository.CompanyRepository;

@Route("/users/edit/:CompanyID")
public class EditUser extends VerticalLayout implements BeforeEnterObserver {

	private Integer companyID;
	
	private Set<Role> role;
	
	TextField textEmail;
	TextField textPassword;
	TextField textName;
	ComboBox<Role> comboRola;
	Button buttonRejestracja;
	
	@Autowired
	private CompanyRepository companyRepo;
	
	public EditUser()
	{
		Label info = new Label("Edycja firmy:");
		
		Label labelEmail = new Label("Wprowadz email:");
		textEmail = new TextField("Email:");
		
		Label labelPassword = new Label("Wprowadz hasło:");
		textPassword = new TextField("Hasło:");
		
		Label labelName = new Label("Wprowadz nazwę firmy:");
		textName = new TextField("Nazwa firmy:");

		Label labelRola = new Label("Rola firmy:");		
		comboRola = new ComboBox<Role>("Role:");
				
		Button buttonRejestracja = new Button("Dodaj");
		
		buttonRejestracja.addClickListener(clickEvent -> {
			
			Set<Role> roles =new HashSet();
			roles.add(comboRola.getValue());
			
			Company company = new Company();
			
			company.setId(companyID);
			company.setEmail(textEmail.getValue());
			
			if (textPassword.getValue().length() > 0)
			{
				company.setPassword(BCrypt.hashpw(textPassword.getValue(), BCrypt.gensalt()));
			}
			else
			{
				Company c = companyRepo.findById(companyID).get();
				company.setPassword(c.getPassword());
			}
			
			company.setName(textName.getValue());			
			company.setRole(roles);
			
			companyRepo.save(company);
		    
		    Notification notification = Notification.show("Zmiany zapisane!");
		});
		
		add(info, labelEmail, textEmail, labelPassword, textPassword, labelName, textName, labelRola, comboRola);
		add(buttonRejestracja);
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// TODO Auto-generated method stub
		
		role = new HashSet();
		Role r1 = new Role();
		r1.setName(ERole.ROLE_ADMIN);
		role.add(r1);
		Role r2 = new Role();
		r2.setName(ERole.ROLE_COMPANY);
		role.add(r2);
		Role r3 = new Role();
		r3.setName(ERole.ROLE_RAILWAY);
		role.add(r3);
		
		comboRola.setItems(role);
		comboRola.setItemLabelGenerator(Role::getStringName);
		
		try 
	    {	
            companyID = Integer.parseInt(event.getRouteParameters().get("CompanyID").get());
	    }
	    catch (NumberFormatException e) {
		    companyID = 0;
	    }
		
		Company company = null;
		company = companyRepo.findById(companyID).orElse(null);
	    
	    if (company != null)
	    {
			Set<Role> roles = company.getRole();
			Role rola = roles.iterator().next();
	    	
	    	textEmail.setValue(company.getEmail());
	    	//textPassword.setValue(company.getPassword());
	    	textName.setValue(company.getName());
	    	comboRola.setValue(rola);
			    
	    }
	}
}
