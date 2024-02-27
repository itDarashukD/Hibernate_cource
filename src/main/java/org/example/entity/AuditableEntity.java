package org.example.entity;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass  // tell the it is a super class
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T> {

    private Instant created_at;

    private String created_by;


}
