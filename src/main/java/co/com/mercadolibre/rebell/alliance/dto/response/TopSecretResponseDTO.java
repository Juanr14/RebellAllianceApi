package co.com.mercadolibre.rebell.alliance.dto.response;

import co.com.mercadolibre.rebell.alliance.dto.PositionDTO;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopSecretResponseDTO implements Serializable{


	private static final long serialVersionUID = -6990208745439214933L;
	
	public PositionDTO position;
	
	public String message;
}
