package io.github.maeloliveira.rest;

import io.github.maeloliveira.domain.model.Post;
import io.github.maeloliveira.domain.model.User;
import io.github.maeloliveira.domain.repository.PostRepository;
import io.github.maeloliveira.domain.repository.UserRepository;
import io.github.maeloliveira.rest.dto.CreatePostRequest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes({"application/json"})
@Produces({"application/json"})
public class PostResource {

    private final UserRepository userRepository;
    private final PostRepository repository;

    @Inject
    public PostResource(UserRepository userRepository, PostRepository repository) {
        this.userRepository = userRepository;
        this.repository = repository;
    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        repository.persist(post);

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
