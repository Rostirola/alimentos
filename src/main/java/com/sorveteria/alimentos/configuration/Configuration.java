package com.sorveteria.alimentos.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public ModelMapper returnModelMapper() {
        return new ModelMapper();
    }
}
