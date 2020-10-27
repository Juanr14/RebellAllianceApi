package co.com.mercadolibre.rebell.alliance.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import co.com.mercadolibre.rebell.alliance.repository.ISatelliteDataRepository;
import co.com.mercadolibre.rebell.alliance.service.IMessageDecoderService;

@Service
public class MessageDecoderServiceImpl implements IMessageDecoderService{
	
	Logger logger = LoggerFactory.getLogger(MessageDecoderServiceImpl.class);


	@Override
	public String decodeMessage(SatelliteInfoDTO[] satellites) {
			
		int minimumMessageLenght = findMinimumLength(satellites);
		
		if(minimumMessageLenght == 0) {
			logger.info("[Rebell Alliance - MessageDecoderServiceImpl] Some of the messages does not contains data");
			return null;
		}
		
		ArrayList<List<String>> fixedMessages = messagesWithoutGap(minimumMessageLenght, satellites);
		logger.info("[Rebell Alliance - MessageDecoderServiceImpl] Fixed messages without gap: "+ fixedMessages.toString());


		String[] combinedMessage = combineMessages(minimumMessageLenght, fixedMessages);
		logger.info("[Rebell Alliance - MessageDecoderServiceImpl] Combined message: "+ combinedMessage.toString());
		
		for(String word: combinedMessage) {
			if(word == null) {
				logger.info("[Rebell Alliance - MessageDecoderServiceImpl] Error missing part of the message was detected");
				return null;
			}
		}
		
		String decodedMessage = String.join(" ", combinedMessage);
		logger.info("[Rebell Alliance - MessageDecoderServiceImpl] Message detected: "+ decodedMessage);

		return decodedMessage;
	}
	
	public int findMinimumLength(SatelliteInfoDTO[] satellites) {
		
		ArrayList<Integer> lenghtsList = new ArrayList<>();
		
		for(SatelliteInfoDTO currentSatelite : satellites) {
			lenghtsList.add(currentSatelite.getMessage().size());
		}
		
		
		return Collections.min(lenghtsList);
	}
	
	public ArrayList<List<String>> messagesWithoutGap(int minimumLength, SatelliteInfoDTO[] satellites){
		
		ArrayList<List<String>> fixedMessages = new ArrayList<>();
		for(SatelliteInfoDTO currentSatelite : satellites) {
			List<String> currentMessage = currentSatelite.getMessage();
			int currentLength = currentMessage.size();
			int difference = currentLength - minimumLength;
			// Removes the gap based on the size of the smaller message
			fixedMessages.add(currentMessage.subList(difference, currentLength));
		}
		
		return fixedMessages;
	} 
	
	public String[] combineMessages(int minimumLength, ArrayList<List<String>> fixedMessages) {
		
		String[] combinedMessage = new String[minimumLength];
		// Itereate over every word on all messages and extract the non-empty parts
		for (int wordIndex = 0; wordIndex < minimumLength; wordIndex++) {
			for(List<String> currentMessage: fixedMessages) {
				String currentWord = currentMessage.get(wordIndex);
					if(!currentWord.isEmpty()) {
						combinedMessage[wordIndex] = currentWord;
					}
					
			}
		}
		
		return combinedMessage;
	}
	

}
