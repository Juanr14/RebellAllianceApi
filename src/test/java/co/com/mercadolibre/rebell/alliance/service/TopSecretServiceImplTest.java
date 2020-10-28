package co.com.mercadolibre.rebell.alliance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import co.com.mercadolibre.rebell.alliance.dto.response.TopSecretResponseDTO;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TopSecretServiceImplTest {

	@Autowired
	ITopSecretService topSecretService;
	
	@MockBean
	ILocationService locationService;
	
	@MockBean
	IMessageDecoderService messageDecoderService;
	
	
	@Before
	public void setup() {
		when(locationService.getPosition(null)).thenReturn(null);
		when(messageDecoderService.decodeMessage(null)).thenReturn(null);
	}
	
	@Test
	public void shouldReceiveResponse() {
		List<String> messages = new ArrayList<>();
		
		SatelliteInfoDTO satellite1 = new SatelliteInfoDTO("fake",(float) 1000, messages);
		SatelliteInfoDTO satellite2 = new SatelliteInfoDTO("skywalker",(float) 900, messages);
		SatelliteInfoDTO satellite3 = new SatelliteInfoDTO("sato",(float) 950.248898, messages);

		SatelliteInfoDTO[] satellitesArray = {satellite1, satellite2, satellite3};
		
		TopSecretResponseDTO response = topSecretService.identifyMessage(satellitesArray       );
		assertNotNull(response);
	}
}
