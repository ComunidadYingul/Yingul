package com.valework.yingul.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valework.yingul.dao.BranchDao;
import com.valework.yingul.model.Yng_Branch;
import com.valework.yingul.service.BranchService;
@Service
public class BranchServiceImpl implements BranchService{
	@Autowired
	BranchDao branchDao;
	@Override
	public List<Yng_Branch> findAll() {
		// TODO Auto-generated method stub
		return branchDao.findAll();
	}

	@Override
	public Yng_Branch findByBranchId(Long id) {
		// TODO Auto-generated method stub
		return branchDao.findByBranchId(id);
	}

}
