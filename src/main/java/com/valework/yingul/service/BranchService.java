package com.valework.yingul.service;

import java.util.List;

import com.valework.yingul.model.Yng_Branch;

public interface BranchService {
	List<Yng_Branch> findAll();
	Yng_Branch findByBranchId(Long id);
}
