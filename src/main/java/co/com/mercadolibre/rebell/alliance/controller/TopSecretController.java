package co.com.mercadolibre.rebell.alliance.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.mercadolibre.rebell.alliance.dto.request.TopSecretRequestDTO;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/topsecret")
@Validated
@Api(tags = "Check from the position of a message, and extract the message")
public class TopSecretController {
	
	Logger logger = LoggerFactory.getLogger(TopSecretController.class);


	@PostMapping(consumes="application/json")
	public ResponseEntity<Object> test(@Valid @RequestBody TopSecretRequestDTO request){
		logger.info("[Rebell Alliance - TopSecretController] Performing /topsecret request...");

		return new ResponseEntity<Object>(request, HttpStatus.OK);

	}
}
