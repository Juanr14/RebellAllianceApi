package co.com.mercadolibre.rebell.alliance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.mercadolibre.rebell.alliance.domain.SatelliteData;
import co.com.mercadolibre.rebell.alliance.dto.PositionDTO;
import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import co.com.mercadolibre.rebell.alliance.dto.response.TopSecretResponseDTO;
import co.com.mercadolibre.rebell.alliance.dto.response.TopSecretSplitResponseDTO;
import co.com.mercadolibre.rebell.alliance.repository.ISatelliteDataRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TopSecretSplitServiceImplTest {
	
	@Autowired
	ITopSecretSplitService topSecretSplitService;
	
	@Autowired
	ITopSecretService topSecretService;
	
	@MockBean
	ILocationService locationService;
	
	@MockBean
	IMessageDecoderService messageDecoderService;
	
	@MockBean
	ISatelliteDataRepository satelliteDataRepository;
	
	@Test
	public void shouldReceiveResponseCompleteData() {
		
		when(locationService.getPosition(ArgumentMatchers.any())).thenReturn(new PositionDTO((float) -2,(float) -2));
		when(messageDecoderService.decodeMessage(ArgumentMatchers.any())).thenReturn("Hola");
		
		List<SatelliteData> retrievedData = new ArrayList<SatelliteData>();
		SatelliteData satelliteFromDb = new SatelliteData();
		satelliteFromDb.setMessage("este,es,un,mensaje");
		retrievedData.add(satelliteFromDb);
		retrievedData.add(satelliteFromDb);
		retrievedData.add(satelliteFromDb);
		
		when(satelliteDataRepository.findAll()).thenReturn(retrievedData);

		TopSecretSplitResponseDTO response = topSecretSplitService.identifyMessage();
		assertNotNull(response);
		assertNotNull(response.getPosition());
		assertNotNull(response.getMessage());
	}
	
	@Test
	public void shouldReceiveResponseMissingData() {
		when(locationService.getPosition(ArgumentMatchers.any())).thenReturn(new PositionDTO((float) -2,(float) -2));
		when(messageDecoderService.decodeMessage(ArgumentMatchers.any())).thenReturn("Hola");
		
		List<SatelliteData> retrievedData = new ArrayList<SatelliteData>();
		SatelliteData satelliteFromDb = new SatelliteData();
		satelliteFromDb.setMessage("este,es,un,mensaje");
		retrievedData.add(satelliteFromDb);
		retrievedData.add(satelliteFromDb);
		
		when(satelliteDataRepository.findAll()).thenReturn(retrievedData);

		TopSecretSplitResponseDTO response = topSecretSplitService.identifyMessage();
		assertNotNull(response);
		assertNull(response.getMessage());
	}
	
	@Test
	public void shouldSaveData() {

		try {
			List<String> messages = new ArrayList<>();
			
			SatelliteInfoDTO satellite1 = new SatelliteInfoDTO("kenobi",(float) 1000, messages);
			
			
			when(satelliteDataRepository.save(ArgumentMatchers.any())).thenReturn(new SatelliteData());

			topSecretSplitService.savePartialData(satellite1);;
			
		} catch (Exception e) {
			assertNull(e);
		}
		
	
	}
}
