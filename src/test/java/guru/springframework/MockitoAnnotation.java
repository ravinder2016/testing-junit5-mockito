package guru.springframework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

public class MockitoAnnotation {
  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Mock
  private Map myMap;

  @Test
  void mapValidate() {
    myMap.put("name", "Raveender");
  }
}
