package com.sapienza.reverie.Repository;

import com.sapienza.reverie.Model.Charm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharmRepository extends JpaRepository<Charm,Long> {

}
