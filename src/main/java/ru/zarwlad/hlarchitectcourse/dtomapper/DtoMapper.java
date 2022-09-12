package ru.zarwlad.hlarchitectcourse.dtomapper;

public interface DtoMapper<D, E, N> {
    //D - DTO, E - entity
    E fromDto(D dto);
    D toDto(E entity);
    E fromNewDto(N newDto);
}
