package com.jgarciahweb.elfutbolconjavi.application.port.out;

import com.jgarciahweb.elfutbolconjavi.domain.User;

public interface SaveUserPort {
    User save(User user);
}
