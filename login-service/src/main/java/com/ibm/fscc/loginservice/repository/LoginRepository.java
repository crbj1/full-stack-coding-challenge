package com.ibm.fscc.loginservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.fscc.loginservice.data.LoginEntity;

@Repository
public interface LoginRepository extends CrudRepository<LoginEntity, String> {

}
