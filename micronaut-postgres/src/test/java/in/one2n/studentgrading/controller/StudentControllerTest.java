package in.one2n.studentgrading.controller;

import java.util.List;
import java.util.Objects;

import in.one2n.studentgrading.entity.Student;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
class StudentControllerTest {

  @Inject
  EmbeddedServer embeddedServer;

  @Inject
  @Client("/")
  HttpClient httpClient;

  @Test
  void testItWorks() {
    assertTrue(embeddedServer.isRunning());
  }

  @Test
  @Order(1)
  void getStudentById() {
    //when:
    HttpRequest<Object> request = HttpRequest.GET("/v1/api/student?id=1");
    HttpResponse<Student> response = httpClient.toBlocking().exchange(request, Student.class);

    //then:
    assertEquals(HttpStatus.OK, response.getStatus());
    assertNotNull(response);

    //when:
    Student student = response.body();

    //then:
    assertNotNull(student);
    assertEquals("Kaylen", student.getFirstName());
  }

  @Test
  void studentNotFoundTest() {
    //when: student
    HttpRequest<Student> request = HttpRequest.GET("/v1/api/student?id=10001");
    HttpResponse<Object> response2 = httpClient.toBlocking()
        .exchange(request, Argument.of(Object.class), Argument.of(Object.class));

    // then:
    assertNotNull(response2);
    assertEquals(HttpStatus.NOT_FOUND, response2.getStatus());
  }

  @Test
  @Order(2)
  void getAllStudents() {

    //when:
    HttpRequest<Object> request = HttpRequest.GET("/v1/api/student/all");
    HttpResponse<List<Student>> response =
        httpClient.toBlocking().exchange(request, Argument.listOf(Student.class));

    //then:
    assertEquals(HttpStatus.OK, response.getStatus());
    assertNotNull(response);

    //when:
    List<Student> studentList = response.body();

    //then:
    assertNotNull(studentList);
    assertEquals("Kaylen", studentList.get(0).getFirstName());
    assertEquals(5, studentList.size());
  }

  @Test
  @Order(3)
  void getUniversityWiseTopper() {
    //when:
    List<Student> topperListFromController = httpClient.toBlocking()
        .retrieve(HttpRequest.GET("/v1/api/student/university-wise-topper"),
            Argument.listOf(Student.class));

    //then:
    assertNotNull(topperListFromController);
    assertEquals("Izayah", topperListFromController.get(0).getFirstName());
  }

  @Test
  @Order(4)
  void getOverallTopperTest() {
    //when:
    List<Student> topperList = httpClient.toBlocking()
        .retrieve(HttpRequest.GET("/v1/api/student/topper"), Argument.listOf(Student.class));

    //then:
    assertNotNull(topperList);
    assertEquals("Bernard", topperList.get(0).getFirstName());
  }

  @Test
  @Order(5)
  void getAllStudentsPagination(){
    HttpRequest<Object> request = HttpRequest.GET("/v1/api/student/all?size=3&pageNumber=8");
    HttpResponse<List<Student>> response =
        httpClient.toBlocking().exchange(request, Argument.listOf(Student.class));

    //then:
    assertEquals(HttpStatus.OK, response.getStatus());
    assertNotNull(response);

    //when:
    List<Student> studentList = response.body();

    //then:
    assertNotNull(studentList);
    assertEquals("Azaria", studentList.get(0).getFirstName());
    assertEquals(3, studentList.size());
  }

  @Test
  @Order(6)
  void updateStudent() {
    //when:
    String requestBody =
        "{" +
            "\"id\": 8,\n"+
            "\"firstName\": \"David\",\n" +
            "\"lastName\": \"Holmes\",\n" +
            "\"universityId\": 2,\n" +
            "\"gradeId\": 3" +
            "}";

    HttpRequest<Object> request = HttpRequest.PUT("/v1/api/student/update", requestBody);
    HttpResponse<Student> response = httpClient.toBlocking().exchange(request, Student.class);

    //then:
    assertNotNull(response);
    assertEquals("David", Objects.requireNonNull(response.body()).getFirstName());
  }

  @Test
  @Order(7)
  void addStudent() {
    //when:
    String requestBody =
        "{" + "\"firstName\": \"Ragnar\",\n" +
            "\"lastName\": \"Lothruk\",\n" +
            "\"universityId\": 3,\n" +
            "\"gradeId\": 4\n" +
            "}";
    HttpRequest<Object> request = HttpRequest.POST("/v1/api/student/create", requestBody);
    HttpResponse<Student> response = httpClient.toBlocking().exchange(request, Student.class);

    //then:
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatus());

    //when:
    Student student = response.body();

    //then:
    assertNotNull(student);
    assertEquals("Ragnar", student.getFirstName());
  }

  @Test
  @Order(8)
  void deleteById() {
    HttpResponse<Student> response = httpClient.toBlocking()
        .exchange(HttpRequest.DELETE("/v1/api/student/?id=1"), Student.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
  }
}