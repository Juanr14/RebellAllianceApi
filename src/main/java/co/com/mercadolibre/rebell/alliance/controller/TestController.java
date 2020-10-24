package co.com.mercadolibre.rebell.alliance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/test")
@Api(tags = "Demo rest controller")
public class TestController {

	Logger logger = LoggerFactory.getLogger(TestController.class);

	@PostMapping(consumes="application/json")
	public ResponseEntity<Object> test(){
		logger.debug("[Rebell Alliance - TestController] Performing /test request...");

		return new ResponseEntity<Object>("<p>Hola Colega</p>", HttpStatus.OK);

	}
}
