package yoonspring.helloboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class HelloApiTest {

  @Test
  void helloApi() {
    TestRestTemplate rest = new TestRestTemplate();

    ResponseEntity<String> res = rest.getForEntity("http://localhost:7000/app/hello?name={name}", String.class, "Spring");

    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);
    assertThat(res.getBody()).isEqualTo("*hello Spring*");
  }

  @Test
  void failsHelloApi() {
    TestRestTemplate rest = new TestRestTemplate();

    ResponseEntity<String> res = rest.getForEntity("http://localhost:7000/app/hello?name=", String.class);
    assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
