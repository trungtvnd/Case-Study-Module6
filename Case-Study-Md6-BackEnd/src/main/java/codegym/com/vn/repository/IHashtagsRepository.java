package codegym.com.vn.repository;

import codegym.com.vn.model.HashTags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHashtagsRepository extends JpaRepository<HashTags, Long> {

}
