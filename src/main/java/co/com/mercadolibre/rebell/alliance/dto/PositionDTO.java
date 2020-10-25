package co.com.mercadolibre.rebell.alliance.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO implements Serializable{

	
	private static final long serialVersionUID = 9130757029259142530L;
	
	public float x;
	
	public float y;

	
}
