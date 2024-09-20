package model;

public class Musicas {
    private int id;
    private String nome;
    private String genero;
    private String artista;
    private int ano;

    // Construtor com todos os parâmetros
    public Musicas(int id, String nome, String genero, String artista, int ano) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.artista = artista;
        this.ano = ano;
    }

    // Construtor sem o ID (para inserção de novos registros)
    public Musicas(String nome, String genero, String artista, int ano) {
        this.nome = nome;
        this.genero = genero;
        this.artista = artista;
        this.ano = ano;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    // Método toString para facilitar a exibição de informações da música
    @Override
    public String toString() {
        return "Musicas{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", genero='" + genero + '\'' +
                ", artista='" + artista + '\'' +
                ", ano=" + ano +
                '}';
    }
}
