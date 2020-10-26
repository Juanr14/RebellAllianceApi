package co.com.mercadolibre.rebell.alliance.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.mercadolibre.rebell.alliance.domain.SatelliteData;
import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import co.com.mercadolibre.rebell.alliance.dto.response.TopSecretSplitResponseDTO;
import co.com.mercadolibre.rebell.alliance.repository.ISatelliteDataRepository;
import co.com.mercadolibre.rebell.alliance.service.ILocationService;
import co.com.mercadolibre.rebell.alliance.service.IMessageDecoderService;
import co.com.mercadolibre.rebell.alliance.service.ITopSecretSplitService;

@Service
public class TopSecretSplitServiceImpl implements ITopSecretSplitService{

	@Autowired
	private ILocationService locationService;
	
	@Autowired
	private IMessageDecoderService messageDecoderService;
	
	@Autowired
	private ISatelliteDataRepository satelliteDataRepository;

	Logger logger = LoggerFactory.getLogger(TopSecretSplitServiceImpl.class);

	
	@Override
	public TopSecretSplitResponseDTO identifyMessage() {
		// TODO Auto-generated method stub
		return null;
		
		
	}

	@Override
	public void savePartialData(SatelliteInfoDTO satellite) {
		logger.info("[Rebell Alliance - TopSecretSplitServiceImpl] Tratando de guardar la data del satelite: ", satellite);
		
		SatelliteData satelliteToSave = new SatelliteData();
		
		satelliteToSave.setName(satellite.getName());
		satelliteToSave.setDistance(satellite.getDistance());
		
		String[] words = satellite.getMessage();
		satelliteToSave.setMessage(String.join(",", words));
		logger.info("[Rebell Alliance - TopSecretSplitServiceImpl] Data Mapeada para guardar: ", satelliteToSave);
		
		satelliteDataRepository.save(satelliteToSave);
		
		logger.info("[Rebell Alliance - TopSecretSplitServiceImpl] Data guardada correctamente ");

						
	}
}
