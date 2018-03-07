package com.valework.yingul.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.valework.yingul.model.Yng_WireTransfer;

public interface WireTransferDao extends CrudRepository<Yng_WireTransfer, Long> {
	Yng_WireTransfer findByWireTransferId(Long wireTransferId);
	List<Yng_WireTransfer> findByOrderByWireTransferIdDesc();
	List<Yng_WireTransfer> findByStatusOrderByWireTransferIdDesc(String status);

}
