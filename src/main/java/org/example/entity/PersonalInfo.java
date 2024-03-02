package org.example.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable // since it is inner abject to class User
public class PersonalInfo {

  @NotNull //@NotNull is present as validation
  private String sex;

  @Column(name = "money_count")
  private Long moneyCount;

}
