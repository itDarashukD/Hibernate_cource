package org.example.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(of = "name" ) //we going to calculate hask by UNIQUIE field
@ToString(exclude = "users") // avoid stackOverFlow
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false) //this field wii be unique and can't be null
    private String name;

    @OneToMany(mappedBy = "company")  //since field in User has name "company"
//    private List<User> users = new ArrayList<>();
    private Set<User> users = new HashSet<>(); //if we want to use Set<>, we have to ovveride @EqulsAndHascode

}
