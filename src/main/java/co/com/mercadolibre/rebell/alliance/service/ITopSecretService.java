package co.com.mercadolibre.rebell.alliance.service;

import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import co.com.mercadolibre.rebell.alliance.dto.response.TopSecretResponseDTO;

public interface ITopSecretService {

	public TopSecretResponseDTO identifyMessage(SatelliteInfoDTO[] satellites);
}
