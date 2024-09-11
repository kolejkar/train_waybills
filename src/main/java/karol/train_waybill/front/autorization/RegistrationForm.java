package karol.train_waybill.front.autorization;

import java.util.stream.Stream;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class RegistrationForm extends FormLayout {
	   
	private H3 title;

	private TextField name;

	private EmailField email;

	private PasswordField password;
	private PasswordField passwordConfirm;

	private Span errorMessageField;

	private Button register;


	public RegistrationForm() {
		
		
       title = new H3("Company register");
	   name = new TextField("Company name");
	   email = new EmailField("Company email");
	
	   password = new PasswordField("Password");
	   passwordConfirm = new PasswordField("Confirm password");
	
	   setRequiredIndicatorVisible(name, email, password, passwordConfirm);
	
	   errorMessageField = new Span();
	
	   register = new Button("Register company");
	
	   add(title, name, email);
	   add(password, passwordConfirm, errorMessageField, register);
	   
	   setResponsiveSteps(new ResponsiveStep("0", 1));
	}


	public PasswordField getPasswordField() { return password; }

	public PasswordField getPasswordConfirmField() { return passwordConfirm; }

	public Span getErrorMessageField() { return errorMessageField; }

	public Button getSubmitButton() { return register; }
	
	public TextField getNameField () { return name; }

	private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
		Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
	}
}
