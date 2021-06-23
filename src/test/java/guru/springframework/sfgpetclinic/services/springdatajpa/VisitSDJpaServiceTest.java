package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

  @Mock
  VisitRepository visitRepository;

  @InjectMocks
  VisitSDJpaService service;

  @Test
  void findAll() {
    //given
    Visit visit = new Visit();
    Set<Visit> visits = new HashSet<>();
    visits.add(visit);
    given(visitRepository.findAll()).willReturn(visits);
    //when(visitRepository.findAll()).thenReturn(visits);

    //when
    Set<Visit> findVisits = service.findAll();

    //then
    then(visitRepository).should().findAll();
    //verify(visitRepository).findAll();
    assertThat(findVisits).hasSize(1);

  }

  @Test
  void findById() {
    //given
    Visit visit = new Visit();
    //when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));
    given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));

    //when
    Visit returnValue = service.findById(1l);

    //then
    then(visitRepository).should().findById(anyLong());
    //verify(visitRepository).findById(anyLong());
    assertThat(visit).isNotNull();
  }

  @Test
  void findByIdBDD() {
    Visit visit = new Visit();
    given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));
    Visit returnValue = service.findById(1l);
    //verify(visitRepository).findById(anyLong());
    //then(visitRepository).should().findById(anyLong());
    then(visitRepository).should(times(1)).findById(anyLong());
    assertThat(returnValue).isNotNull();
  }

  @Test
  void save() {
    //given
    Visit visit = new Visit();
    given(visitRepository.save(any(Visit.class))).willReturn(visit);
    //when(visitRepository.save(any(Visit.class))).thenReturn(visit);

    //when
    Visit savedVisit = service.save(new Visit());

    //then
    then(visitRepository).should().save(any(Visit.class));
    //verify(visitRepository).save(any(Visit.class));
    assertThat(savedVisit).isNotNull();
  }

  @Test
  void delete() {
    //given
    Visit visit = new Visit();

    //when
    service.delete(visit);

    then(visitRepository).should().delete(any(Visit.class));
    //verify(visitRepository).delete(any(Visit.class));
  }

  @Test
  void deleteByObjectBDD() {
    Visit visit = new Visit();
    service.delete(visit);
    then(visitRepository).should().delete(any(Visit.class));
  }

  @Test
  void deleteById() {
    //given - none

    //when
    service.deleteById(1l);

    //then
    then(visitRepository).should(times(1)).deleteById(anyLong());
    //verify(visitRepository).deleteById(anyLong());

  }

  @Test
  void testDoThrow() {
    doThrow(new RuntimeException("Test")).when(visitRepository).delete(any());
    assertThrows(RuntimeException.class, () -> service.delete(new Visit()));
    verify(visitRepository).delete(any());
  }

  @Test
  void findByIdThrowsBDD() {
    //given
    given(visitRepository.findById(anyLong())).willThrow(new RuntimeException("Test"));

    //when
    assertThrows(RuntimeException.class, () -> service.findById(1l));

    //then
    then(visitRepository).should().findById(anyLong());


  }

  @Test
  void deleteByIdThrowsBDD() {
    willThrow(new RuntimeException("Test")).given(visitRepository).deleteById(anyLong());

    assertThrows(RuntimeException.class, () -> service.deleteById(1l));

    then(visitRepository).should().deleteById(anyLong());
  }
}