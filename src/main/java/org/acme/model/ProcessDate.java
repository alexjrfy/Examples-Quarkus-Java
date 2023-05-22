package org.acme.model;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ProcessDate {
    private Instant dataProcess;
}
