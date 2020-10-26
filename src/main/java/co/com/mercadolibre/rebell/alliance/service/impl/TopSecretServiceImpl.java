package co.com.mercadolibre.rebell.alliance.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.mercadolibre.rebell.alliance.dto.PositionDTO;
import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import co.com.mercadolibre.rebell.alliance.dto.response.TopSecretResponseDTO;
import co.com.mercadolibre.rebell.alliance.service.ILocationService;
import co.com.mercadolibre.rebell.alliance.service.IMessageDecoderService;
import co.com.mercadolibre.rebell.alliance.service.ITopSecretService;

@Service
public class TopSecretServiceImpl implements ITopSecretService{
	
	@Autowired
	private ILocationService locationService;
	
	@Autowired
	private IMessageDecoderService messageDecoderService;

	Logger logger = LoggerFactory.getLogger(TopSecretServiceImpl.class);

	
	@Override
	public TopSecretResponseDTO identifyMessage(SatelliteInfoDTO[] satellites) {


		PositionDTO position = locationService.getPosition(satellites);
		String messageDecoded = messageDecoderService.decodeMessage(satellites);
		
		TopSecretResponseDTO response = new TopSecretResponseDTO();
		response.setPosition(position); 
		response.setMessage(messageDecoded);
		return response;
	}



}
