package co.com.mercadolibre.rebell.alliance.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import co.com.mercadolibre.rebell.alliance.dto.PositionDTO;
import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceImplTest {

	@Autowired 
	ILocationService locationService;
	
	@Test
	public void shouldReceiveValidPosition() {
		
		List<String> messages = new ArrayList<>();
		
		SatelliteInfoDTO satellite1 = new SatelliteInfoDTO("kenobi",(float) 1000, messages);
		SatelliteInfoDTO satellite2 = new SatelliteInfoDTO("skywalker",(float) 900, messages);
		SatelliteInfoDTO satellite3 = new SatelliteInfoDTO("sato",(float) 950.248898, messages);

		SatelliteInfoDTO[] satellitesArray = {satellite1, satellite2, satellite3};
		
		PositionDTO retrievedPosition = locationService.getPosition(satellitesArray);
		
		assertNotNull(retrievedPosition);
		assertNotNull(retrievedPosition.getX());
		assertNotNull(retrievedPosition.getY());

	}
	
	@Test
	public void shouldRecevieNullByWrongName(){
		List<String> messages = new ArrayList<>();
		
		SatelliteInfoDTO satellite1 = new SatelliteInfoDTO("fake",(float) 1000, messages);
		SatelliteInfoDTO satellite2 = new SatelliteInfoDTO("skywalker",(float) 900, messages);
		SatelliteInfoDTO satellite3 = new SatelliteInfoDTO("sato",(float) 950.248898, messages);

		SatelliteInfoDTO[] satellitesArray = {satellite1, satellite2, satellite3};
		
		PositionDTO retrievedPosition = locationService.getPosition(satellitesArray);
		
		assertNull(retrievedPosition);
	}	
}
