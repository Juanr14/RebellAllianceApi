package co.com.mercadolibre.rebell.alliance.service;

import java.util.List;

import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;

public interface IMessageDecoderService {

	public String decodeMessage(SatelliteInfoDTO[] messages);
}
