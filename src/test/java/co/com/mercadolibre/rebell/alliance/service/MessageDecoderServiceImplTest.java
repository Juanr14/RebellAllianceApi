package co.com.mercadolibre.rebell.alliance.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.mercadolibre.rebell.alliance.dto.PositionDTO;
import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageDecoderServiceImplTest {

	@Autowired
	IMessageDecoderService messageDecoderService;
	
	@Test
	public void shouldReceiveValidMessage() {
		

		List<String> messages1 = Stream.of("","este","es","un","mensaje")
			      .collect(Collectors.toList());
		List<String> messages2 = Stream.of("este","","un","mensaje")
			      .collect(Collectors.toList());
		List<String> messages3 = Stream.of("","","es","","mensaje")
			      .collect(Collectors.toList());

		
		SatelliteInfoDTO satellite1 = new SatelliteInfoDTO("kenobi",(float) 1000, messages1);
		SatelliteInfoDTO satellite2 = new SatelliteInfoDTO("skywalker",(float) 900, messages2);
		SatelliteInfoDTO satellite3 = new SatelliteInfoDTO("sato",(float) 950.248898, messages3);

		SatelliteInfoDTO[] satellitesArray = {satellite1, satellite2, satellite3};
		
		String retrievedMessage = messageDecoderService.decodeMessage(satellitesArray);
		
		assertNotNull(retrievedMessage);

	}
	
	@Test
	public void shouldReceiveValidMessageRemovingGap() {
		

		List<String> messages1 = Stream.of("este","es","un","mensaje")
			      .collect(Collectors.toList());
		List<String> messages2 = Stream.of("este","","un","mensaje")
			      .collect(Collectors.toList());
		List<String> messages3 = Stream.of("","es","","mensaje")
			      .collect(Collectors.toList());

		
		SatelliteInfoDTO satellite1 = new SatelliteInfoDTO("kenobi",(float) 1000, messages1);
		SatelliteInfoDTO satellite2 = new SatelliteInfoDTO("skywalker",(float) 900, messages2);
		SatelliteInfoDTO satellite3 = new SatelliteInfoDTO("sato",(float) 950.248898, messages3);

		SatelliteInfoDTO[] satellitesArray = {satellite1, satellite2, satellite3};
		
		String retrievedMessage = messageDecoderService.decodeMessage(satellitesArray);
		
		assertNotNull(retrievedMessage);

	}
	
	@Test
	public void shouldReceiveNullByWrongMessages() {
		

		List<String> messages1 = Stream.of("","es","un","mensaje")
			      .collect(Collectors.toList());
		List<String> messages2 = Stream.of("","","un","mensaje")
			      .collect(Collectors.toList());
		List<String> messages3 = Stream.of("","es","","mensaje")
			      .collect(Collectors.toList());

		
		SatelliteInfoDTO satellite1 = new SatelliteInfoDTO("kenobi",(float) 1000, messages1);
		SatelliteInfoDTO satellite2 = new SatelliteInfoDTO("skywalker",(float) 900, messages2);
		SatelliteInfoDTO satellite3 = new SatelliteInfoDTO("sato",(float) 950.248898, messages3);

		SatelliteInfoDTO[] satellitesArray = {satellite1, satellite2, satellite3};
		
		String retrievedMessage = messageDecoderService.decodeMessage(satellitesArray);
		
		assertNull(retrievedMessage);

	}
}
