package in.one2n.studentgrading.controller;

import java.util.List;

import in.one2n.studentgrading.dto.StudentScoreDTO;
import in.one2n.studentgrading.entity.Student;
import in.one2n.studentgrading.service.StudentService;
import in.one2n.studentgrading.util.PageableUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;

@Controller("/v1/api/student")
public class StudentController {

  public final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @Get
  public HttpResponse<Student> getStudentById(@QueryValue Long id) {
    return HttpResponse.ok(studentService.getStudentById(id));
  }

  @Post("/create")
  public HttpResponse<Student> addStudent(@Body Student student) {
    return HttpResponse.ok(studentService.addStudent(student));
  }

  @Put("/update")
  public HttpResponse<Student> updateStudent(@Body Student student) {
    return HttpResponse.ok(studentService.updateStudent(student));
  }

  @Delete
  public HttpStatus deleteById(@QueryValue Long id) {
    studentService.deleteById(id);
    return HttpStatus.NO_CONTENT;
  }

  @Get("/topper")
  public HttpResponse<List<StudentScoreDTO>> getOverallTopper() {
    return HttpResponse.ok(studentService.getOverallTopper());
  }

  @Get("/university-wise-topper")
  public HttpResponse<List<StudentScoreDTO>> getUniversityTopper() {
    return HttpResponse.ok(studentService.getUniversityWiseTopper());
  }

  @Get(value = "/all")
  public HttpResponse<List<Student>> getAllStudents(@QueryValue(defaultValue = "5") int size,
      @QueryValue(defaultValue = "1") int pageNumber) {
    PageableUtils pageable = new PageableUtils(size, pageNumber - 1);
    return HttpResponse.ok(studentService.getAllStudents(pageable));
  }

  @Get(value = "/score/all")
  public HttpResponse<List<StudentScoreDTO>> getAllStudentsWithScore(
      @QueryValue(defaultValue = "5") int size, @QueryValue(defaultValue = "1") int pageNumber) {
    return HttpResponse.ok(
        studentService.getAllStudentsWithScores(new PageableUtils(size, pageNumber - 1)));
  }
}
