package org.example.entity;


import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.converter.CustomBirthdayConverter;

@EqualsAndHashCode(of = "userName") //if we use exactly Set<User> in Company
@ToString(exclude = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // most usefull
    public Integer  id;

    @Column(unique = true, nullable = false)
    private String userName;

    private String firstName;

    private String lastname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private Integer age;
    @Enumerated(EnumType.STRING)
    private Role role;

    // instead using age and LacalDate birthday we can create custom type :
    // should create converter : CustomBirthdayConverter
    @Column(name = "custom_birth_date")
    @Convert(converter = CustomBirthdayConverter.class)
    private CustomBirthday customBirthday;

    @Embedded //since it is inner class
    private PersonalInfo personalInfo;

//    @ManyToOne (cascade = CascadeType.ALL)    //only All can save both types User and Company to DB                     //many users to one company
    @ManyToOne ( optional = false, fetch = FetchType.EAGER)    //optional - совпадения для второй сущности при джоине не обязательно должны бытьб Игер - жадная (по-умочанию для сущностей), Lazy - по умолч для Коллекций
    @JoinColumn(name = "company_id")    // the tables user and company going to join by company_id , company_id == id in Company class
    private Company company;

}
