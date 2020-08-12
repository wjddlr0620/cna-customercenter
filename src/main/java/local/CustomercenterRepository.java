package local;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomercenterRepository extends CrudRepository<Customercenter, Long> {

    List<Customercenter> findByOrderId(Long orderId);

}