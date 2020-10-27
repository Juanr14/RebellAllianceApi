package co.com.mercadolibre.rebell.alliance.dto.request;

import java.io.Serializable;

import co.com.mercadolibre.rebell.alliance.dto.SatelliteInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopSecretRequestDTO implements Serializable{


	private static final long serialVersionUID = 8288480620810187185L;
	public SatelliteInfoDTO[] satellites;
	
}
