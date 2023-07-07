package br.com.compra.repository;

import br.com.compra.model.Compra;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompraRepository extends MongoRepository<Compra, Long> {
}
