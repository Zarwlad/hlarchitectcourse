package ru.zarwlad.hlarchitectcourse.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean(name = "modelMapperBean")
    ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
