import com.danarossa.controllers.ControllerException;
import com.danarossa.router.Router;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    private Router router = new Router();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            System.out.println("inside of doPost");
            this.router.call(request,response);
        } catch (ControllerException e) {
            e.printStackTrace();
            response.sendError(response.SC_FORBIDDEN, "Token expired");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("inside of doGet");
        try {
            this.router.call(request,response);
        } catch (ControllerException e) {
            response.sendError(response.SC_FORBIDDEN, "Token expired");
        }
    }


}
