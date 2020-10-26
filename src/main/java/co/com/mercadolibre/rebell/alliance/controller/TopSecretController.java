package co.com.mercadolibre.rebell.alliance.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.mercadolibre.rebell.alliance.dto.request.TopSecretRequestDTO;
import co.com.mercadolibre.rebell.alliance.service.IMessageDecoderService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/")
@Validated
@Api(tags = "Check from the position of a message, and extract the message")
public class TopSecretController {
	
	@Autowired
	private IMessageDecoderService messageDecoderService;
	
	Logger logger = LoggerFactory.getLogger(TopSecretController.class);


	@PostMapping(consumes="application/json")
	@RequestMapping("/topsecret")
	public ResponseEntity<Object> analyzeMessage(@Valid @RequestBody TopSecretRequestDTO request){
		logger.info("[Rebell Alliance - TopSecretController] Performing /topsecret request...");

		String decodedMessage = messageDecoderService.decodeMessage(request.getSatellites());
		return new ResponseEntity<Object>(decodedMessage, HttpStatus.OK);

	}
	
	@PostMapping(consumes="application/json")
	@RequestMapping("/topsecret_split/{satellite_name}")
	public ResponseEntity<Object> saveSplitMessageData(@PathVariable("satellite_name") String sattelliteName, @Valid @RequestBody TopSecretRequestDTO request){
		logger.info("[Rebell Alliance - TopSecretController] Performing /topsecret request...");

		// String decodedMessage = messageDecoderService.decodeMessage(request.getSatellites());
		return new ResponseEntity<Object>(sattelliteName, HttpStatus.OK);

	}
	
	@GetMapping(consumes="application/json")
	@RequestMapping("/topsecret_split")
	public ResponseEntity<Object> analyzeSplitMessage(@Valid @RequestBody TopSecretRequestDTO request){
		logger.info("[Rebell Alliance - TopSecretController] Performing /topsecret request...");

		String decodedMessage = messageDecoderService.decodeMessage(request.getSatellites());
		return new ResponseEntity<Object>(decodedMessage, HttpStatus.OK);

	}
	
	
}
