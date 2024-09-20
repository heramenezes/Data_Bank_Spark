package app;

import static spark.Spark.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import service.MusicasService;


public class Main {

	    private static MusicasService musicasService = new MusicasService();

	    public static void main(String[] args) {
	        port(4567);

	        get("/", (request, response) -> {
	            response.type("text/html");
	            return renderHTMLForm();
	        });

	        post("/musicas", (request, response) -> musicasService.add(request, response));

	        post("/musicas/get", (request, response) -> musicasService.get(request, response));

	        post("/musicas/update", (request, response) -> musicasService.update(request, response));

	        post("/musicas/delete", (request, response) -> musicasService.remove(request, response));

	        post("/musicas/all", (request, response) -> musicasService.getAll(request, response));
	    }

	    private static String renderHTMLForm() {
	        try {
	            return new String(Files.readAllBytes(Paths.get("src/main/resources/formulario_musica.html")));
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Erro ao carregar o formulário.";
	        }
	    }

}
