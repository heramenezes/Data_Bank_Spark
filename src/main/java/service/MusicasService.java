package service;

import dao.MusicasDAO;
import model.Musicas;
import spark.Request;
import spark.Response;

import java.util.List;

public class MusicasService {

    private MusicasDAO musicasDAO;

    // Construtor da classe MusicasService
    public MusicasService() {
        musicasDAO = new MusicasDAO(); // Inicializa o DAO para operações no banco de dados
    }

    // Adiciona uma nova música
    public Object add(Request request, Response response) {
        String nome = request.queryParams("nome");
        String genero = request.queryParams("genero");
        String artista = request.queryParams("artista");
        int ano = Integer.parseInt(request.queryParams("ano"));

        Musicas musica = new Musicas(nome, genero, artista, ano);
        musicasDAO.insert(musica); // Insere a nova música no banco de dados

        response.status(201); // Status HTTP 201: Created
        return "<html><body><div class='message'>Música cadastrada com sucesso!</div></body></html>";
    }

    // Obtém detalhes de uma música específica pelo ID
    public String get(Request request, Response response) {
        String idParam = request.queryParams("id");

        // Verifica se o ID foi fornecido
        if (idParam == null || idParam.isEmpty()) {
            return "<html><body><div class='error'>Erro: ID não fornecido.</div></body></html>";
        }

        try {
            int id = Integer.parseInt(idParam);
            Musicas musica = musicasDAO.get(id); // Obtém a música pelo ID

            if (musica == null) {
                return "<html><body><div class='error'>Erro: Música não encontrada.</div></body></html>";
            }

            // Retorna os detalhes da música em HTML
            StringBuilder retorno = new StringBuilder();
            retorno.append("<html><head><style>");
            retorno.append("body { font-family: Arial, sans-serif; margin: 20px; padding: 0; background-color: #f4f4f4; }");
            retorno.append("div { background: #fff; margin: 10px 0; padding: 15px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
            retorno.append(".message { margin-top: 20px; padding: 10px; background-color: #e7f4e4; border: 1px solid #d4e6d0; border-radius: 4px; color: #2e8b57; }");
            retorno.append(".error { background-color: #f4e4e4; border: 1px solid #e6d0d0; color: #d9534f; }");
            retorno.append("</style></head><body>");
            
            retorno.append("<div>");
            retorno.append("Nome: ").append(musica.getNome()).append("<br><br>");
            retorno.append("Gênero: ").append(musica.getGenero()).append("<br><br>");
            retorno.append("Artista: ").append(musica.getArtista()).append("<br><br>");
            retorno.append("Ano: ").append(musica.getAno());
            retorno.append("</div>");
            
            retorno.append("</body></html>");

            return retorno.toString();
        } catch (NumberFormatException e) {
            // Retorna uma mensagem de erro se o ID for inválido
            return "<html><body><div class='error'>Erro: ID inválido.</div></body></html>";
        }
    }

    // Atualiza os detalhes de uma música existente
    public Object update(Request request, Response response) {
        String idParam = request.queryParams("id");
        String nome = request.queryParams("nome");
        String genero = request.queryParams("genero");
        String artista = request.queryParams("artista");
        String anoParam = request.queryParams("ano");

        // Verifica se o ID foi fornecido
        if (idParam == null || idParam.isEmpty()) {
            response.status(400); // Status HTTP 400: Bad Request
            return "<html><body><div class='error'>Erro: ID não fornecido.</div></body></html>";
        }

        try {
            int id = Integer.parseInt(idParam);
            Musicas musica = musicasDAO.get(id); // Obtém a música pelo ID

            if (musica != null) {
                // Atualiza os campos apenas se os novos valores não forem nulos
                if (nome != null && !nome.isEmpty()) {
                    musica.setNome(nome);
                }
                if (genero != null && !genero.isEmpty()) {
                    musica.setGenero(genero);
                }
                if (artista != null && !artista.isEmpty()) {
                    musica.setArtista(artista);
                }
                if (anoParam != null && !anoParam.isEmpty()) {
                    musica.setAno(Integer.parseInt(anoParam));
                }

                musicasDAO.update(musica); // Atualiza a música no banco de dados

                return "<html><body><div class='message'>Música atualizada com sucesso!</div></body></html>";
            } else {
                response.status(404); // Status HTTP 404: Not Found
                return "<html><body><div class='error'>Música não encontrada!</div></body></html>";
            }
        } catch (NumberFormatException e) {
            // Retorna uma mensagem de erro se o ID ou o ano forem inválidos
            response.status(400); // Status HTTP 400: Bad Request
            return "<html><body><div class='error'>Erro: ID ou ano inválidos.</div></body></html>";
        }
    }

    // Remove uma música pelo ID
    public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id")); // Obtém o ID da música
        Musicas musica = musicasDAO.get(id); // Obtém a música pelo ID

        if (musica != null) {
            musicasDAO.delete(id); // Remove a música do banco de dados
            return "<html><body><div class='message'>Música removida com sucesso!</div></body></html>";
        } else {
            response.status(404); // Status HTTP 404: Not Found
            return "<html><body><div class='error'>Música não encontrada!</div></body></html>";
        }
    }

    // Obtém todas as músicas cadastradas
    public Object getAll(Request request, Response response) {
        List<Musicas> musicas = musicasDAO.getAll(); // Obtém todas as músicas do banco de dados
        StringBuilder retorno = new StringBuilder();

        // Monta a resposta HTML
        retorno.append("<html><head><style>");
        retorno.append("body { font-family: Arial, sans-serif; margin: 20px; padding: 0; background-color: #f4f4f4; }");
        retorno.append("ul { list-style-type: none; padding: 0; }");
        retorno.append("li { background: #fff; margin: 10px 0; padding: 15px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
        retorno.append(".message { margin-top: 20px; padding: 10px; background-color: #e7f4e4; border: 1px solid #d4e6d0; border-radius: 4px; color: #2e8b57; }");
        retorno.append(".error { background-color: #f4e4e4; border: 1px solid #e6d0d0; color: #d9534f; }");
        retorno.append("</style></head><body>");
        
        retorno.append("<h2>Todas as Músicas</h2>");
        
        if (musicas.isEmpty()) {
            retorno.append("<p>Não há músicas cadastradas.</p>");
        } else {
            retorno.append("<ul>");
            for (Musicas musica : musicas) {
                retorno.append("<li>");
                retorno.append("ID: ").append(musica.getId()).append("<br><br>");
                retorno.append("Nome: ").append(musica.getNome()).append("<br><br>");
                retorno.append("Gênero: ").append(musica.getGenero()).append("<br><br>");
                retorno.append("Artista: ").append(musica.getArtista()).append("<br><br>");
                retorno.append("Ano: ").append(musica.getAno());
                retorno.append("</li>");
            }
            retorno.append("</ul>");
        }
        
        retorno.append("</body></html>");

        return retorno.toString();
    }
}
