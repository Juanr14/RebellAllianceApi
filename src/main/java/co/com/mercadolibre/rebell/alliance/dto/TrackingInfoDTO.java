package co.com.mercadolibre.rebell.alliance.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingInfoDTO implements Serializable{
	
	private static final long serialVersionUID = 1441778505248539113L;
	
	public PositionDTO coordinates;
	
	public String name;
	
	public float radius;

}
