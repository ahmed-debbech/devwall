package com.debbech.generator.database;

import com.debbech.generator.model.ai.WriteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IWriteRequestRepo extends JpaRepository<WriteRequest, Long> {


    @Query(value = "select * from write_request where title_hash = :hash;", nativeQuery = true)
    Optional<WriteRequest> retrieveByHash(@Param("hash") String sha1);
}
