package ji.hs.fire;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JihsFireApplication {

	public static void main(String[] args) {
		System.setProperty("logging.file.name", ":/media/sdc/log/jihs_fire_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".log");
		SpringApplication.run(JihsFireApplication.class, args);
	}

}
