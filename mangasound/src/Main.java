import java.io.File;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, Playlist> playlists = new HashMap<>();
    private static final Reprodutor reprodutor = new Reprodutor();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarPlaylist();
                    break;
                case 2:
                    listarPlaylists();
                    break;
                case 3:
                    selecionarPlaylist();
                    break;
                case 4:
                    reproduzir();
                    break;
                case 5:
                    pausar();
                    break;
                case 6:
                    proximaMusica();
                    break;
                case 7:
                    voltarMusica();
                    break;
                case 8:
                    editarOrdemPlaylist();
                    break;
                case 9:
                    listarMusicasRepositorio();
                    break;
                case 10:
                    adicionarMusicaDoRepositorio();
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Criar nova playlist");
        System.out.println("2. Listar playlists");
        System.out.println("3. Selecionar playlist");
        System.out.println("4. Reproduzir");
        System.out.println("5. Pausar");
        System.out.println("6. Próxima música");
        System.out.println("7. Voltar música");
        System.out.println("8. Editar ordem da playlist");
        System.out.println("9. Listar músicas do repositório");
        System.out.println("10. Adicionar música do repositório a uma playlist");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void criarPlaylist() {
        System.out.print("Digite o nome da nova playlist: ");
        String nome = scanner.nextLine();
        playlists.put(nome, new Playlist(nome));
        System.out.println("Playlist criada com sucesso.");
    }

    private static void listarPlaylists() {
        System.out.println("\n--- PLAYLISTS ---");
        if (playlists.isEmpty()) {
            System.out.println("Nenhuma playlist criada.");
        } else {
            playlists.keySet().forEach(System.out::println);
        }
    }

    private static void selecionarPlaylist() {
        System.out.print("Digite o nome da playlist: ");
        String nome = scanner.nextLine();
        Playlist playlist = playlists.get(nome);
        if (playlist != null) {
            reprodutor.selecionarPlaylist(playlist);
            System.out.println("Playlist selecionada.");
        } else {
            System.out.println("Playlist não encontrada.");
        }
    }

    private static void reproduzir() {
        reprodutor.reproduzir();
    }

    private static void pausar() {
        reprodutor.pausar();
    }

    private static void proximaMusica() {
        reprodutor.proxima();
    }

    private static void voltarMusica() {
        reprodutor.voltar();
    }

    private static void editarOrdemPlaylist() {
        System.out.print("Digite o nome da playlist: ");
        String nome = scanner.nextLine();
        Playlist playlist = playlists.get(nome);
        if (playlist != null) {
            playlist.listarMusicas();
            System.out.print("Digite o índice da música que quer mover: ");
            int de = scanner.nextInt();
            System.out.print("Digite o novo índice: ");
            int para = scanner.nextInt();
            scanner.nextLine();
            playlist.trocarOrdem(de, para);
        } else {
            System.out.println("Playlist não encontrada.");
        }
    }

    private static void listarMusicasRepositorio() {
        File pasta = new File("repositorio");
        File[] arquivos = pasta.listFiles((dir, nome) -> nome.toLowerCase().endsWith(".wav"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhuma música encontrada no repositório.");
            return;
        }

        System.out.println("\n--- MÚSICAS DISPONÍVEIS NO REPOSITÓRIO ---");
        for (int i = 0; i < arquivos.length; i++) {
            System.out.println(i + " - " + arquivos[i].getName());
        }
    }

    private static void adicionarMusicaDoRepositorio() {
        listarMusicasRepositorio();

        System.out.print("Digite o nome da playlist: ");
        String nomePl = scanner.nextLine();
        Playlist playlist = playlists.get(nomePl);

        if (playlist == null) {
            System.out.println("Playlist não encontrada.");
            return;
        }

        System.out.print("Número da música que deseja adicionar: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        File[] arquivos = new File("repositorio").listFiles((dir, nome) -> nome.endsWith(".wav"));
        if (arquivos != null && index >= 0 && index < arquivos.length) {
            File musicaSelecionada = arquivos[index];
            playlist.adicionarMusica(new Musica(musicaSelecionada.getName(), musicaSelecionada.getPath()));
            System.out.println("Música adicionada!");
        } else {
            System.out.println("Índice inválido.");
        }
    }
}