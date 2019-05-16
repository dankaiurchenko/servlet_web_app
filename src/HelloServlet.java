import com.danarossa.router.Router;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    private Router router = new Router();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            this.router.call(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(response.SC_FORBIDDEN, "Token expired");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        this.router.call(request,response);
    }

}
