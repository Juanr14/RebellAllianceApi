package co.com.mercadolibre.rebell.alliance.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.com.mercadolibre.rebell.alliance.domain.SatelliteData;
import co.com.mercadolibre.rebell.alliance.dto.PositionDTO;
import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import co.com.mercadolibre.rebell.alliance.dto.response.TopSecretSplitResponseDTO;
import co.com.mercadolibre.rebell.alliance.repository.ISatelliteDataRepository;
import co.com.mercadolibre.rebell.alliance.service.ILocationService;
import co.com.mercadolibre.rebell.alliance.service.IMessageDecoderService;
import co.com.mercadolibre.rebell.alliance.service.ITopSecretSplitService;

@Service
public class TopSecretSplitServiceImpl implements ITopSecretSplitService{
	
	@Value("${satellites.number}")
	private int satellitesNumber;

	@Autowired
	private ILocationService locationService;
	
	@Autowired
	private IMessageDecoderService messageDecoderService;
	
	@Autowired
	private ISatelliteDataRepository satelliteDataRepository;

	Logger logger = LoggerFactory.getLogger(TopSecretSplitServiceImpl.class);

	
	@Override
	public TopSecretSplitResponseDTO identifyMessage() {
		
		TopSecretSplitResponseDTO response = new TopSecretSplitResponseDTO();
		List<SatelliteData> satellitesSaved = satelliteDataRepository.findAll();
		
		if(satellitesSaved.size() == satellitesNumber) {
			logger.info("[Rebell Alliance - TopSecretSplitServiceImpl] Informacion completa realizando identificacion: "+satellitesSaved.toString());
			List<SatelliteInfoDTO> mappedSatellites = satellitesSaved.stream()
					.map(currentSatellite -> transformData(currentSatellite))
			        .collect(Collectors.toList()); 
			logger.info("[Rebell Alliance - TopSecretSplitServiceImpl] Informacion mapeada lista para procesar: "+mappedSatellites.toString());

			SatelliteInfoDTO[] satellitesArray = mappedSatellites.toArray(new SatelliteInfoDTO[0]);
			PositionDTO position = locationService.getPosition(mappedSatellites.toArray(satellitesArray));
			String messageDecoded = messageDecoderService.decodeMessage(mappedSatellites.toArray(satellitesArray));
			
			logger.info("[Rebell Alliance - TopSecretSplitServiceImpl] Response enviada al controller: "+response.toString());
			response.setPosition(position); 
			response.setMessage(messageDecoded);
			return response;
		}
		logger.info("[Rebell Alliance - TopSecretSplitServiceImpl] No hay informacion suficiente para identificar el mensaje: "+satellitesSaved.toString());
		return response;
			
	}

	@Override
	public void savePartialData(SatelliteInfoDTO satellite) {
		logger.info("[Rebell Alliance - TopSecretSplitServiceImpl] Tratando de guardar la data del satelite: ", satellite);
		
		SatelliteData satelliteToSave = new SatelliteData();
		
		satelliteToSave.setName(satellite.getName());
		satelliteToSave.setDistance(satellite.getDistance());
		
		List<String> words = satellite.getMessage();
		satelliteToSave.setMessage(String.join(",", words));
		logger.info("[Rebell Alliance - TopSecretSplitServiceImpl] Data Mapeada para guardar: "+ satelliteToSave.toString());
		
		satelliteDataRepository.save(satelliteToSave);
		
		logger.info("[Rebell Alliance - TopSecretSplitServiceImpl] Data guardada correctamente ");

						
	}
	
	private SatelliteInfoDTO transformData(SatelliteData satelliteToTransform) {
		
		SatelliteInfoDTO transformedSatellite = new SatelliteInfoDTO();
		
		transformedSatellite.setName(satelliteToTransform.getName());
		transformedSatellite.setDistance(satelliteToTransform.getDistance());
		
		List<String> messageParsed = 
				Stream.of(satelliteToTransform.getMessage().split(",",-1))
				  .collect(Collectors.toList());
		
		transformedSatellite.setMessage(messageParsed);
		
		return transformedSatellite;
	}
}
