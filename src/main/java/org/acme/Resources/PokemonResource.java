package org.acme.Resources;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniCreate;
import jakarta.json.Json;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.dto.PokemonDTO;
import org.acme.model.Pokemon;
import org.acme.model.ProcessDate;


import java.time.Instant;
import java.util.List;

@Path("/pokemon")
public class PokemonResource {
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public Uni<String> hello() {

        JsonNode jsonNode = mockRequestPokemon();
        Uni<ProcessDate> pd = mockProcessDateRepo();

        var mapper = Uni.createFrom()
                .item(jsonNode)
                .map(this::mapperJsonNodeToDto);

        //mapper.map(PokemonDTO::getName); --> Exemplo para retornar uma str

        var date = findDataProcess();

        var findAndUpdate = Uni.combine()
                .all()
                .unis(mapper, date)
                .combinedWith( (mp, dt) -> findPokemon(mp,dt))
                .map(x -> {
                    if(x!=null && x.getCustom()==null){
                        updateCustom(x);
                    }
                    return x;
                })
        ;

        return findAndUpdate.map(Pokemon::getCustom);
    }

    private JsonNode mockRequestPokemon(){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jNode = mapper.createObjectNode();
        jNode.put("name","Bulbasaur");
        jNode.put("number","1");

        return jNode;
    }

    private Uni<ProcessDate> mockProcessDateRepo(){
        ProcessDate pd = new ProcessDate();
        pd.setDataProcess(Instant.now());
        return Uni.createFrom().item(pd);
    }

    private Uni<Instant> findDataProcess(){
        return mockProcessDateRepo()
                .log()
                .onItem()
                .transform(ProcessDate::getDataProcess);
    }

    private PokemonDTO mapperJsonNodeToDto(JsonNode jsonNode){
        try {
            ObjectMapper mapper = new ObjectMapper();
            PokemonDTO pkmn = mapper.treeToValue(jsonNode, PokemonDTO.class);
            return pkmn;
        }catch (Exception e){
            return null;
        }
    }

    private Pokemon mockPokemonFind(){
        Pokemon pkmn = new Pokemon();
        pkmn.setId(1);
        pkmn.setName("Bulbassaur");
        pkmn.setNumber(1);
        pkmn.setCreatedAt(Instant.now());
        pkmn.setUpdatedAt(Instant.now());
        pkmn.setCustom(null);
        return pkmn;
    }

    private Pokemon findPokemon(PokemonDTO dto, Instant pd){
        Pokemon mock = mockPokemonFind();
        if(dto.getNumber()==mock.getNumber()){
            return mock;
        }else{
            return null;
        }
    }

    private Pokemon updateCustom(Pokemon pokemon){
        pokemon.setCustom("Atualizado");
        return pokemon;
    }
}
