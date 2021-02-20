package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void sendResponse(HttpServletResponse response,
                                    Object obj) {
        response.setContentType("application/json");
        try {
            String body = objectMapper.writeValueAsString(obj);
            PrintWriter out = response.getWriter();

            out.print(body);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
