package karol.train_waybill.front;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.TrainStation;
import karol.train_waybill.repository.TrainStationRepository;

@Route("station/view")
public class ViewStationGUI extends VerticalLayout {

	public ViewStationGUI(TrainStationRepository stationRepo)
	{
		Label info = new Label("Projects lists:");		
		
		Grid<TrainStation> grid= new Grid<TrainStation>(TrainStation.class);
		
		SingleSelect<Grid<TrainStation>, TrainStation> stationSelect =
		        grid.asSingleSelect();
		
		Button edit = new Button("Edit");
		
		edit.addClickListener(clickEvent -> {
			TrainStation station = stationSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/station/edit/" + station.getId());
		});
		
		Button delete = new Button("Delete");
		
		edit.addClickListener(clickEvent -> {
			TrainStation station = stationSelect.getValue();
					//grid.getSelectedRow().first();
			stationRepo.deleteById(station.getId());
		});
				
		
		Button add = new Button("Add station");
		
		add.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/station/add");
		});
		
		grid.setItems(stationRepo.findAll());
				
		add(info, add, edit, delete, grid);
	}
}
