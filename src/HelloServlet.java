import com.danarossa.router.Router;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HelloServlet")
public class HelloServlet extends HttpServlet {

    private Router router = new Router();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.router.call(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        this.router.call(request,response);
    }

}
