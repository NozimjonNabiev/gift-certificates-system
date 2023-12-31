package com.epam.esm.config;

import com.epam.esm.util.mapper.GiftCertificateMapper;
import com.epam.esm.util.mapper.FilterMapper;
import com.epam.esm.util.mapper.TagMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceTestConfig {

    @Bean
    public GiftCertificateMapper certificateMapper() {
        return Mappers.getMapper(GiftCertificateMapper.class);
    }

    @Bean
    public FilterMapper filterMapper() {
        return Mappers.getMapper(FilterMapper.class);
    }

    @Bean
    public TagMapper tagMapper() {
        return Mappers.getMapper(TagMapper.class);
    }
}
