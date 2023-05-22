//package org.acme;
//
//import io.smallrye.mutiny.Uni;
//import io.smallrye.mutiny.groups.UniCreate;
//import jakarta.ws.rs.Consumes;
//import jakarta.ws.rs.GET;
//import jakarta.ws.rs.Path;
//import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.core.MediaType;
//import org.acme.dto.PokemonDTO;
//import org.acme.model.Pokemon;
//
//import java.time.Instant;
//
//@Path("/hello")
//public class GreetingResource {
//    @GET
//    @Consumes("application/json")
//    @Produces("application/json")
//    public Uni<Pokemon> hello() {
//        PokemonDTO pokemonDTO = new PokemonDTO();
//        pokemonDTO.setNumber(1);
//        pokemonDTO.setName("Bulbasaur");
//
//        var uni = Uni.createFrom()
//                .item(pokemonDTO)
//                .map(x -> x)
//        ;
//
//        var uni2 = Uni.createFrom()
//                .item(Instant.now())
//                .map(x -> x)
//        ;
//
//        var uni3 = Uni.combine()
//                .all()
//                .unis(uni, uni2)
//                .combinedWith(this::transformEntity)
//                .map(x -> x)
//                ;
//
//        return uni3;
//    }
//
//    private Pokemon transformEntity(PokemonDTO pokemonDTO, Instant dateNow){
//        Pokemon pokemon = new Pokemon();
//        pokemon.setId((int)Math.random());
//        pokemon.setNumber(pokemonDTO.getNumber());
//        pokemon.setName(pokemonDTO.getName());
//        pokemon.setCreatedAt(dateNow);
//        pokemon.setUpdatedAt(dateNow);
//        pokemon.setCustom("alex");
//
//        return pokemon;
//    }
//}
