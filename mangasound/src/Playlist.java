import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {
    private String nome;
    private List<Musica> musicas;

    public Playlist(String nome) {
        this.nome = nome;
        this.musicas = new ArrayList<>();
    }

    public void adicionarMusica(Musica musica) {
        musicas.add(musica);
    }

    public void trocarOrdem(int de, int para) {
        if (de >= 0 && de < musicas.size() && para >= 0 && para < musicas.size()) {
            Collections.swap(musicas, de, para);
            System.out.println("Ordem trocada com sucesso.");
        } else {
            System.out.println("Índices inválidos.");
        }
    }

    public void listarMusicas() {
        System.out.println("\n--- MÚSICAS NA PLAYLIST: " + nome + " ---");
        for (int i = 0; i < musicas.size(); i++) {
            System.out.println(i + " - " + musicas.get(i).getNome());
        }
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public String getNome() {
        return nome;
    }
}