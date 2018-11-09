package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_Claim;
import com.valework.yingul.model.Yng_Confirm;

public interface ClaimDao extends CrudRepository<Yng_Claim, Long>{
	Yng_Claim findByClaimId(Long clainId);
	Yng_Claim findByConfirm(Yng_Confirm confirm);
}
