package com.github.csifinos.wsendpoint.bonus;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceCounterRepository extends CrudRepository<SequenceCounter, String> {
}

