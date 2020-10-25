package co.com.mercadolibre.rebell.alliance.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SatelliteInfoDTO implements Serializable{

	private static final long serialVersionUID = -2857540142613737729L;

	public String name;
	
	public float distance;
	
	public String[] message;

}
