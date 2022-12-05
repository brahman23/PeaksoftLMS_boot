package com.example.peaksoftlms_boot.repository;


import com.example.peaksoftlms_boot.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public  interface CompanyRepository extends JpaRepository<Company, Long> {

}
