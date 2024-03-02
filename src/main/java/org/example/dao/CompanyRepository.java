package org.example.dao;

import javax.persistence.EntityManager;
import org.example.entity.Company;

public class CompanyRepository extends BaseRepository<Integer, Company> {

    public CompanyRepository(  EntityManager manager) {
        super(Company.class, manager);
    }
}
