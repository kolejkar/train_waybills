package karol.train_waybill.front.autorization;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;

import karol.train_waybill.database.Company;
import karol.train_waybill.database.ERole;
import karol.train_waybill.database.Role;
import karol.train_waybill.repository.CompanyRepository;

public class RegistrationFormBinder {
	
	private CompanyRepository companyRepo;
	
	private RegistrationForm registrationForm;

	private boolean enablePasswordValidation;

	public RegistrationFormBinder(RegistrationForm registrationForm, CompanyRepository companyRepo) {
       this.registrationForm = registrationForm;
       this.companyRepo = companyRepo;
	}

	public void addBindingAndValidation() {
		BeanValidationBinder<Company> binder = new BeanValidationBinder<>(Company.class);
		binder.bindInstanceFields(registrationForm);
		
		binder.forField(registrationForm.getNameField()).asRequired("Every company must have a name").bind(Company::getName, Company::setName);

		binder.forField(registrationForm.getPasswordField()).withValidator(this::passwordValidator).bind("password");
		registrationForm.getPasswordConfirmField().addValueChangeListener(e -> {
			enablePasswordValidation = true;

			binder.validate();
		});
		
		binder.setStatusLabel(registrationForm.getErrorMessageField());
		registrationForm.getSubmitButton().addClickListener(event -> {
			try {
				Role role = new Role();
				role.setName(ERole.ROLE_COMPANY);				
				
				Set<Role> roles =new HashSet();
				roles.add(role);
				Company company = new Company();
				binder.writeBean(company);
				company.setRole(roles);
				company.setPassword(BCrypt.hashpw(company.getPassword(), BCrypt.gensalt()));
				companyRepo.save(company);
				
				showSuccess(company);
			} catch (ValidationException exception) {

			}
		});
	}

	private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
		if (pass1 == null || pass1.length() < 8) {
			return ValidationResult.error("Password should be at least 8 characters long");
		}

		if (!enablePasswordValidation) {
			enablePasswordValidation = true;
			return ValidationResult.ok();
		}
		
		String pass2 = registrationForm.getPasswordConfirmField().getValue();

		if (pass1 != null && pass1.equals(pass2)) {
			return ValidationResult.ok();
		}

		return ValidationResult.error("Passwords do not match");
	}

	private void showSuccess(Company company) {
		Notification notification = Notification.show("Sucesfully registred company " + company.getName());
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

	}
}
