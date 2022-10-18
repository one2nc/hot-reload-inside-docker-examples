package in.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@ToString
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "user_name", unique = true)
  private String userName;

  @Column(name = "email", unique = true)
  private String email;

  @Size(min = 8, message = "password has to be > 8 characters")
  @Column(name = "password")
  private String password;

  @Column(name = "gender")
  private String gender;

  @Column(name = "state")
  private String state;

  @Pattern(regexp = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$", message = "only digits are allowed!")
  @Column(name = "mobileNumber")
  private String mobileNumber;

  @Column(name = "address")
  private String address;
}
