package guru.springframework;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class InlineMockTest {
  @Test
  void mockTest() {
    Map map = mock(Map.class);
    assertEquals(map.size(), 0);
  }
}
