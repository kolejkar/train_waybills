package karol.train_waybill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"karol.train.controller.WaybillController", "karol.train_waybill.config"})
public class TrainWaybillApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainWaybillApplication.class, args);
	}

}
