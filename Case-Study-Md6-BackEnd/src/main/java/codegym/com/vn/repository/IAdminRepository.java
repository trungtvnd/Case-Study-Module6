package codegym.com.vn.repository;


import codegym.com.vn.model.Post;
import codegym.com.vn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdminRepository extends JpaRepository<User,Long> {
}
