package karol.train_waybill.front.autorization;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import karol.train_waybill.repository.CompanyRepository;

@Route("register")
public class RegistrationView extends VerticalLayout {

	public RegistrationView(CompanyRepository companyRepo) {
		RegistrationForm registrationForm = new RegistrationForm();
		//setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);
		
		add(registrationForm);

		RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm, companyRepo);
		registrationFormBinder.addBindingAndValidation();
	}
}
