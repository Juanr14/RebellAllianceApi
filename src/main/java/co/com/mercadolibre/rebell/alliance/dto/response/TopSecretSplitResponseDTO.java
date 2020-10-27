package co.com.mercadolibre.rebell.alliance.dto.response;

import java.io.Serializable;

import co.com.mercadolibre.rebell.alliance.dto.PositionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopSecretSplitResponseDTO implements Serializable{

	private static final long serialVersionUID = -367296094909633291L;

	public PositionDTO position;
	
	public String message;
}
