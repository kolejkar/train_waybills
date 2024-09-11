package karol.train_waybill.front.autorization;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import karol.train_waybill.front.MenuGUI;
import karol.train_waybill.repository.CompanyRepository;

@Route("register")
public class RegistrationView extends VerticalLayout {

	public RegistrationView(CompanyRepository companyRepo) {
		
		MenuGUI menu = new MenuGUI(companyRepo);
		add(menu);		
		
		RegistrationForm registrationForm = new RegistrationForm();
		this.setAlignItems(FlexComponent.Alignment.CENTER);
		
		add(registrationForm);

		RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm, companyRepo);
		registrationFormBinder.addBindingAndValidation();
	}
}
