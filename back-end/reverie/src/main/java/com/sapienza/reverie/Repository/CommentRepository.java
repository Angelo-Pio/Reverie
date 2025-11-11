package com.sapienza.reverie.Repository;

import com.sapienza.reverie.Model.Charm;
import com.sapienza.reverie.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
        SELECT c.charm
        FROM Comment c
        WHERE c.created_at <= :timestamp and c.user.id = :user
        GROUP BY c.charm
        ORDER BY MAX(c.created_at) DESC
    """)
    List<Charm> findMostRecentlyCommentedCharms(@Param("timestamp") LocalDateTime timestamp, @Param("user") Long user_id);

}
