package com.jgarciahweb.elfutbolconjavi.application.services;

import reactor.core.publisher.Mono;

public interface CommandHandler<C, R> {
    Mono<R> execute(C command);
}
