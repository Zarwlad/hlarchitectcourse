package ru.zarwlad.hlarchitectcourse.dtomapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.zarwlad.hlarchitectcourse.entity.City;
import ru.zarwlad.hlarchitectcourse.entity.Interest;
import ru.zarwlad.hlarchitectcourse.entity.Person;
import ru.zarwlad.hlarchitectcourse.model.NewPersonDto;
import ru.zarwlad.hlarchitectcourse.model.PersonDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonDtoMapperImpl implements PersonDtoMapper{
    @Autowired
    PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    CityDtoMapper cityDtoMapper;

    @Autowired
    InterestDtoMapper interestDtoMapper;

    @Override
    public Person fromDto(PersonDto dto) {
        return modelMapper.map(dto, Person.class);
    }

    @Override
    public PersonDto toDto(Person entity) {
        PersonDto personDto = modelMapper.map(entity, PersonDto.class);
        if (entity.getInterests() != null && !entity.getInterests().isEmpty()) {
            personDto.setInterestsDtoList(entity.getInterests().stream()
                    .map(interest -> interestDtoMapper.toDto(interest)).collect(Collectors.toList())
            );
        }
        personDto.setCityDto(cityDtoMapper.toDto(entity.getCity()));

        return personDto;
    }

    public Person fromNewDto (NewPersonDto dto){
        City city = new City();
        city.setName(dto.getNewCityDto().getName());
        city.setRegion(dto.getNewCityDto().getRegion());

        List<Interest> interests = dto.getNewInterestDtoList().stream()
                .map(newInterestDto -> {
                    Interest interest = new Interest();
                    interest.setTag(newInterestDto.getTag());
                    return interest;
                }).toList();

        Person person = new Person();
        person.setCity(city);
        person.setPassword(passwordEncoder.encode(dto.getPassword()));
        person.setLogin(dto.getLogin());
        person.setEmail(dto.getEmail());
        person.setName(dto.getName());
        person.setSurname(dto.getSurname());
        person.setAge(dto.getAge());
        person.setGender(dto.getGender());
        person.setInterests(interests);

        return person;
    }
}
