package org.example.entity;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

@MappedSuperclass   // tell that it is a super class
public interface BaseEntity<T extends Serializable> {

    T getId();

    void setId(T id);
}
