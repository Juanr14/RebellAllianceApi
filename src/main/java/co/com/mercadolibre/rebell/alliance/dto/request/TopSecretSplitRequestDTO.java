package co.com.mercadolibre.rebell.alliance.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopSecretSplitRequestDTO implements Serializable {

	private static final long serialVersionUID = -2747086912362670518L;

	public float distance;
	
	public String[] message;
}
