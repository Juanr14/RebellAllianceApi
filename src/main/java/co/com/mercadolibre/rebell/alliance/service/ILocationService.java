package co.com.mercadolibre.rebell.alliance.service;

import co.com.mercadolibre.rebell.alliance.dto.PositionDTO;
import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;

public interface ILocationService {
	PositionDTO getPosition(SatelliteInfoDTO[] satellites);
}
