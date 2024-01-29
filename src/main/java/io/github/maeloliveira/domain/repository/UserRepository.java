package io.github.maeloliveira.domain.repository;

import io.github.maeloliveira.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Long> {
    public User ListNames(String name){
        return find("name", name).firstResult();
    }

    public List<User> findAlive(){
        return list("status");
    }
}
