package com.shuyun.sbd.repository;

import com.shuyun.sbd.domain.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Component:
 * Description:
 * Date: 15/7/5
 *
 * @author yue.zhang
 */
public interface CityRepository extends CommonJpsRepository<City, Long> {

    @Query("select c from City c where c.name = ?1")
    public Iterable<City> findByNameSql(String name);

    public Page<City> findAll(Pageable pageable);

//    public Page<City> findSearch(Specification<City> spec, Pageable pageable);

    public Iterable<City> findByName(String name);

    public List<City> findAll(Specification<City> spec);

}
