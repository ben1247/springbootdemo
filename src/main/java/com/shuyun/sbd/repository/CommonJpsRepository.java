package com.shuyun.sbd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Component:
 * Description:
 * Date: 15/7/5
 *
 * @author yue.zhang
 */
@NoRepositoryBean
public interface CommonJpsRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
