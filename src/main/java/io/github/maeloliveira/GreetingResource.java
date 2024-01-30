package io.github.maeloliveira;

import io.github.maeloliveira.domain.model.User;
import io.github.maeloliveira.domain.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/social")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @Inject
    UserRepository userRepository;

    @GET
    public PanacheQuery<User> list(String name) {
        return (PanacheQuery<User>) userRepository.listNames(name);
    }

    @GET
    @Path("/status")
    public PanacheQuery<User> listAlive() {
        return (PanacheQuery<User>) userRepository.findAlive();
    }

}
