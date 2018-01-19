package com.valework.yingul.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.PrimaryTransaction;

public interface PrimaryTransactionDao extends CrudRepository<PrimaryTransaction, Long> {

    List<PrimaryTransaction> findAll();
}
