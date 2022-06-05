package com.example.democloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.democloud.domain.Contato;

@Repository
public interface ContatoRepository extends CrudRepository<Contato, Long> {

}
