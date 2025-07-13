package KritimBackend.KritimBackend.repository;

import KritimBackend.KritimBackend.model.Notices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepo extends JpaRepository<Notices, Long> {

    @Query("SELECT n FROM Notices n WHERE n.Publisher.userId = :userId")
    List<Notices> findByPublisherId(@Param("userId") Long userId);

    @Query("SELECT n FROM Notices n WHERE n.Title = :title AND n.Publisher.userId = :userId")
    List<Notices> findByTitleAndPublisherId(@Param("title") String title, @Param("userId") Long userId);
}