package io.github.maeloliveira.rest;

import io.github.maeloliveira.domain.model.User;
import io.github.maeloliveira.domain.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes({"application/json"})
@Produces({"application/json"})
public class PostResource {

    private final UserRepository userRepository;

    @Inject
    public PostResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @POST
    public Response savePost(@PathParam("userId") Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPosts(@PathParam("userId") Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }
}
