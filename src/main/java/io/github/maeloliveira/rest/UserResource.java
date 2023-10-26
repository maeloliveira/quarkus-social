package io.github.maeloliveira.rest;

import io.github.maeloliveira.domain.model.User;
import io.github.maeloliveira.domain.model.repository.UserRepository;
import io.github.maeloliveira.rest.dto.CreateUserRequest;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.beans.Transient;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserRepository userRepository;

    @Inject
    public UserResource (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());
        userRepository.persist(user);
        return Response.ok(user).build();
    }

    @GET
    public Response listAllUsers() {
        PanacheQuery<User> queryList = userRepository.findAll();
        return Response.ok(queryList.list()).build();
    }
}