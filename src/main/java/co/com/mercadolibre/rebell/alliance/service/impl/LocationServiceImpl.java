package co.com.mercadolibre.rebell.alliance.service.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.com.mercadolibre.rebell.alliance.dto.PositionDTO;
import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import co.com.mercadolibre.rebell.alliance.dto.TrackingInfoDTO;
import co.com.mercadolibre.rebell.alliance.service.ILocationService;

@Service
public class LocationServiceImpl implements ILocationService{

	@Value("${satellites.coordinates}")
	private String rawCoordinates;
	
	@Value("${satellites.number}")
	private int satellitesNumber;
	
	Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);

	
	@Override
	public PositionDTO getPosition(SatelliteInfoDTO[] satellites) {

		PositionDTO foundedPosition = new PositionDTO();
		ArrayList<TrackingInfoDTO> mappedTrackingInfo = new ArrayList<>();
		
		for(SatelliteInfoDTO currentSatellite: satellites) {
			mappedTrackingInfo.add(mapSatelliteCoordinates(currentSatellite));
		}
		if(!mappedTrackingInfo.contains(null)) {
			TrackingInfoDTO infoSatellite1 = mappedTrackingInfo.get(0);
			TrackingInfoDTO infoSatellite2 = mappedTrackingInfo.get(1);
			TrackingInfoDTO infoSatellite3 = mappedTrackingInfo.get(2);
			
			foundedPosition = trackMessagePosition(infoSatellite1, infoSatellite2, infoSatellite3);
			logger.info("[Rebell Alliance - TopSecretServiceImpl] Posición del mensaje determinada, enviando coordenadas: "+foundedPosition.toString());
			
			return foundedPosition;
		}
		logger.info("[Rebell Alliance - TopSecretServiceImpl] No se pudo determinar la posición, uno o más satelites no pudieron ser mapeados");

		return null;
		
	}
	
	private TrackingInfoDTO mapSatelliteCoordinates(SatelliteInfoDTO satellite) {
		String[] satellitesCoordinates =  rawCoordinates.split("/");
		if(satellitesCoordinates.length != satellitesNumber) {
			logger.info("[Rebell Alliance - TopSecretServiceImpl] Error en propiedades, numero de coordenadas parametrizadas erroneas");
			return null;
		}
		
		for(String currentCoordinates: satellitesCoordinates) {
			String[] splitedCoordinates = currentCoordinates.split(",");
			String name = splitedCoordinates[0];
			float x = Float.parseFloat(splitedCoordinates[1]);
			float y = Float.parseFloat(splitedCoordinates[2]);
			
			if(name.equals(satellite.getName())) {
				TrackingInfoDTO mappedCoordinates = new TrackingInfoDTO();
				
				PositionDTO currentPosition = new PositionDTO(x , y);
				
				mappedCoordinates.setName(name);
				mappedCoordinates.setRadius(satellite.getDistance());
				mappedCoordinates.setCoordinates(currentPosition);
				return mappedCoordinates;
			}
		}
		return null;
	}
	
	private PositionDTO trackMessagePosition(TrackingInfoDTO satellite1, TrackingInfoDTO satellite2, TrackingInfoDTO satellite3) {
		
		// Satellite 1 data
		float x1 = satellite1.getCoordinates().getX();
		float y1 = satellite1.getCoordinates().getY();
		float r1 = satellite1.getRadius();
		
		// Satellite 2 data
		float x2 = satellite2.getCoordinates().getX();
		float y2 = satellite2.getCoordinates().getY();
		float r2 = satellite2.getRadius();
		
		// Satellite 3 data
		float x3 = satellite3.getCoordinates().getX();
		float y3 = satellite3.getCoordinates().getY();
		float r3 = satellite3.getRadius();
		
		// Calculing the coefficients of the variables
		float A = 2*x2 - 2*x1;
		float B = 2*y2 - 2*y1;
		float C = r1*r1 - r2*r2 - x1*x1 + x2*x2 - y1*y1 + y2*y2;  
		float D = 2*x3 - 2*x2;
		float E = 2*y3 - 2*y2;
		float F = r2*r2 - r3*r3 - x2*x2 + x3*x3 - y2*y2 + y3*y3;
		
		// Calculing the coordinates of the emisor
		float x = (C*E - F*B) / (E*A - B*D);
	    float y = (C*D - A*F) / (B*D - A*E);
		
		return new PositionDTO(x , y);
	}

}
