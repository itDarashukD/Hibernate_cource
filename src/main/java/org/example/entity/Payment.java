package org.example.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;


@Data
@NoArgsConstructor
@AllArgsConstructor
//@ToString(exclude = "receiver")
@Builder
@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)  //more usefull
public class Payment implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id") // //in User present mapped field "id"
    private User receiver;

    @Column(nullable = false)
    private Integer amount;

    @Version
    private Long version;  //need to add this field to do @OptimisticLocking || @PessimisticLocking

}
