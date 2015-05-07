package com.kartashov.rest.repositories;

import com.kartashov.rest.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface UnitRepository extends JpaRepository<Unit, Long> {
}
