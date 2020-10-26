package co.com.mercadolibre.rebell.alliance.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import co.com.mercadolibre.rebell.alliance.dto.request.TopSecretRequestDTO;
import co.com.mercadolibre.rebell.alliance.dto.request.TopSecretSplitRequestDTO;
import co.com.mercadolibre.rebell.alliance.dto.response.TopSecretResponseDTO;
import co.com.mercadolibre.rebell.alliance.service.IMessageDecoderService;
import co.com.mercadolibre.rebell.alliance.service.ITopSecretService;
import co.com.mercadolibre.rebell.alliance.service.ITopSecretSplitService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/")
@Validated
@Api(tags = "Check from the position of a message, and extract the message")
public class TopSecretController {
	
	@Value("${satellites.names}")
	private String acceptedNames;
	
	@Autowired
	private IMessageDecoderService messageDecoderService;
	
	@Autowired
	private ITopSecretService topSecretService;
	
	@Autowired
	private ITopSecretSplitService topSecretSplitService;
	
	Logger logger = LoggerFactory.getLogger(TopSecretController.class);


	@PostMapping(consumes="application/json")
	@RequestMapping("/topsecret")
	public ResponseEntity<Object> analyzeMessage(@Valid @RequestBody TopSecretRequestDTO request){
		logger.info("[Rebell Alliance - TopSecretController] Performing /topsecret request...");

		TopSecretResponseDTO response = topSecretService.identifyMessage(request.getSatellites());
		
		if(response.getPosition() == null || response.getMessage() == null) {
			logger.info("[Rebell Alliance - TopSecretController] No se pudo descifrar la posición o el mensaje");
			return ResponseEntity.notFound().build();
		} 
		logger.info("[Rebell Alliance - TopSecretController] Mensaje analizado con exito - Respuesta", response);
		return new ResponseEntity<Object>(response, HttpStatus.OK);

	}
	
	@PostMapping(consumes="application/json")
	@RequestMapping("/topsecret_split/{satellite_name}")
	public ResponseEntity<Void> saveSplitMessageData(
			@Valid 
			@PathVariable("satellite_name") String satelliteName, @Valid @RequestBody TopSecretSplitRequestDTO request){
		logger.info("[Rebell Alliance - TopSecretController] Performing /topsecret_split/ post request...");
		
		if(!satelliteName.matches("^("+acceptedNames+")$")) {
			logger.info("[Rebell Alliance - TopSecretController] Satellite name is not valid");
			return ResponseEntity.badRequest().build();
		}
		SatelliteInfoDTO satellite = new SatelliteInfoDTO(satelliteName,request.getDistance(), request.getMessage());
		topSecretSplitService.savePartialData(satellite);
		return ResponseEntity.ok().build();

	}
	
	@GetMapping("/topsecret_split")
	public ResponseEntity<Object> analyzeSplitMessage(@Valid @RequestBody TopSecretRequestDTO request){
		logger.info("[Rebell Alliance - TopSecretController] Performing /topsecret_split get request...");

		String decodedMessage = messageDecoderService.decodeMessage(request.getSatellites());
		return new ResponseEntity<Object>(decodedMessage, HttpStatus.OK);

	}
	
	
}
