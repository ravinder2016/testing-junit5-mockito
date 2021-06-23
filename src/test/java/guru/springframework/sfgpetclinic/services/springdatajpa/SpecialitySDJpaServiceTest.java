package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

  @Mock(lenient = true)
  private SpecialtyRepository specialtyRepository;

  @InjectMocks
  private SpecialitySDJpaService service;

  @Test
  void deleteByObject() {
    Speciality speciality = new Speciality();
    service.delete(speciality);
    verify(specialtyRepository).delete(any(Speciality.class));
  }

  @Test
  void findById() {
    Speciality speciality = new Speciality();
    when(specialtyRepository.findById(1l)).thenReturn(Optional.of(speciality));
    Speciality foundSpeciality = service.findById(1l);
    assertThat(foundSpeciality).isNotNull();
    verify(specialtyRepository).findById(1l);
  }

  @Test
  void delete() {
    service.delete(new Speciality());
  }

  @Test
  void deleteById() {
    service.deleteById(1l);
    service.deleteById(1l);
    verify(specialtyRepository, times(2)).deleteById(1l);
  }

  @Test
  void deleteByIdAtleastOnce() {
    service.deleteById(1l);
    service.deleteById(1l);
    verify(specialtyRepository, atLeastOnce()).deleteById(1l);
  }

  @Test
  void deleteByIdAtmost() {
    service.deleteById(1l);
    service.deleteById(1l);
    verify(specialtyRepository, atMost(5)).deleteById(1l);
  }

  @Test
  void testSaveLambda() {
    Speciality speciality = new Speciality();
    final String MATCH_ME ="MatchMe";
    speciality.setDescription("MatchMe");

    Speciality savedSpeciality = new Speciality();
    savedSpeciality.setId(1l);

    //given(specialtyRepository.save(any(Speciality.class))).willReturn(savedSpeciality);
    given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpeciality);

    Speciality returnSpeciality = service.save(speciality);

    assertThat(returnSpeciality.getId()).isEqualTo(1l);
  }

  @Test
  void testSaveLambdaNoMatch() {
    Speciality speciality = new Speciality();
    final String MATCH_ME ="MatchMe1";
    speciality.setDescription("MatchMe");

    Speciality savedSpeciality = new Speciality();
    savedSpeciality.setId(1l);

    //given(specialtyRepository.save(any(Speciality.class))).willReturn(savedSpeciality);
    given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpeciality);

    Speciality returnSpeciality = service.save(speciality);

    assertNull(returnSpeciality);;
  }
}