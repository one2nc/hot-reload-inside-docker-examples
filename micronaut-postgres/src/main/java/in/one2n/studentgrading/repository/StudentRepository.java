package in.one2n.studentgrading.repository;

import java.util.List;

import in.one2n.studentgrading.dto.StudentScoreDTO;
import in.one2n.studentgrading.entity.Student;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.repository.PageableRepository;

@Repository
public interface StudentRepository extends PageableRepository<Student, Long> {

  String findOverAllTopperQuery =
      "select "
          + "s.id as id, s.first_name as firstName, s.last_name as lastName, "
          + "u.name as university, sc.final_score as finalScore, g.grade as grade "
          + "from students s "
          + "join universities u on s.university_id = u.id "
          + "join grades g on s.grade_id = g.id "
          + "join scores sc on s.id = sc.student_id "
          + "where sc.final_score = (select max(final_score) from scores)";

  String maxFinalScoreAndUniversityQuery =
      "select "
          + "max(sc.final_score) as finalScore, u.name as university "
          + "from students s "
          + "join universities u on s.university_id = u.id "
          + "join scores sc on s.id = sc.student_id "
          + "group by university";

  String findUniversityWiseTopper =
      "select "
          + "s.id as id, s.first_name as firstName, s.last_name as lastName, "
          + "u.name as university, sc.final_score as finalScore, g.grade as grade "
          + "from students s "
          + "join universities u on s.university_id = u.id "
          + "join grades g on s.grade_id = g.id "
          + "join scores sc on s.id = sc.student_id "
          + "where (sc.final_score, u.name) "
          + "in "
          + "("+maxFinalScoreAndUniversityQuery+")";

  String findAllStudentsWithScoreQuery =
      "select s.id as id, s.first_name as firstName, s.last_name as lastName, g.grade as grade, "
          + "u.name as university, sc.final_score as finalScore, sc.test1_score as test1Score, "
          + "sc.test2_score as test2Score, sc.test3_score as test3Score, sc.test4_score as test4Score "
          + "from students s "
          + "join universities u on s.university_id = u.id "
          + "join grades g on s.grade_id = g.id "
          + "join scores sc on s.id = sc.student_id";

  String studentCountQuery = "select count(*) from students";

  @Query(value = findAllStudentsWithScoreQuery, countQuery = studentCountQuery, nativeQuery = true)
  Page<StudentScoreDTO> findAllStudentsWithScores(Pageable pageable);

  @Query(value = findOverAllTopperQuery, nativeQuery = true)
  List<StudentScoreDTO> findOverallTopper();

  @Query(value = findUniversityWiseTopper, nativeQuery = true)
  List<StudentScoreDTO> findUniversityWiseTopper();
}