package co.com.mercadolibre.rebell.alliance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.mercadolibre.rebell.alliance.domain.SatelliteData;



/**
 * ISatelliteDataRepository define methods to database transactions (JPA), for
 * SatelliteData entity. Inherits JpaRepository default methods.
 * 
 */
@Repository
public interface ISatelliteDataRepository 
	extends JpaRepository<SatelliteData, Long> {	
}
