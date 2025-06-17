package Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = "utilisateur-service")
public interface UserClient {
    @GetMapping("/utilisateurs")
    List<Object> getAllUsers();
}
