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
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.Company;
import karol.train_waybill.database.ERole;
import karol.train_waybill.database.Role;
import karol.train_waybill.front.MenuGUI;
import karol.train_waybill.repository.CompanyRepository;

@Route("/users/addEmployee")
public class AddEmployee extends VerticalLayout{

	private Set<Role> role;
	
	TextField textEmail;
	TextField textPassword;
	Button buttonRejestracja;
	
	@Autowired
	private CompanyRepository companyRepo;
	
	public AddEmployee(CompanyRepository companyR)
	{
		MenuGUI menu = new MenuGUI(companyR);
		add(menu);
		
		Label info = new Label("Dodanie pracownika:");
		
		Label labelEmail = new Label("Wprowadz email:");
		textEmail = new TextField("Email:");
		
		Label labelPassword = new Label("Wprowadz hasło:");
		textPassword = new TextField("Hasło:");
						
		Button buttonRejestracja = new Button("Dodaj");
		
		buttonRejestracja.addClickListener(clickEvent -> {
			Role role = new Role();
			role.setName(ERole.ROLE_RAILWAY);
			
			Set<Role> roles =new HashSet();
			roles.add(role);
			
			Company company = new Company();
			
			company.setName("Train Cargo");
			company.setEmail(textEmail.getValue());
			
			if (textPassword.getValue().length() > 0)
			{
				company.setPassword(BCrypt.hashpw(textPassword.getValue(), BCrypt.gensalt()));
			}		
			company.setRole(roles);
			
			companyRepo.save(company);
		    
		    Notification notification = Notification.show("Pracownik został utworzony!");
		});
		
		add(info, labelEmail, textEmail, labelPassword, textPassword);
		add(buttonRejestracja);
	}
}
