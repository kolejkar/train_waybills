package karol.train_waybill.front;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/SessionLimit")
public class SessionLimit extends VerticalLayout {
    
    public SessionLimit()
    {
        Label info = new Label("Osiągnięto limit sesji.");
        Button main = new Button("Strona główna");
        main.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/");
		});

        add(info, main);
    }

}
