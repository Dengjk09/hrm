package com.dengjk.dao;


import com.dengjk.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Dengjk
 * @create 2019-04-13 16:50
 * @desc
 **/
@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> ,JpaSpecificationExecutor<Company> {

}
