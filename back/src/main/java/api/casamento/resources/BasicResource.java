package api.casamento.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BasicResource {
	private static final Logger log = LoggerFactory.getLogger(BasicResource.class);
	
	@GetMapping("/isAlive")
	public String isAlive() {
		try {
			return "I'm Alive";

		} catch (Exception e) {
			log.error("Erro.", e);
			
		}
		return null;
	}
	
}
