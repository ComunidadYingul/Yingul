package com.valework.yingul.dao;

import org.springframework.data.repository.CrudRepository;

import com.valework.yingul.model.security.Yng_Role;

public interface RoleDao extends CrudRepository<Yng_Role, Integer> {
    Yng_Role findByName(String name);
}
