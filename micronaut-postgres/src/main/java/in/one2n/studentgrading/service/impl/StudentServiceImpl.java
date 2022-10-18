package in.one2n.studentgrading.service.impl;

import java.util.List;

import in.one2n.studentgrading.constants.ErrorMessage;
import in.one2n.studentgrading.dto.StudentScoreDTO;
import in.one2n.studentgrading.entity.Student;
import in.one2n.studentgrading.exception.UserNotFoundException;
import in.one2n.studentgrading.repository.StudentRepository;
import in.one2n.studentgrading.service.StudentService;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class StudentServiceImpl implements StudentService {

  private final Logger logger;

  private final StudentRepository studentRepository;

  public StudentServiceImpl(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
    this.logger = LoggerFactory.getLogger(Student.class);
  }

  @Override
  public List<Student> getAllStudents(Pageable pageable) {
    return studentRepository.findAll(pageable).getContent();
  }

  @Override
  public Student getStudentById(Long id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
  }

  @Override
  public Student addStudent(Student student) {
    return studentRepository.save(student);
  }

  @Override
  public void deleteById(Long id) {
    studentRepository.deleteById(id);
    logger.info("Deleted student with id " + id);
  }

  @Override
  public List<StudentScoreDTO> getAllStudentsWithScores(Pageable pageable) {
    return studentRepository.findAllStudentsWithScores(pageable).getContent();
  }

  @Override
  public Student updateStudent(Student student) {
    return studentRepository.update(student);
  }

  @Override
  public List<StudentScoreDTO> getOverallTopper() {
    return studentRepository.findOverallTopper();
  }

  @Override
  public List<StudentScoreDTO> getUniversityWiseTopper() {
    return studentRepository.findUniversityWiseTopper();
  }
}
