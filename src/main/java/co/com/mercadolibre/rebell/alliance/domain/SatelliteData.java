package co.com.mercadolibre.rebell.alliance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * MutantValidation is the entity that stores each of the validations
 * SatelliteData is the entity that stores information captured by
 * the POST topsecret_split in order to perform de message decryption
 * and get the location using multiple request to charge the data.
 * 
 */
@Data
@Entity
public class SatelliteData {
	/**
	 * Name of the current satellite.
	 */
	@Id()
	@Column(name = "name", length = 255)
	private String name;
	
	/**
	 * Distance detected by the current satellite.
	 */
	@Column(name = "distance")
	private float distance;
	
	/**
	 * Message captured by the satellite, in string format concatenated
	 * by ,
	 */
	@Column(name = "message", length = 500)
	private String message;
	
}
