package co.com.mercadolibre.rebell.alliance.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import co.com.mercadolibre.rebell.alliance.domain.SatelliteData;
import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import co.com.mercadolibre.rebell.alliance.repository.ISatelliteDataRepository;
import co.com.mercadolibre.rebell.alliance.service.IMessageDecoderService;

@Service
@PropertySource("classpath:message.properties")
public class MessageDecoderServiceImpl implements IMessageDecoderService{
	@Autowired
	private ISatelliteDataRepository satelliteDataRepository;
	
	Logger logger = LoggerFactory.getLogger(MessageDecoderServiceImpl.class);

	@Value("${message.length}")
	private int messageLength;

	@Override
	public String decodeMessage(SatelliteInfoDTO[] messages) {
			
		ArrayList<String> recoveredWords = new ArrayList<String>();
		List<String> distinctWords;
		String decodedMessage = null;
		
		for(SatelliteInfoDTO currentSatelite : messages) {
			
			String[] currentMessage = currentSatelite.getMessage();
			
			if(!isValidMessageLenght(currentMessage)) return null;
			for(String currentWord: currentMessage) {
				if(!currentWord.isEmpty()) {
					recoveredWords.add(currentWord);
				}
			}
		}
		
		distinctWords = recoveredWords.stream().distinct().collect(Collectors.toList());
		
		if(distinctWords.size() != messageLength) return null;
				
		decodedMessage = String.join(" ", distinctWords);
		return decodedMessage;
	}
	
	private boolean isValidMessageLenght(String[] message) {
		return message.length == messageLength;
	}
	

}
