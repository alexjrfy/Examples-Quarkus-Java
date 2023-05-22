package org.acme.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Pokemon {
    private Integer id;
    private Integer number;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private String custom;
}
