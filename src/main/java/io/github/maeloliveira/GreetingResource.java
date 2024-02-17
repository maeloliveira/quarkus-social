package io.github.maeloliveira;

import io.github.maeloliveira.domain.repository.UserRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/social")
@Produces({"application/json"})
@Consumes({"application/json"})

public class GreetingResource {
    @Inject
    UserRepository userRepository;

    public GreetingResource() {
    }

    @GET
    public PanacheQuery list(String name) {
        return (PanacheQuery) this.userRepository.listNames(name);
    }
    @GET
    @Path("/status")
    public PanacheQuery listAlive() {
        return (PanacheQuery) this.userRepository.findAlive();
    }
}
