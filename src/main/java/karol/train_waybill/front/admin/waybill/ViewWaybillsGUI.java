package karol.train_waybill.front.admin.waybill;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.Route;

import karol.train_waybill.database.Company;
import karol.train_waybill.database.Waybill;
import karol.train_waybill.front.MenuGUI;
import karol.train_waybill.repository.CompanyRepository;
import karol.train_waybill.repository.WaybillRepository;

@Route("waybill/view")
public class ViewWaybillsGUI extends VerticalLayout {
	
	@Autowired
	public ViewWaybillsGUI(WaybillRepository waybillRepo, CompanyRepository companyRepo)
	{
		MenuGUI menu = new MenuGUI(companyRepo);
		add(menu);
		
		Label info = new Label("Listy przewozowe:");		
		
		Grid<Waybill> grid= new Grid<Waybill>(Waybill.class);
				
		grid.setItems(waybillRepo.findAll());
		grid.removeColumnByKey("dest_station_id");
		grid.removeColumnByKey("source_station_id");
		grid.removeColumnByKey("wagon");
		
		grid.addColumn(p -> {
			return p.getSource_station_id().getFirma();
		}).setHeader("Stacja nadania");
		
		grid.addColumn(p -> {
			return p.getDest_station_id().getFirma();
		}).setHeader("Stacja docelowa");
		
		grid.addColumn(p -> {
			return p.getWagon().getCar_number();
		}).setHeader("Wagon");
		
		SingleSelect<Grid<Waybill>, Waybill> waybillSelect =
		        grid.asSingleSelect();
		
		Button edit = new Button("Edit");
		
		edit.addClickListener(clickEvent -> {
			Waybill waybill = waybillSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/waybill/edit/" + waybill.getId());
		});
		
		Button delete = new Button("Delete");
		
		delete.addClickListener(clickEvent -> {
			Waybill waybill = waybillSelect.getValue();
					//grid.getSelectedRow().first();
			waybillRepo.deleteById(waybill.getId());
		});
		
		Button detail = new Button("Detail >>");
		
		detail.addClickListener(clickEvent -> {
			Waybill waybill = waybillSelect.getValue();
					//grid.getSelectedRow().first();
			UI.getCurrent().getPage().setLocation("/waybill/detail/" + waybill.getId());
		});
				
		
		Button add = new Button("Add waybill");
		
		add.addClickListener(clickEvent -> {
			UI.getCurrent().getPage().setLocation("/waybill/add");
		});
		
		add(info, add, edit, delete, detail, grid);
	}
}
