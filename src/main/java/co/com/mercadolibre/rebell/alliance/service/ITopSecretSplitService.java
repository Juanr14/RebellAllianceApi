package co.com.mercadolibre.rebell.alliance.service;

import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import co.com.mercadolibre.rebell.alliance.dto.response.TopSecretSplitResponseDTO;

public interface ITopSecretSplitService {

	public TopSecretSplitResponseDTO identifyMessage();
	public void savePartialData(SatelliteInfoDTO satellite);
}
