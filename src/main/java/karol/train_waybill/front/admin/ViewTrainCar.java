package karol.train_waybill.front.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.TrainCar;
import karol.train_waybill.database.Waybill;
import karol.train_waybill.front.MenuGUI;
import karol.train_waybill.repository.CompanyRepository;
import karol.train_waybill.repository.TrainCarRepository;

@Route("traincar/view")
public class ViewTrainCar extends VerticalLayout {
	
	public ViewTrainCar(TrainCarRepository trainCarRepo, CompanyRepository companyRepo) {
		
		MenuGUI menu = new MenuGUI(companyRepo);
		add(menu);
		
		Label info = new Label("Lista wagonów kolejowych:");		
		
		Grid<TrainCar> grid= new Grid<TrainCar>(TrainCar.class);
		grid.setItems(trainCarRepo.findAll());
		grid.removeColumnByKey("waybills");
		
		SingleSelect<Grid<TrainCar>, TrainCar> carSelect =
		        grid.asSingleSelect();
		
		Button edit = new Button("Edit");
		
		edit.addClickListener(clickEvent -> {
			TrainCar trainCar = carSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/traincar/edit/" + trainCar.getCar_number());
		});
		
		Button delete = new Button("Delete");
		
		delete.addClickListener(clickEvent -> {
			TrainCar trainCar = carSelect.getValue();
					//grid.getSelectedRow().first();
			trainCarRepo.deleteById(trainCar.getCar_number());
		});
		
		Button add = new Button("Add train car");
		
		add.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/traincar/add");
		});
		
		add(info, add, edit, delete, grid);
	}

}
