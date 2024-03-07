package io.github.maeloliveira.rest;

import io.github.maeloliveira.domain.model.Follower;
import io.github.maeloliveira.domain.repository.FollowerRepository;
import io.github.maeloliveira.domain.repository.UserRepository;
import io.github.maeloliveira.rest.dto.FollowerPerUserResponse;
import io.github.maeloliveira.rest.dto.FollowerRequest;
import io.github.maeloliveira.rest.dto.FollowerResponse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private final FollowerRepository repository;
    private final UserRepository userRepository;

    @Inject
    public FollowerResource(FollowerRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @PUT
    @Transactional
    public Response followUser(@PathParam("userId") Long userId, FollowerRequest request) {

        if (userId.equals(request.getFollowerId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("You're can't follow yourself")
                    .build();
        }

        var user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        var follower = userRepository.findById(request.getFollowerId());

        boolean follows = repository.follows(follower, user);

        if (!follows) {
            var entity = new Follower();
            entity.setUser(user);
            entity.setFollower(follower);

            repository.persist(entity);
        }

        return Response.status(Response.Status.NO_CONTENT).build();

    }

    @GET
    public Response listFollowers(@PathParam("userId") Long userId) {
        var list = repository.findByUser(userId);
        FollowerPerUserResponse responseObject = new FollowerPerUserResponse();
        responseObject.setFollowerCount(list.size());

        var followerlist = list.stream()
                .map(FollowerResponse::new)
                .collect(Collectors.toList());

        responseObject.setContent(followerlist);
        return Response.ok(responseObject).build();
    }

    @DELETE
    @Transactional
    public Response unfollowUser(@PathParam("userId") Long userId,
                                 @QueryParam("followerId") Long followerId) {
        var user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        repository.deleteByFollowerAndUser(followerId, userId);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
