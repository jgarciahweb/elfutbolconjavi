package com.jgarciahweb.elfutbolconjavi.domain.validation;

import reactor.core.publisher.Mono;

public interface ConstraintChecker<T> {
    Mono<T> check(T command);
}