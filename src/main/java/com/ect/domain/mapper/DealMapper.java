package com.ect.domain.mapper;

import com.ect.domain.DealDomain;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public interface DealMapper {

    void save(DealDomain dealDomain);

}
