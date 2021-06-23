package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
  public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
  @Mock
  private OwnerService service;

  @InjectMocks
  private OwnerController controller;

  @Mock
  BindingResult bindingResult;

  @Captor
  ArgumentCaptor<String> captor;

  @Test
  void processFindForm() {
    //given
    Owner owner = new Owner(1l, "Raveender", "Karumula");
    List<Owner> allOwners = new ArrayList<>();
    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
    given(service.findAllByLastNameLike(captor.capture())).willReturn(allOwners);

    //when
    String returnValue = controller.processFindForm(owner, bindingResult, null);
    assertThat("%Karumula%").isEqualTo(captor.getValue());
  }

  @BeforeEach
  void setUp() {
    given(service.findAllByLastNameLike(captor.capture()))
      .willAnswer(argument -> {
        List<Owner> allOwners = new ArrayList<>();
        String name = argument.getArgument(0);
        if (name.equals("%Karumula%")) {
          allOwners.add(new Owner(1l, "Raveender", "Karumula"));
          return allOwners;
        }
        throw new RuntimeException("Invalid Argument");
      });
  }

  @Test
  void processFindFormWithAnnotation() {
    //given
    Owner owner = new Owner(1l, "Raveender", "Karumula");
    /*List<Owner> allOwners = new ArrayList<>();
    given(service.findAllByLastNameLike(captor.capture())).willReturn(allOwners);*/

    //when
    String viewName = controller.processFindForm(owner, bindingResult, null);

    assertThat("%Karumula%").isEqualTo(captor.getValue());
    assertThat("redirect:/owners/1").isEqualTo(viewName);
  }

  @Test
  void processCreationFormHasErrors() {

    //given
    Owner owner = new Owner(1l, "Raveender", "Karumula");
    given(bindingResult.hasErrors()).willReturn(true);

    //when
    String returnedView = controller.processCreationForm(owner, bindingResult);

    //then
    assertThat(returnedView).isEqualTo(OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
  }

  @Test
  void processCreationFormNoErrors() {

    Owner owner = new Owner(1l, "Raveender", "Karumula");
    //given
    given(bindingResult.hasErrors()).willReturn(false);
    given(service.save(any(Owner.class))).willReturn(owner);

    //when
    String returnView = controller.processCreationForm(owner, bindingResult);

    //then
    assertThat(returnView).isEqualTo("redirect:/owners/1");
  }
}