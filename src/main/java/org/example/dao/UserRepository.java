package org.example.dao;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.example.entity.Payment;
import org.example.entity.User;

public class UserRepository extends BaseRepository<Integer, User>{

    public UserRepository( EntityManager manager) {
        super(User.class, manager);
    }
}
