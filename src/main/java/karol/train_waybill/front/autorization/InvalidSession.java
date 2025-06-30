package karol.train_waybill.front.autorization;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("InvalidSession")
public class InvalidSession extends VerticalLayout {

    public InvalidSession()
    {
        Label info = new Label("Twoja sesja wygasła. Prosimy się zalogować ponownie.");
        Button login = new Button("Powrót do logowania");
        login.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("login");
		});

        add(info, login);
    }
}
