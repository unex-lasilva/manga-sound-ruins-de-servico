import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Reprodutor {
    private Playlist playlistAtual;
    private int indexAtual = 0;
    private Clip clip;
    private long pausa = 0;

    public void selecionarPlaylist(Playlist playlist) {
        parar();
        this.playlistAtual = playlist;
        this.indexAtual = 0;
    }

    public void reproduzir() {
        if (playlistAtual == null || playlistAtual.getMusicas().isEmpty()) {
            System.out.println("Nenhuma playlist selecionada ou playlist vazia.");
            return;
        }

        try {
            if (clip != null && clip.isOpen()) {
                clip.setMicrosecondPosition(pausa);
                clip.start();
                System.out.println("Música retomada.");
                return;
            }

            Musica musica = playlistAtual.getMusicas().get(indexAtual);
            File arquivo = new File(musica.getCaminho());

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivo);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            System.out.println("Tocando: " + musica.getNome());

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Erro ao reproduzir música: " + e.getMessage());
        }
    }

    public void pausar() {
        if (clip != null && clip.isRunning()) {
            pausa = clip.getMicrosecondPosition();
            clip.stop();
            System.out.println("Música pausada.");
        }
    }

    public void proxima() {
        if (playlistAtual == null) return;

        parar();
        if (indexAtual < playlistAtual.getMusicas().size() - 1) {
            indexAtual++;
            reproduzir();
        } else {
            System.out.println("Fim da playlist.");
        }
    }

    public void voltar() {
        if (clip != null && clip.isRunning() && clip.getMicrosecondPosition() > 10_000_000) {
            // Mais de 10 segundos (10.000.000 microssegundos)
            parar();
            reproduzir();
        } else {
            if (indexAtual > 0) {
                parar();
                indexAtual--;
                reproduzir();
            } else {
                System.out.println("Já está na primeira música.");
            }
        }
    }

    private void parar() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clip = null;
            pausa = 0;
        }
    }
}