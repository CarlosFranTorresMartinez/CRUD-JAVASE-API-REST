package connector;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Student;

public class StudentConnector {

    private static final Logger log = Logger.getLogger(StudentConnector.class.toString());

    private HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    public void saveStudent(Object object) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/student/create"))
                    .POST(HttpRequest.BodyPublishers.ofString(payload(object)))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse httpResponse = httpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());

            switch (httpResponse.statusCode()) {
                case 200:
                    JOptionPane.showMessageDialog(null, "Registro Completo");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Error al registrar al estudiante.");
            }

        } catch (ExceptionInInitializerError | IOException | InterruptedException e) {
            log.log(Level.SEVERE, e.toString());
        }
    }

    public List<Student> findAll() {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/student/all"))
                    .GET()
                    .header("Content-Type", "application/json")
                    .build();

            Type userListType = new TypeToken<List<Student>>() {
            }.getType();

            HttpResponse httpResponse = httpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return data(httpResponse.body(), userListType);
        } catch (IOException | InterruptedException e) {
            return Collections.emptyList();
        }
    }

    public void updateStudent(Object object) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/v1/student/update"))
                    .PUT(HttpRequest.BodyPublishers.ofString(payload(object)))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse httpResponse = httpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());

            switch (httpResponse.statusCode()) {
                case 200:
                    JOptionPane.showMessageDialog(null, "Actualiacion Completa");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Error al registrar al estudiante.");
            }

        } catch (ExceptionInInitializerError | IOException | InterruptedException e) {
            log.log(Level.SEVERE, e.toString());
        }
    }

    public String payload(Object object) {
        return new Gson().toJson(object);
    }

    public List<Student> data(Object string, Type type) {
        return new Gson().fromJson(string.toString(), type);
    }
}
