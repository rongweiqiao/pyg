package Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Test1 {
    @RequestMapping("test")
    public String findName(HttpServletRequest request){
        System.out.println(request.getRemoteUser());
        return request.getRemoteUser();
    }
}
