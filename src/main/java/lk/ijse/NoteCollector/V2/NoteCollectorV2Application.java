package lk.ijse.NoteCollector.V2;

import ch.qos.logback.core.model.Model;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NoteCollectorV2Application {

	public static void main(String[] args) {
		SpringApplication.run(NoteCollectorV2Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


}
