package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Motorized;
import com.valework.yingul.model.Yng_MotorizedSecurity;

public interface MotorizedSecurityDao extends CrudRepository<Yng_MotorizedSecurity,Long> {
	List<Yng_MotorizedSecurity> findByMotorized(Yng_Motorized motorized);
}
