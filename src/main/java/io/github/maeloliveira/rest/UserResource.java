package io.github.maeloliveira.rest;

import io.github.maeloliveira.domain.model.User;
import io.github.maeloliveira.domain.repository.UserRepository;
import io.github.maeloliveira.rest.dto.CreateUserRequest;
import io.github.maeloliveira.rest.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/users")
@Consumes({"application/json"})
@Produces({"application/json"})
public class UserResource {
    private final UserRepository userRepository;
    private final Validator validator;

    @Inject
    public UserResource(UserRepository userRepository, Validator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {
        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
        if (!violations.isEmpty()) {
            ResponseError responseError = ResponseError.crateFromValidation(violations);
            return Response.status(422).entity(responseError).build();
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());
        this.userRepository.persist(user);
        return Response.status(Response.Status.CREATED.getStatusCode()).entity(user).build();
    }

    @GET
    public Response listAllUsers() {
        PanacheQuery<User> queryList = this.userRepository.findAll();
        return Response.ok(queryList.list()).build();
    }

    @GET
    @Path("{id}")
    public Response findUserById(@PathParam("id") Long id) {
        User user = this.userRepository.findById(id);
        if (user != null)
            return Response.ok(user).build();
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {
        User user = this.userRepository.findById(id);
        if (user != null) {
            this.userRepository.delete(user);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData) {
        User user = this.userRepository.findById(id);
        if (user != null) {
            user.setName(userData.getName());
            user.setAge(userData.getAge());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
