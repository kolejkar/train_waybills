package karol.train_waybill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import karol.train_waybill.service.InitService;

@SpringBootApplication
@ComponentScan({"karol.train.controller.WaybillController", 
	"karol.train_waybill.config", "karol.train_waybill.front.admin.users", "karol.train_waybill.service"})
public class TrainWaybillApplication {

	@Autowired
	InitService initService;
	
	public static void main(String[] args) {
		SpringApplication.run(TrainWaybillApplication.class, args);
	}

}
