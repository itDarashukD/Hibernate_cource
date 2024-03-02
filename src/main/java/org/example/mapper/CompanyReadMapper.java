package org.example.mapper;

import org.example.dto.CompanyReadDto;
import org.example.entity.Company;

public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {

    @Override
    public CompanyReadDto mapFrom(Company from) {

        return new CompanyReadDto(from.getId(), from.getName());
    }


}
