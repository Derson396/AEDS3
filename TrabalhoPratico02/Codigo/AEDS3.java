//Bibliotecas --------------------------------------------------------

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Scanner;

// MAIN-----------------------------------------------------------------
public class AEDS3 {
    static HashingExtendido he;
    static Arvore ar;

    public static void limparTela() throws InterruptedException, IOException {
        // Limpa a tela no windows, no linux e no MacOS
        if (System.getProperty("os.name").contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command("clear");
        }

    }

    public static void main(String[] args) throws Exception {
        int input = 1;
        Scanner a = new Scanner(System.in);
        limparTela();
        while (input != 0) {
            System.out.println("\n\t ================== MENU ==================\n");
            System.out.println("\t [ 1] -> Criar a base dados                   ");
            System.out.println("\t [ 2] -> Ler da base de dados                 ");
            System.out.println("\t [ 3] -> Criar novo registro                  ");
            System.out.println("\t [ 4] -> Remover um registro                  ");
            System.out.println("\t [ 5] -> Atualizar um registro                ");
            System.out.println("\t [ 6] -> Pesquisar registro pelo id           ");
            System.out.println("\t [ 7] -> Ordenação externa parametrizavel     ");
            System.out.println("\t [ 8] -> Hashing Extendido                    ");
            System.out.println("\t [ 9] -> Arvore B+                            ");
            System.out.println("\t [10] -> Lista Invertida                      ");
            System.out.println("\t [ 0] -> Sair                                 ");
            System.out.print("\n\t Escolha uma opção: ");
            input = a.nextInt();
            System.out.print(input);
            switch (input) {
                case 1:
                    limparTela();
                    Criar();
                    break;
                case 2:
                    limparTela();
                    ler();
                    break;
                case 3:
                    limparTela();
                    System.out.println("Qual o nome do filme?");
                    Filme novo = new Filme();
                    novo.setSeries_Title();
                    System.out.println("Qual a data do filme?");
                    novo.setReleased_Year();
                    System.out.println("Qual o genero?");
                    novo.setGenre(a.nextLine(), true);
                    System.out.println("Qual sua nota?");
                    novo.setIMDB_Rating();
                    CRUD.Create(novo);
                    break;
                case 4:
                    limparTela();
                    System.out.print("ID a ser apagado:");
                    if (CRUD.delete(a.nextInt())) {
                        System.out.println("\nApagado com sucesso");
                    }
                    break;
                case 5:
                    limparTela();
                    atualizar();
                    break;
                case 6:
                    limparTela();
                    CRUD.Read(a.nextInt()).imprime();
                    ;
                    break;
                case 7:
                    limparTela();
                    System.out.println("Insira quantos caminhos e quantos blocos:");
                    VariableIntercalation abc = new VariableIntercalation(a.nextInt(), a.nextInt());
                    abc.sort();
                    break;
                case 8:
                    limparTela();
                    hashung();
                    break;
                case 9:
                    limparTela();
                    Avinha();
                    break;
                case 10:
                    limparTela();
                    Listado();
                    break;
                case 0:
                    limparTela();
                    ;
                    break;
                default:
                    break;

            }
        }
        a.close();

    }

    private static void Listado() throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;
        System.out.println("\n\t===== LISTA INVERTIDA ===== ");
        System.out.println("\t [ 1] -> Ano     ");
        System.out.println("\t [ 2] -> Genero   ");
        System.out.println("\t [ 3] -> Cancelar");
        System.out.print("\t Escolha uma opção: ");

        do {
            try {
                opcao = sc.nextInt();
                if (opcao < 1 || opcao > 3)
                    System.out.println("\n\t Opção inválida!\n");
            } catch (Exception e) {
                System.out.print("\n\t Digite um número: ");
                sc.next();
                break;
            }
        } while (opcao < 1 || opcao > 3);

        if (opcao == 1) {
            System.out.print("\n\t Digite o Ano: ");
            int ano = sc.nextInt();
            if (Lista.listaInvertidaAno(ano))
                System.out.println("\n\t Listado com sucesso!\n");
            else
                System.out.println("\n\t Erro ao listar!\n");

        } else if (opcao == 2) {
            System.out.print("\n\t Digite a Genero: ");
            String genero = sc.next();
            if (Lista.listaInvertidaGenero(genero))
                System.out.println("\n\t Listado com sucesso!\n");
            else
                System.out.println("\n\t Erro ao listar!\n");
        } else
            System.out.println("\n\t Cancelado!\n");

        sc.close();
    }

    private static void Avinha() throws Exception {
        int opcao = 0;
        Scanner sc = new Scanner(System.in);
        ar = new Arvore(5, "../Saida/arvoreb.db");
        System.out.println("\n\t===== ArvoreB Mais ===== ");
        System.out.println("\t [ 1] -> Pesquisar (Id) ");
        System.out.println("\t [ 2] -> Mostrar Arvore ");
        System.out.println("\t [ 3] -> Cancelar       ");
        System.out.print("\t Escolha uma opção: ");

        do {
            try {
                opcao = sc.nextInt();
                if (opcao < 1 || opcao > 3)
                    System.out.println("\n\t Opção inválida!\n");
            } catch (Exception e) {
                System.out.print("\n\t Digite um número: ");
                sc.next();
                break;
            }
        } while (opcao < 1 || opcao > 3);

        if (opcao == 1) {
            System.out.println("\n\t========== PESQUISA ==========");
            System.out.print("\tChave (Id): ");
            int chav = sc.nextInt();
            System.out.println("\tPosição: " + ar.achar(chav));
            /*
             * Filme c = CRUD.Read(chav);
             * if (c == null) {
             * System.out.println("\n\t Conta de origem não encontrada!");
             * } else {
             * try {
             * int chav2 = (int) (CRUD.lerFile2(chav));
             * System.out.println("\tPosição: " + chav2);
             * System.out.println("\n");
             * } catch (Exception e) {
             * System.out.println("\n\t Erro!!\n");
             * }
             * }
             */

        } else if (opcao == 2) {
            System.out.println("\n\t========== ARVOREB+ ==========");
            ar.print();
        } else if (opcao == 3) {
        } else {
            System.out.println("\n\t Erro!!\n");
        }
        sc.close();
    }

    private static void hashung() throws Exception {
        int opcao = 0;
        Scanner sc = new Scanner(System.in);
        he = new HashingExtendido(4, "../Saida/diretorio.hash.db", "../Saida/Buckets.hash.db");
        System.out.println("\n\t===== Hash Estendido ===== ");
        System.out.println("\t [ 1] -> Pesquisar (Id)            ");
        System.out.println("\t [ 2] -> Mostrar Hash              ");
        System.out.println("\t [ 3] -> Cancelar                  ");
        System.out.print("\t Escolha uma opção:          ");
        do {
            try {
                opcao = sc.nextInt();
                if (opcao < 1 || opcao > 3)
                    System.out.println("\n\t Opção inválida!\n");
            } catch (Exception e) {
                System.out.print("\n\t Digite um número: ");
                sc.next();
                break;
            }
        } while (opcao < 1 || opcao > 3);

        if (opcao == 1) {
            System.out.println("\n\t========== PESQUISA ==========");
            System.out.print("\tChave (Id): ");
            int chav = sc.nextInt();
            System.out.println("\tPosição: " + (int) he.read(chav));
        } else if (opcao == 2) {
            System.out.println("\n\t========== HASH ==========");
            he.print();
        } else if (opcao == 3) {
        } else {
            System.out.println("\n\t Erro!!\n");
        }
        sc.close();
    }

    /**
     * Vai criar a base de dados a partir do csv
     */
    private static void Criar() {
        String line = "";
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("../Entrada/Filmes.csv"), "UTF-8"))) {
            line = br.readLine();
            while (line != null) {
                Filme obj = new Filme(line);

                CRUD.Create(obj);

                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("File cannot be read");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws FileNotFoundException
     *                               Lê todos os registros vivos na base de dados
     */
    private static void ler() throws FileNotFoundException {
        try (RandomAccessFile arq = new RandomAccessFile("../Saida/Arquivo.db", "rw")) {
            arq.seek(4);
            int len;
            byte[] b;
            Filme trem;
            while (arq.getFilePointer() < arq.length()) {
                Byte lapide = arq.readByte();
                if (lapide == 1) {
                    len = arq.readInt();
                    b = new byte[len];
                    arq.read(b);

                    trem = new Filme();
                    trem.fromByteArray(b);
                    trem.imprime();
                } else {
                    len = arq.readInt();
                    arq.seek(arq.getFilePointer() + len);
                }
            }
            arq.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException | ParseException e) {

            e.printStackTrace();
        }
    }

    /**
     * @throws Exception
     */
    private static void atualizar() throws Exception {
        System.out.print("\tId a ser alterado:");
        Scanner abc = new Scanner(System.in);
        Filme movie = new Filme();
        movie = CRUD.Read(abc.nextInt());
        int input = 6;
        if (movie != null) {
            while (true) {
                movie.imprime();
                System.out.println("\n\t ================== ATUALIZAR ==================\n");
                System.out.println("\t [ 1] -> Nome                                 ");
                System.out.println("\t [ 2] -> Data de lançamento                   ");
                System.out.println("\t [ 3] -> Genero                               ");
                System.out.println("\t [ 4] -> Nota IMDB                            ");
                System.out.println("\t [ 0] -> Sair                                 ");
                System.out.print("\n\t Escolha uma opção: ");
                input = abc.nextInt();
                switch (input) {
                    case 1:
                        movie.setSeries_Title();
                        CRUD.update(movie);
                        break;
                    case 2:
                        movie.setReleased_Year();
                        CRUD.update(movie);
                        break;
                    case 3:
                        movie.setGenre();
                        CRUD.update(movie);
                        break;
                    case 4:
                        movie.setIMDB_Rating();
                        CRUD.update(movie);
                        break;
                    case 0:
                        return;
                    default:

                        break;
                }
                break;
            }
        }
        abc.close();
    }

}

// Classe---------------------------------------------------------------
class Filme {

    private int ID_Series;

    private String Series_Title;
    private LocalDate Released_Year, dataPadrao;
    private ArrayList<String> Genre;
    private double IMDB_Rating;

    /**
     * @throws ParseException
     *                        Construtora base
     */
    Filme() throws ParseException {
        Genre = new ArrayList<String>();
        String datinha = "1820-01-01";
        dataPadrao = LocalDate.parse(datinha);
        setID_Series(-1);
    }

    /**
     * @param line
     * @throws ParseException
     *                        Construtora a partir de uma linha do csv onde se e
     *                        copiado subStrings para o campo do obj
     */
    Filme(String line) throws ParseException {
        String datinha = "1820-01-01";
        dataPadrao = LocalDate.parse(datinha);
        Genre = new ArrayList<String>();
        int index = 0, atr_index = 0;
        // acha o nome

        if (line.charAt(index) == '\"') {
            atr_index = ++index;
            while (true) {
                index++;
                if (line.charAt(index) == '\"') {
                    setSeries_Title(line.substring(atr_index, index));
                    atr_index = ++index;
                    atr_index = ++index;
                    break;
                }
            }
        } else {
            while (true) {
                index++;
                if (line.charAt(index) == ',') {
                    setSeries_Title(line.substring(atr_index, index));
                    atr_index = ++index;
                    break;
                }
            }

        }
        // acha a data
        while (true) {
            index++;
            if (line.charAt(index) == ',') {
                setReleased_Year(GerarData(line.substring(atr_index, index)));
                atr_index = ++index;
                break;
            }
        }

        // acha os generos

        if (line.charAt(index) == '\"') {
            atr_index = ++index;
            while (true) {
                if (line.charAt(index) == '\"') {
                    Genre.add(line.substring(atr_index, index));
                    atr_index = ++index;
                    atr_index = ++index;
                    break;
                }
                if (line.charAt(index) == ',') {
                    Genre.add(line.substring(atr_index, index));
                    atr_index = ++index;
                }
                index++;
                if (line.charAt(atr_index) == ' ') {
                    atr_index++;
                }
            }
        } else {
            while (true) {
                index++;
                if (line.charAt(index) == ',') {
                    Genre.add(line.substring(atr_index, index));
                    atr_index = ++index;
                    break;
                }
            }
        }
        // achar a nota
        if (atr_index == line.length() - 1) {
            setIMDB_Rating(line.charAt(atr_index));

        } else {
            setIMDB_Rating(Double.parseDouble(line.substring(atr_index, line.length())));
        }
    }

    /**
     * Para imprimir os campos do registro
     */
    public void imprime() {
        System.out.print(
                "\tID: (" + ID_Series + ") Titulo: (" + Series_Title + ") Data de lançamento: (" + Released_Year
                        + ") Generos: (");
        for (String i : Genre) {
            System.out.print(i + " ");
        }
        System.out.println(") Nota no IMDB: (" + IMDB_Rating + ")");
    }

    /**
     * @param a
     * @param inserir
     *                Insere ou remove um genero
     */
    public void setGenre(String a, boolean inserir) {
        if (inserir) {
            Genre.add(a);
        } else {
            Genre.remove(a);
        }
    }

    /**
     * @param ano
     * @return
     * @throws ParseException
     *                        Como a base de dados contem somente o ano foi feito um
     *                        aleatorizador para o dia e o mes
     */
    public LocalDate GerarData(String ano) throws ParseException {
        String dia = Integer.toString((int) ((((Math.random() * 21)) % 27) + 1));
        String mes = Integer.toString((int) ((((Math.random() * 21)) % 11) + 1));
        if (Integer.parseInt(mes) < 10) {
            mes = "0" + mes;
        }
        if (Integer.parseInt(dia) < 10) {
            dia = "0" + dia;
        }
        String datinha = ano + "-" + mes + "-" + dia;
        LocalDate data = LocalDate.parse(datinha);
        return (data);
    }

    /**
     * @return
     * @throws IOException
     *                     Transforma o Registro em um vetor de bytes
     */
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(getID_Series());
        dos.writeUTF(getSeries_Title());
        int diferencaEmDias = Period.between(dataPadrao, Released_Year).getDays();
        int diferencaEmMes = Period.between(dataPadrao, Released_Year).getMonths();
        int diferencaEmAno = Period.between(dataPadrao, Released_Year).getYears();
        dos.writeInt(diferencaEmDias);
        dos.writeInt(diferencaEmMes);
        dos.writeInt(diferencaEmAno);
        dos.writeInt(this.Genre.size());
        for (String i : Genre) {
            dos.writeUTF(i);
        }
        dos.writeDouble(IMDB_Rating);
        return baos.toByteArray();
    }

    /**
     * @param b
     * @throws IOException
     *                     Transforma um vetor de bytes em registro
     */
    public void fromByteArray(byte[] b) throws IOException {
        Genre = new ArrayList<String>();
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        setID_Series(dis.readInt());
        setSeries_Title(dis.readUTF());
        setReleased_Year(dataPadrao.plusDays(dis.readInt()));
        setReleased_Year(Released_Year.plusMonths(dis.readInt()));
        setReleased_Year(Released_Year.plusYears(dis.readInt()));
        int n = dis.readInt();
        for (int i = 0; i < n; i++) {
            Genre.add(dis.readUTF());
        }
        setIMDB_Rating(dis.readDouble());
    }

    /**
     * Troca o titulo do Registro
     */
    public void setSeries_Title() {
        Scanner a = new Scanner(System.in);
        System.out.println("Ensira:");
        setSeries_Title(a.nextLine());
        a.close();
    }

    /**
     * Troca a data do Registro
     */
    public void setReleased_Year() {
        System.out.println("Ensira:");
        Scanner a = new Scanner(System.in);
        LocalDate.parse(a.nextLine());
        a.close();
    }

    /**
     * Altera o Genero do Registro
     */
    public void setGenre() {
        Scanner a = new Scanner(System.in);
        System.out.println("Deseja remover ou inserir(1 para inserir 0 pra remover):");
        boolean ensira;
        if (a.nextInt() == 1) {
            ensira = true;
        } else {
            ensira = false;
        }
        System.out.println("Ensira:");
        setGenre(a.nextLine(), ensira);
        a.close();
    }

    /**
     * Troca a avaliação do Registro
     */
    public void setIMDB_Rating() {
        System.out.println("Ensira:");
        Scanner a = new Scanner(System.in);
        setIMDB_Rating(a.nextDouble());
        a.close();
    }

    public Filme clone() {

        Filme cloned = null;
        try {
            cloned = new Filme();
            cloned.ID_Series = this.ID_Series;
            cloned.Series_Title = this.Series_Title;
            cloned.Released_Year = this.Released_Year;
            cloned.dataPadrao = this.dataPadrao;
            cloned.Genre = this.Genre;
            cloned.IMDB_Rating = this.IMDB_Rating;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cloned;
    }

    /*
     * Seção para os Getters e setters
     */
    public int getID_Series() {
        return ID_Series;
    }

    public void setID_Series(int iD_Series) {
        ID_Series = iD_Series;
    }

    public String getSeries_Title() {
        return Series_Title;
    }

    public void setSeries_Title(String series_Title) {
        Series_Title = series_Title;
    }

    public LocalDate getReleased_Year() {
        return Released_Year;
    }

    public void setReleased_Year(LocalDate released_Year) {
        Released_Year = released_Year;
    }

    public double getIMDB_Rating() {
        return IMDB_Rating;
    }

    public void setIMDB_Rating(double iMDB_Rating) {
        IMDB_Rating = iMDB_Rating;
    }

    public int getYear() {
        return getReleased_Year().getYear();
    }

    public int getTotalGenero() {
        return Genre.size();
    }

    public String[] Generos() {
        String[] c = new String[getTotalGenero()];
        for (int i = 0; i < c.length; i++) {
            c[i] = Genre.get(i);
        }
        return c;
    }

}

// CRUD------------------------------------------------------------------
class CRUD extends AEDS3 {
    /**
     * @param objeto
     * @param rota
     * @param certo
     * @throws Exception
     */
    public static void Create(Filme objeto, String rota, boolean certo) throws Exception {
        try (RandomAccessFile arq = new RandomAccessFile(rota, "rw")) {
            if (certo) {
                arq.seek(0);
                arq.writeInt(objeto.getID_Series());
                arq.seek(arq.length());
            }
            byte[] b = objeto.toByteArray();
            arq.seek(arq.length());
            arq.writeByte(1);// lapide
            arq.writeInt(b.length);// tamanho
            arq.write(b);// registro
            arq.close();

            long dado = (long) (CRUD.lerFile2(Integer.valueOf(objeto.getID_Series())));
            int pos = (int) (CRUD.lerFile2(objeto.getID_Series()));
            he = new HashingExtendido(4, "../Saida/diretorio.hash.db", "../Saida/Buckets.hash.db");
            ar = new Arvore(5, "../Saida/arvoreb.db");
            he.create(Integer.valueOf(objeto.getID_Series()), Long.valueOf(dado));
            ar.create(objeto.getID_Series(), pos);
        }
    }

    public static double lerFile2(int pesquisa) throws ParseException, IOException {

        double num = 0;
        RandomAccessFile raf = new RandomAccessFile("../Saida/Arquivo.db", "rw");
        try {
            raf.seek(4);
            while (raf.length() != raf.getFilePointer()) {

                double aa = raf.getFilePointer();

                if (raf.readByte() == 1) {
                    int len = raf.readInt();
                    byte[] b = new byte[len];
                    raf.read(b);

                    Filme procura = new Filme();
                    procura.fromByteArray(b);

                    if (procura.getID_Series() == pesquisa) {
                        raf.close();
                        num = aa;
                        return num;
                    }
                } else {
                    raf.skipBytes(raf.readInt());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        raf.close();
        return num;
    }

    /**
     * @param objeto
     * @param entrada
     * @throws Exception
     */
    public static void Create(Filme objeto) throws Exception {

        try (RandomAccessFile arq = new RandomAccessFile("../Saida/Arquivo.db", "rw")) {
            if (arq.length() == 0) {
                arq.writeInt(0);
            }
            arq.seek(0);
            int ultimoID = arq.readInt();
            objeto.setID_Series(++ultimoID);
            arq.seek(0);
            arq.writeInt(ultimoID);
            byte[] b = objeto.toByteArray();
            arq.seek(arq.length());
            arq.writeByte(1);// lapide
            arq.writeInt(b.length);// tamanho
            arq.write(b);// registro
            arq.close();

            long dado = (long) (CRUD.lerFile2(Integer.valueOf(objeto.getID_Series())));
            int pos = (int) (CRUD.lerFile2(objeto.getID_Series()));
            he = new HashingExtendido(4, "../Saida/diretorio.hash.db", "../Saida/Buckets.hash.db");
            ar = new Arvore(5, "../Saida/arvoreb.db");
            he.create(Integer.valueOf(objeto.getID_Series()), Long.valueOf(dado));
            ar.create(objeto.getID_Series(), pos);

        }
    }

    /**
     * @param busca
     * @param entrada
     * @return
     * @throws IOException
     * @throws ParseException
     *                        Procura um registro pelo seu id
     */
    public static Filme Read(int busca) throws IOException, ParseException {
        try (RandomAccessFile arq = new RandomAccessFile("../Saida/Arquivo.db", "rw")) {

            arq.seek(4);
            byte[] b;
            Filme procura = null;
            int len = 0;
            while (arq.getFilePointer() < arq.length()) {
                Byte lapide = arq.readByte();
                if (lapide == 1) {
                    len = arq.readInt();
                    b = new byte[len];
                    arq.read(b);

                    procura = new Filme();
                    procura.fromByteArray(b);

                    if (busca == procura.getID_Series()) {
                        return procura;
                    }
                } else {
                    len = arq.readInt();
                    arq.seek(arq.getFilePointer() + len);
                }
            }
            arq.close();
            return procura;
        }
    }

    /**
     * @param novoObj
     * @param entrada
     * @return
     * @throws Exception
     */
    public static boolean update(Filme novoObj) throws Exception {
        boolean resp = false;
        try (RandomAccessFile arq = new RandomAccessFile("../Saida/Arquivo.db", "rw")) {
            arq.seek(4);
            byte[] b;
            int len = 0;
            while ((int) arq.getFilePointer() < arq.length()) {
                Byte lapide = arq.readByte();
                if (lapide == 1) {
                    len = arq.readInt();
                    b = new byte[len];
                    Long posiçãoLap = arq.getFilePointer();
                    arq.read(b);
                    byte[] a = novoObj.toByteArray();
                    Filme procura = new Filme();
                    procura.fromByteArray(b);
                    if (novoObj.getID_Series() == procura.getID_Series()) {
                        if (a.length <= len) {
                            arq.seek(posiçãoLap);// pula pra quando começa o array de byte sendo 2 do bool e 4 do
                                                 // inteiro identificador
                            arq.write(a);
                            return true;
                        } else {
                            delete(novoObj.getID_Series());
                            Create(novoObj, "../Saida/Arquivo.db", true);
                            return true;
                        }
                    }
                } else {
                    len = arq.readInt();
                    arq.seek(arq.getFilePointer() + len);
                }
            }
            arq.close();
        }
        return resp;
    }

    /**
     * @param busca
     * @return
     * @throws Exception
     */
    public static boolean delete(int busca) throws Exception {
        boolean resp = false;
        int len;
        byte[] b;
        try (RandomAccessFile arq = new RandomAccessFile("../Saida/Arquivo.db", "rw")) {
            arq.seek(4);

            while ((int) arq.getFilePointer() < arq.length()) {
                int pos = (int) arq.getFilePointer();
                Byte lapide = arq.readByte();
                if (lapide == 1) {
                    len = arq.readInt();
                    b = new byte[len];
                    arq.read(b);
                    Filme procura = new Filme();
                    procura.fromByteArray(b);

                    if (busca == procura.getID_Series()) {
                        arq.seek(pos);
                        arq.writeByte(0);
                        ar = new Arvore(5, "../Saida/arvoreb.db");
                        pos = (int) (CRUD.lerFile2(procura.getID_Series()));
                        he = new HashingExtendido(4, "../Saida/diretorio.hash.db", "../Saida/Buckets.hash.db");
                        ar = new Arvore(5, "../Saida/arvoreb.db");
                        he.delete(procura.getID_Series());
                        ar.delete(procura.getID_Series(), pos);
                        return true;
                    }

                } else {
                    len = arq.readInt();
                    arq.seek((int) arq.getFilePointer() + len);
                }
            }

            arq.close();
        }
        return resp;
    }
}

class VariableIntercalation {
    private String fileName = "../Saida/Arquivo.db",
            fileTemp = "../Fontes/Temporario", typeTemp = ".db";
    private RandomAccessFile file;
    private int qntFiles, blockSize, lastId, numPrimRead, numPrimWrite, numTmpPrim, numTmpSec;
    private RandomAccessFile[] tempOutput, tempInput;
    private File tempFile;
    private Filme[] logs;
    private long[] remainingBytesTmp, filePos; // posicao do ponteiro em cada arquivo temporario
    private boolean[] availableFiles;

    public VariableIntercalation(int qntFiles, int blockSize) throws FileNotFoundException {
        file = new RandomAccessFile(fileName, "rw");
        this.qntFiles = qntFiles;
        this.blockSize = blockSize;
        this.tempInput = new RandomAccessFile[qntFiles];
        this.tempOutput = new RandomAccessFile[qntFiles];
        this.filePos = new long[qntFiles];
        this.availableFiles = new boolean[qntFiles];
        this.remainingBytesTmp = new long[qntFiles];
        this.numTmpPrim = -1;
        this.numTmpSec = -1;
    }

    /**
     * Variable intercalation sort
     * 
     * @throws Exception
     */
    public void sort() throws Exception {
        // distribui os registros nos arquivos temporarios
        System.out.println("Distribuindo arquivo em arquivos temporarios...");
        distribute();
        // intercala os arquivos temporarios
        System.out.println("Intercalando arquivos temporarios...");
        intercalate();

        System.out.println("Arquivo ordenado!");
    }

    /**
     * Distribute main file in n temporary files
     * 
     * @throws Exception
     */
    private void distribute() throws Exception {
        // ler o id do inicio do arquivo
        file.seek(0);
        this.lastId = file.readInt();
        numPrimRead = 0;

        startTemp(); // apenas cria os arquivos temporarios
        int index = 0;
        logs = new Filme[blockSize]; // array para ordenar os arquivos na memoria primaria

        while (!isAvaliable()) { // enquanto o arquivo nao termina
            logs = new Filme[blockSize];
            readLogs();
            sortArray(logs); // ordena bloco em memoria primaria
            for (int i = 0; i < logs.length; i++) { // escreve no arquivo temporario
                if (logs[i] != null) {
                    tempOutput[index].writeByte(1);
                    tempOutput[index].writeInt(logs[i].toByteArray().length);
                    tempOutput[index].write(logs[i].toByteArray());
                }
            }
            index = (index + 1) % qntFiles; // seleciona o proximo arquivo a armazenar o bloco
        }
        closeTemp();
        file.close();
    }

    /**
     * Intercalation of temp files to ordenate
     * 
     * @throws Exception
     */
    private void intercalate() throws Exception {
        int indexInsertion = 0;
        numPrimRead = 0;
        numPrimWrite = qntFiles;
        for (int i = 0; i < qntFiles; i++) {
            // vetores que contem arquivos que contem os registros
            tempInput[i] = new RandomAccessFile(fileTemp + (i + numPrimRead) + typeTemp, "rw");
            tempOutput[i] = new RandomAccessFile(fileTemp + (i + numPrimWrite) + typeTemp, "rw");
            filePos[i] = 0; // comeca a ler os arquivos (posicao 0)
        }

        while (!(numTmpPrim == 1 && numTmpSec == 0 || numTmpPrim == 0 && numTmpSec == 1)) { // enquanto existir apenas
                                                                                            // um
                                                                                            // arquivo para leitura ->
                                                                                            // os
                                                                                            // outros arquivos estao
                                                                                            // vazios
            mergeFiles(indexInsertion);
            numTmpPrim = filesToRead();
            if (numTmpPrim == 0) {
                toggleTempFiles();
                blockSize = blockSize * qntFiles;
                numTmpSec = filesToRead();
            }
            indexInsertion = (indexInsertion + 1) % qntFiles;
        }
        for (int i = 0; i < qntFiles; i++) {
            tempInput[i].close();
            tempOutput[i].close();
        }

        int fileNumber = getFileId(indexInsertion);

        File filmeSort = new File("../Saida/Arquivo.db");
        RandomAccessFile fileTempFinal = new RandomAccessFile(fileTemp + fileNumber + typeTemp, "rw");
        RandomAccessFile sortedFile = new RandomAccessFile(filmeSort, "rw");
        // verifica se arquivo existe
        if (filmeSort.exists()) {
            // exclui se ja existir
            sortedFile.setLength(0);
        }
        // copia do arquivo temporario pro arquivo final
        copyFile(fileTempFinal, sortedFile);
        sortedFile.close();
        fileTempFinal.close();
        // deletar arquivos temporarios
        deleteTempFiles();
    }

    // -------------------------------------- utilitarios

    /**
     * Verify if exists more data to read
     * 
     * @return boolean
     * @throws Exception
     */
    private boolean isAvaliable() throws Exception {
        return file.getFilePointer() == file.length();
    }

    /**
     * Create temp files
     */
    private void startTemp() {
        try {
            for (int i = 0; i < qntFiles; i++) {
                tempFile = new File(fileTemp + (i + numPrimRead) + typeTemp);
                if (!tempFile.exists())
                    tempFile.createNewFile();
                tempOutput[i] = new RandomAccessFile(tempFile, "rw");
            }
        } catch (IOException e) {
            System.err.println("Falha ao iniciar arquivos temporários");
            e.printStackTrace();
        }
    }

    /**
     * Close temp files
     */
    private void closeTemp() {
        try {
            for (int i = 0; i < qntFiles; i++) {
                tempOutput[i].close();
            }
        } catch (IOException e) {
            System.err.println("Falha ao finalizar conexão com arquivos temporários");
            e.printStackTrace();
        }
    }

    /**
     * Delete temp files
     */
    private void deleteTempFiles() {
        for (int i = 0; i < qntFiles * 2; i++) {
            tempFile = new File(fileTemp + i + typeTemp);
            if (tempFile.exists())
                tempFile.delete();
        }
    }

    /**
     * Swap two items fron an array
     * 
     * @param array Array of Musica
     * @param i     position to swap
     * @param j     position to swap
     */
    public void swap(Filme[] array, int i, int j) {
        Filme temp = array[i].clone();
        array[i] = array[j].clone();
        array[j] = temp.clone();
    }

    /**
     * Selection sort by name
     * 
     * @param array Array of Musica
     */
    public void sortArray(Filme[] array) { // by name
        for (int i = (array.length - 1); i > 0; i--) {
            if (array[i] != null) { // caso o array tenha espacos vazios
                for (int j = 0; j < i; j++) {
                    if (array[j] == null) {
                        array[j] = array[i];
                        array[i] = null;
                        i--;
                    }
                    if (array[i].getID_Series() < array[j].getID_Series())
                        swap(array, i, j);
                }
            }
        }
    }

    /**
     * Read and set items in an array in primary memory
     * 
     * @throws Exception
     */
    private void readLogs() throws Exception {

        try {
            for (int i = 0; i < blockSize; i++) {
                if (!isAvaliable())
                    logs[i] = readFilme(file);
                else
                    i = blockSize;
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler registros e salvar internamente");
            e.printStackTrace();
        }
    }

    /**
     * Function that returns the number of files to read internally
     * 
     * @return total de arquivos disponiveis para ler
     */
    private int filesToRead() {
        int totFiles = 0;
        try {
            for (int i = 0; i < qntFiles; i++) {
                remainingBytesTmp[i] = tempInput[i].length() - tempInput[i].getFilePointer();
                if (remainingBytesTmp[i] > 0) {
                    totFiles++;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao verificar os dados de bytes nas temps");
            e.printStackTrace();
        }
        return totFiles;
    }

    /**
     * Toggle temp files
     * 
     * @throws Exception
     */
    private void toggleTempFiles() throws Exception {
        for (int i = 0; i < qntFiles; i++) {
            tempInput[i].close();
            tempOutput[i].close();
        }
        numPrimRead = numPrimRead == 0 ? qntFiles : 0;
        numPrimWrite = numPrimWrite == 0 ? qntFiles : 0;
        for (int i = 0; i < qntFiles; i++) {
            tempInput[i] = new RandomAccessFile(fileTemp + (i + numPrimRead) + typeTemp, "rw");
            tempOutput[i] = new RandomAccessFile(fileTemp + (i + numPrimWrite) + typeTemp, "rw");
            tempOutput[i].setLength(0);
            filePos[i] = 0; // comeca a ler os arquivos (posicao 0)
        }
    }

    /**
     * Get index id from temp file
     * 
     * @param index parametro de indice do arquivo
     * @return
     */
    private int getFileId(int index) {
        if (index == 0) {
            return ((qntFiles - 1) + numPrimRead);
        } else {
            return ((index - 1) + numPrimRead);
        }
    }

    /**
     * Merge logs from n files in one file
     * 
     * @param index index do arquivo que sera escrito
     * @throws Exception caso erro
     */
    private void mergeFiles(int index) throws Exception {
        Filme[] compareFilme = new Filme[qntFiles];
        Filme wroteFilme = new Filme();
        int smallestValueIndex, wroteFilmeId = 0; // index do menor valor

        // iniciando o vetor de filmes -> armazena a primeira filmes de cada arquivo no
        // vetor
        // iniciando contador -> nenhuma filmes ainda foi colocada no arquivo de escrita
        for (int i = 0; i < qntFiles; i++) {
            if (filePos[i] < tempInput[i].length()) {
                compareFilme[i] = readFilmeMerge(tempInput[i], filePos[i]);

            }

            filePos[i] = tempInput[i].getFilePointer(); // armazena a posicao do o ponteiro dos arquivos de entrada
            availableFiles[i] = true;
        }
        // ESTA DANDO PROBLEMA NESSA REPETICAO
        while (isFilesAvailables() && !isAllFilesAllRead()) { // enquanto ainda existe bloco de algum arquivo para a
                                                              // leitura
                                                              // -> algum elemento do vetor de contador e difernete do
                                                              // tamanho do bloco

            smallestValueIndex = firstAvailableFileToMerge(); // recebe menor index do bloco ainda valido
            filePos[wroteFilmeId] = tempInput[wroteFilmeId].getFilePointer();
            // encontra a menor musica do vetor
            for (int i = 0; i < compareFilme.length; i++) {
                if (availableFiles[i] == true && filePos[i] < tempInput[i].length()) { // pula o arquivo que ja teve seu
                                                                                       // bloco
                                                                                       // todo lido
                    if (compareFilme[i].getID_Series() < compareFilme[smallestValueIndex].getID_Series()) {
                        smallestValueIndex = i;
                    }
                }
            }
            // colocar menor valor no arquivo de escrita

            tempOutput[index].writeByte(1);
            tempOutput[index].writeInt(compareFilme[smallestValueIndex].toByteArray().length);
            tempOutput[index].write(compareFilme[smallestValueIndex].toByteArray());

            wroteFilme = compareFilme[smallestValueIndex].clone();
            wroteFilmeId = smallestValueIndex;

            // se nao chegou no fim do arquivo, le a proximo filme
            if (filePos[smallestValueIndex] < tempInput[smallestValueIndex].length()) {
                // le proxima musica do arquivo inserido
                compareFilme[smallestValueIndex] = readFilmeMerge(tempInput[smallestValueIndex],
                        filePos[smallestValueIndex]);
                // se musica lida for menor que a musica escrita, desconsidera o arquivo
                if (wroteFilme.getID_Series() > compareFilme[smallestValueIndex].getID_Series())
                    availableFiles[smallestValueIndex] = false;
            }
        }
    }

    /**
     * Verify if theres at least one file available to merge
     * 
     * @return
     * @throws IOException
     */
    private boolean isFilesAvailables() throws IOException {
        int counter = 0;
        for (int i = 0; i < availableFiles.length; i++) {
            if (availableFiles[i] == false)
                counter++;
        }
        // se todos os arquivos forem false
        if (counter >= availableFiles.length)
            return false;
        else
            return true;
    }

    /**
     * Verify if all temp files are read
     * 
     * @return
     * @throws IOException
     */
    private boolean isAllFilesAllRead() throws IOException {
        boolean[] verify = new boolean[qntFiles];

        for (int i = 0; i < verify.length; i++) {
            verify[i] = false;
            if (filePos[i] >= tempInput[i].length())
                verify[i] = true;
        }
        for (int i = 0; i < verify.length; i++) {
            if (verify[i] == false)
                return false;
        }
        return true;
    }

    /**
     * Find first file available to merge
     * 
     * @return
     */
    private int firstAvailableFileToMerge() {
        for (int i = 0; i < availableFiles.length; i++) {
            if (availableFiles[i] == true)
                return i;
        }
        return -1;
    }

    /**
     * Read Music from file in a specific position
     * 
     * @param input
     * @param pos
     * @return
     * @throws Exception
     */
    private Filme readFilmeMerge(RandomAccessFile input, long pos) throws Exception {
        input.seek(pos);
        return readFilme(input);
    }

    /**
     * Read Music from file
     * 
     * @param file
     * @return
     * @throws Exception
     */
    private Filme readFilme(RandomAccessFile file) throws Exception {
        Filme reg = null;
        Byte lapide = file.readByte();
        int sizeReg = file.readInt();
        byte[] bytearray = new byte[sizeReg];
        file.read(bytearray);
        if (lapide != 0) {
            reg = new Filme();
            reg.fromByteArray(bytearray);
        }
        return reg;
    }

    /**
     * Copy file
     * 
     * @param tmp
     * @param target
     * @throws Exception
     */
    private void copyFile(RandomAccessFile tmp, RandomAccessFile target) throws Exception {
        target.writeInt(lastId);
        while (tmp.getFilePointer() != tmp.length()) {
            Filme filminho = readFilme(tmp);
            if (filminho != null) {
                target.writeByte(1);
                target.writeInt(filminho.toByteArray().length);
                target.write(filminho.toByteArray());
            }
        }
    }
}

/**
 * HashingExtendido
 */
class HashingExtendido {

    String nomeArquivoDiretorio;
    String nomeArquivoBuckets;
    RandomAccessFile arqDiretorio;
    RandomAccessFile arqBuckets;
    int quantidadeDadosPorBucket;
    Diretorio diretorio;

    HashingExtendido() {
    }

    class Bucket {

        byte profundidadeLocal; // profundidade local do Bucket
        short quantidade; // quantidade de pares presentes no Bucket
        short quantidadeMaxima; // quantidade máxima de pares que o Bucket pode conter
        int[] chaves; // sequência de chaves armazenadas no Bucket
        long[] dados; // sequência de dados correspondentes às chaves
        short bytesPorPar; // size fixo de cada par de chave/dado em bytes
        short bytesPorBucket; // size fixo do Bucket em bytes

        public Bucket(int qtdmax) throws Exception {
            this(qtdmax, 0);
        }

        public Bucket(int qtdmax, int pl) throws Exception {
            if (qtdmax > 32767)
                throw new Exception("Quantidade máxima de 32.767 elementos");
            if (pl > 127)
                throw new Exception("Profundidade local máxima de 127 bits");
            profundidadeLocal = (byte) pl;
            quantidade = 0;
            quantidadeMaxima = (short) qtdmax;
            chaves = new int[quantidadeMaxima];
            dados = new long[quantidadeMaxima];
            bytesPorPar = 12; // int + long
            bytesPorBucket = (short) (bytesPorPar * quantidadeMaxima + 3);
        }

        public byte[] toByteArray() throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeByte(profundidadeLocal);
            dos.writeShort(quantidade);
            int i = 0;
            while (i < quantidade) {
                dos.writeInt(chaves[i]);
                dos.writeLong(dados[i]);
                i++;
            }
            while (i < quantidadeMaxima) {
                dos.writeInt(0);
                dos.writeLong(0);
                i++;
            }
            return baos.toByteArray();
        }

        public void fromByteArray(byte[] ba) throws Exception {
            ByteArrayInputStream bais = new ByteArrayInputStream(ba);
            DataInputStream dis = new DataInputStream(bais);
            profundidadeLocal = dis.readByte();
            quantidade = dis.readShort();
            int i = 0;
            while (i < quantidadeMaxima) {
                chaves[i] = dis.readInt();
                dados[i] = dis.readLong();
                i++;
            }
        }

        public boolean create(int c, long d) {
            if (full())
                return false;
            int i = quantidade - 1;
            while (i >= 0 && c < chaves[i]) {
                chaves[i + 1] = chaves[i];
                dados[i + 1] = dados[i];
                i--;
            }
            i++;
            chaves[i] = c;
            dados[i] = d;
            quantidade++;
            return true;
        }

        public long read(int c) {
            if (empty())
                return -1;
            int i = 0;
            while (i < quantidade && c > chaves[i])
                i++;
            if (i < quantidade && c == chaves[i])
                return dados[i];
            else
                return -1;
        }

        public boolean update(int c, long d) {
            if (empty())
                return false;
            int i = 0;
            while (i < quantidade && c > chaves[i])
                i++;
            if (i < quantidade && c == chaves[i]) {
                dados[i] = d;
                return true;
            } else
                return false;
        }

        public boolean delete(int c) {
            if (empty())
                return false;
            int i = 0;
            while (i < quantidade && c > chaves[i])
                i++;
            if (c == chaves[i]) {
                while (i < quantidade - 1) {
                    chaves[i] = chaves[i + 1];
                    dados[i] = dados[i + 1];
                    i++;
                }
                quantidade--;
                return true;
            } else
                return false;
        }

        public boolean empty() {
            return quantidade == 0;
        }

        public boolean full() {
            return quantidade == quantidadeMaxima;
        }

        public String toString() {
            String s = "Profundidade Local: " + profundidadeLocal +
                    "\nQuantidade: " + quantidade +
                    "\n| ";
            int i = 0;
            while (i < quantidade) {
                s += chaves[i] + ";" + dados[i] + " | ";
                i++;
            }
            while (i < quantidadeMaxima) {
                s += "-;- | ";
                i++;
            }
            return s;
        }

        public int size() {
            return bytesPorBucket;
        }

    }

    class Diretorio {

        byte profundidadeGlobal;
        long[] enderecos;

        public Diretorio() throws Exception {
            profundidadeGlobal = 0;
            enderecos = new long[1];
            enderecos[0] = 0;
        }

        public boolean atualizaEndereco(int p, long e) {
            if (p > Math.pow(2, profundidadeGlobal))
                return false;
            enderecos[p] = e;
            return true;
        }

        public byte[] toByteArray() throws Exception {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeByte(profundidadeGlobal);
            int quantidade = (int) Math.pow(2, profundidadeGlobal);
            int i = 0;
            while (i < quantidade) {
                dos.writeLong(enderecos[i]);
                i++;
            }
            return baos.toByteArray();
        }

        public void fromByteArray(byte[] ba) throws Exception {

            ByteArrayInputStream bais = new ByteArrayInputStream(ba);
            DataInputStream dis = new DataInputStream(bais);
            profundidadeGlobal = dis.readByte();
            int quantidade = (int) Math.pow(2, profundidadeGlobal);
            enderecos = new long[quantidade];
            for (int i = 0; i < enderecos.length; i++) {
                enderecos[i] = dis.readLong();
            }

        }

        public String toString() {
            String s = "Profundidade global: " + profundidadeGlobal;
            int i = 0;
            int quantidade = (int) Math.pow(2, profundidadeGlobal);
            while (i < quantidade) {
                s += "\n" + i + ": " + enderecos[i];
                i++;
            }
            return s;
        }

        protected long endereco(int p) {
            if (p > Math.pow(2, profundidadeGlobal))
                return -1;
            return enderecos[p];
        }

        protected boolean duplica() throws Exception {
            if (profundidadeGlobal == 127)
                return false;
            profundidadeGlobal++;
            int q1 = (int) Math.pow(2, profundidadeGlobal - 1);
            int q2 = (int) Math.pow(2, profundidadeGlobal);
            long[] novosEnderecos = new long[q2];
            int i = 0;
            while (i < q1) {
                novosEnderecos[i] = enderecos[i];
                i++;
            }
            while (i < q2) {
                novosEnderecos[i] = enderecos[i - q1];
                i++;
            }
            enderecos = novosEnderecos;
            return true;
        }

        protected int hash(int chave) {
            return chave % (int) Math.pow(2, profundidadeGlobal);
        }

        protected int hash2(int chave, int pl) { // cálculo do hash para uma dada profundidade local
            return chave % (int) Math.pow(2, pl);
        }

    }

    public HashingExtendido(int n, String nd, String nc) throws Exception {
        quantidadeDadosPorBucket = n;
        nomeArquivoDiretorio = nd;
        nomeArquivoBuckets = nc;

        arqDiretorio = new RandomAccessFile(nomeArquivoDiretorio, "rw");
        arqBuckets = new RandomAccessFile(nomeArquivoBuckets, "rw");

        // Se o diretorio ou os Buckets estiverem vazios, cria um novo diretorio e lista
        // de Buckets
        if (arqDiretorio.length() == 0 || arqBuckets.length() == 0) {

            // Cria um novo diretorio, com profundidade de 0 bits (1 único elemento)
            diretorio = new Diretorio();
            byte[] bd = diretorio.toByteArray();
            arqDiretorio.write(bd);

            // Cria um Bucket vazio, já apontado pelo único elemento do diretorio
            Bucket c = new Bucket(quantidadeDadosPorBucket);
            bd = c.toByteArray();
            arqBuckets.seek(0);
            arqBuckets.write(bd);
        }
    }

    static int chamadas = 0;

    public boolean create(int chave, long dado) throws Exception {
        // Carrega o diretorio
        byte[] bd = new byte[(int) arqDiretorio.length()];

        arqDiretorio.seek(0);
        arqDiretorio.read(bd);

        diretorio = new Diretorio();

        diretorio.fromByteArray(bd);

        // Identifica a hash do diretorio,
        int i = diretorio.hash(chave);

        // Recupera o Bucket
        long enderecoBucket = diretorio.endereco(i);
        Bucket c = new Bucket(quantidadeDadosPorBucket);
        byte[] ba = new byte[c.size()];

        arqBuckets.seek(enderecoBucket);
        arqBuckets.read(ba);
        c.fromByteArray(ba);

        // Testa se a chave já não existe no Bucket
        if (c.read(chave) != -1)
            throw new Exception("\nChave já existe");

        // Testa se o Bucket já não está cheio
        // Se não estiver, create o par de chave e dado
        if (!c.full()) {
            // Insere a chave no Bucket e o atualiza
            c.create(chave, dado);
            arqBuckets.seek(enderecoBucket);
            arqBuckets.write(c.toByteArray());
            return true;
        }
        // Duplica o diretorio
        byte pl = c.profundidadeLocal;
        if (pl >= diretorio.profundidadeGlobal)
            diretorio.duplica();
        byte pg = diretorio.profundidadeGlobal;

        // Cria os novos Buckets, com os seus dados no arquivo de Buckets
        Bucket c1 = new Bucket(quantidadeDadosPorBucket, pl + 1);
        arqBuckets.seek(enderecoBucket);
        arqBuckets.write(c1.toByteArray());

        Bucket c2 = new Bucket(quantidadeDadosPorBucket, pl + 1);
        long novoEndereco = arqBuckets.length();
        arqBuckets.seek(novoEndereco);
        arqBuckets.write(c2.toByteArray());

        // Atualiza os dados no diretorio
        int inicio = diretorio.hash2(chave, c.profundidadeLocal);
        int deslocamento = (int) Math.pow(2, pl);
        int max = (int) Math.pow(2, pg);
        boolean troca = false;

        for (int j = inicio; j < max; j += deslocamento) {
            if (troca)
                diretorio.atualizaEndereco(j, novoEndereco);
            troca = !troca;
        }

        // Atualiza o arquivo do diretorio
        bd = diretorio.toByteArray();
        arqDiretorio.seek(0);
        arqDiretorio.write(bd);

        // Reinsere as chaves
        for (int j = 0; j < c.quantidade; j++) {
            if ((int) c.chaves[j] != -1) {
                create(c.chaves[j], c.dados[j]);
            }
        }
        create(chave, dado);
        return false;

    }

    public long read(int chave) throws Exception {

        // Carrega o diretorio
        byte[] bd = new byte[(int) arqDiretorio.length()];
        arqDiretorio.seek(0);
        arqDiretorio.read(bd);
        diretorio = new Diretorio();
        diretorio.fromByteArray(bd);

        // Identifica a hash do diretorio,
        int i = diretorio.hash(chave);

        // Recupera o Bucket
        long enderecoBucket = diretorio.endereco(i);
        Bucket c = new Bucket(quantidadeDadosPorBucket);
        byte[] ba = new byte[c.size()];
        arqBuckets.seek(enderecoBucket);
        arqBuckets.read(ba);
        c.fromByteArray(ba);

        return c.read(chave);
    }

    public boolean update(int chave, long novoDado) throws Exception {

        // Carrega o diretorio
        byte[] bd = new byte[(int) arqDiretorio.length()];
        arqDiretorio.seek(0);
        arqDiretorio.read(bd);
        diretorio = new Diretorio();
        diretorio.fromByteArray(bd);

        // Identifica a hash do diretorio,
        int i = diretorio.hash(chave);

        // Recupera o Bucket
        long enderecoBucket = diretorio.endereco(i);
        Bucket c = new Bucket(quantidadeDadosPorBucket);
        byte[] ba = new byte[c.size()];
        arqBuckets.seek(enderecoBucket);
        arqBuckets.read(ba);
        c.fromByteArray(ba);

        // atualiza o dado
        if (!c.update(chave, novoDado))
            return false;

        // Atualiza o Bucket
        arqBuckets.seek(enderecoBucket);
        arqBuckets.write(c.toByteArray());
        return true;

    }

    public boolean delete(int chave) throws Exception {

        // Carrega o diretorio
        byte[] bd = new byte[(int) arqDiretorio.length()];
        arqDiretorio.seek(0);
        arqDiretorio.read(bd);
        diretorio = new Diretorio();
        diretorio.fromByteArray(bd);

        // Identifica a hash do diretorio,
        int i = diretorio.hash(chave);

        // Recupera o Bucket
        long enderecoBucket = diretorio.endereco(i);
        Bucket c = new Bucket(quantidadeDadosPorBucket);
        byte[] ba = new byte[c.size()];
        arqBuckets.seek(enderecoBucket);
        arqBuckets.read(ba);
        c.fromByteArray(ba);

        // delete a chave
        if (!c.delete(chave))
            return false;

        // Atualiza o Bucket
        arqBuckets.seek(enderecoBucket);
        arqBuckets.write(c.toByteArray());
        return true;
    }

    public void print() {
        try {
            byte[] bd = new byte[(int) arqDiretorio.length()];
            arqDiretorio.seek(0);
            arqDiretorio.read(bd);
            diretorio = new Diretorio();
            diretorio.fromByteArray(bd);
            System.out.println("\nDIRETÓRIO ------------------");
            System.out.println(diretorio);

            System.out.println("\nBucketS ---------------------");
            arqBuckets.seek(0);
            while (arqBuckets.getFilePointer() != arqBuckets.length()) {
                Bucket c = new Bucket(quantidadeDadosPorBucket);
                byte[] ba = new byte[c.size()];
                arqBuckets.read(ba);
                c.fromByteArray(ba);
                System.out.println(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/**
 * Arvore
 */
class Arvore {

    private int ordem; // Número máximo de filhos que uma página pode conter
    private int maxElementos; // Variável igual a ordem - 1 para facilitar a clareza do código
    private int maxFilhos; // Variável igual a ordem para facilitar a clareza do código
    private RandomAccessFile arquivo; // Arquivo em que a árvore será armazenada
    private String nomeArquivo;

    // Variáveis usadas nas funções recursivas (já que não é possível passar valores
    // por referência)
    private int chave1Aux;
    private int chave2Aux;
    private long paginaAux;
    private boolean cresceu;
    private boolean diminuiu;

    // Esta classe representa uma página da árvore (folha ou não folha).
    private class Pagina {

        protected int ordem; // Número máximo de filhos que uma página pode ter
        protected int maxElementos; // Variável igual a ordem - 1 para facilitar a clareza do código
        protected int maxFilhos; // Variável igual a ordem para facilitar a clareza do código
        protected int n; // Número de elementos presentes na página
        protected int[] chaves1; // Chave 1 (principal)
        protected int[] chaves2; // Chave 2 (subordinada)
        protected long proxima; // Próxima folha, quando a página for uma folha
        protected long[] filhos; // Vetor de ponteiros para os filhos
        protected int TAMANHO_REGISTRO; // Os elementos são de tamanho fixo
        protected int TAMANHO_PAGINA; // A página será de tamanho fixo, calculado a partir da ordem

        // Construtor da página
        public Pagina(int o) throws Exception {

            // Inicialização dos atributos
            n = 0;
            ordem = o;
            maxFilhos = o;
            maxElementos = o - 1;
            chaves1 = new int[maxElementos];
            chaves2 = new int[maxElementos];
            filhos = new long[maxFilhos];
            proxima = -1;

            // Criação de uma página vázia
            for (int i = 0; i < maxElementos; i++) {
                chaves1[i] = 0;
                chaves2[i] = 0;
                filhos[i] = -1;
            }
            filhos[maxFilhos - 1] = -1;

            TAMANHO_REGISTRO = 8;
            TAMANHO_PAGINA = 4 + maxElementos * TAMANHO_REGISTRO + maxFilhos * 8 + 16;
        }

        // Retorna o vetor de bytes que representa a página para armazenamento em
        // arquivo
        protected byte[] getBytes() throws IOException {

            // Um fluxo de bytes é usado para construção do vetor de bytes
            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(ba);

            // Quantidade de elementos presentes na página
            out.writeInt(n);

            // Escreve todos os elementos
            int i = 0;
            while (i < n) {
                out.writeLong(filhos[i]);
                out.writeInt(chaves1[i]);
                out.writeInt(chaves2[i]);
                i++;
            }
            out.writeLong(filhos[i]);

            // Completa o restante da página com registros vazios
            byte[] registroVazio = new byte[TAMANHO_REGISTRO];
            while (i < maxElementos) {
                out.write(registroVazio);
                out.writeLong(filhos[i + 1]);
                i++;
            }

            // Escreve o ponteiro para a próxima página
            out.writeLong(proxima);

            // Retorna o vetor de bytes que representa a página
            return ba.toByteArray();
        }

        // Reconstrói uma página a partir de um vetor de bytes lido no arquivo
        public void setBytes(byte[] buffer) throws IOException {

            // Usa um fluxo de bytes para leitura dos atributos
            ByteArrayInputStream ba = new ByteArrayInputStream(buffer);
            DataInputStream in = new DataInputStream(ba);

            // Lê a quantidade de elementos da página
            n = in.readInt();

            // Lê todos os elementos (reais ou vazios)
            int i = 0;
            while (i < maxElementos) {
                filhos[i] = in.readLong();
                chaves1[i] = in.readInt();
                chaves2[i] = in.readInt();
                i++;
            }
            filhos[i] = in.readLong();
            proxima = in.readLong();
        }
    }

    // ------------------------------------------------------------------------------

    public Arvore() {
    }

    public Arvore(int o, String na) throws IOException {

        // Inicializa os atributos da árvore
        ordem = o;
        maxElementos = o - 1;
        maxFilhos = o;
        nomeArquivo = na;

        // Abre (ou cria) o arquivo, escrevendo uma raiz empty, se necessário.
        arquivo = new RandomAccessFile(nomeArquivo, "rw");
        if (arquivo.length() < 8)
            arquivo.writeLong(-1); // raiz empty
    }

    // Testa se a árvore está empty. Uma árvore empty é identificada pela raiz == -1
    public boolean empty() throws IOException {
        long raiz;
        arquivo.seek(0);
        raiz = arquivo.readLong();
        return raiz == -1;
    }

    // Busca recursiva por um elemento a partir da chave.
    public int[] read(int c1) throws Exception {

        // Recupera a raiz da árvore
        long raiz;
        arquivo.seek(0);
        raiz = arquivo.readLong();

        // Executa a busca recursiva
        if (raiz != -1)
            return read1(c1, raiz);
        else
            return new int[0];
    }

    // Busca recursiva.
    public int[] read1(int chave1, long pagina) throws Exception {

        if (pagina == -1)
            return new int[0];

        arquivo.seek(pagina);
        Pagina pa = new Pagina(ordem);
        byte[] buffer = new byte[pa.TAMANHO_PAGINA];
        arquivo.read(buffer);
        pa.setBytes(buffer);

        int i = 0;
        while (i < pa.n && chave1 > pa.chaves1[i]) {
            i++;
        }

        if (i < pa.n && pa.filhos[0] == -1 && chave1 == pa.chaves1[i]) {

            // Cria a lista de retorno e insere as chaves secundárias encontradas
            ArrayList<Integer> lista = new ArrayList<Integer>();
            while (chave1 <= pa.chaves1[i]) {

                if (chave1 == pa.chaves1[i])
                    lista.add(pa.chaves2[i]);
                i++;

                // Se chegar ao fim da folha, então avança para a folha seguinte
                if (i == pa.n) {
                    if (pa.proxima == -1)
                        break;
                    arquivo.seek(pa.proxima);
                    arquivo.read(buffer);
                    pa.setBytes(buffer);
                    i = 0;
                }
            }

            // Constrói o vetor de resposta
            int[] resposta = new int[lista.size()];
            for (int j = 0; j < lista.size(); j++)
                resposta[j] = (int) lista.get(j);
            return resposta;

        }

        else if (i == pa.n && pa.filhos[0] == -1) {

            // Testa se há uma próxima folha. Nesse caso, retorna um vetor vazio
            if (pa.proxima == -1)
                return new int[0];

            // Lê a próxima folha
            arquivo.seek(pa.proxima);
            arquivo.read(buffer);
            pa.setBytes(buffer);

            // Testa se a chave é a primeira da próxima folha
            i = 0;
            if (chave1 <= pa.chaves1[0]) {

                // Cria a lista de retorno
                ArrayList<Integer> lista = new ArrayList<Integer>();

                // Testa se a chave foi encontrada, e adiciona todas as chaves
                // secundárias
                while (chave1 <= pa.chaves1[i]) {
                    if (chave1 == pa.chaves1[i])
                        lista.add(pa.chaves2[i]);
                    i++;
                    if (i == pa.n) {
                        if (pa.proxima == -1)
                            break;
                        arquivo.seek(pa.proxima);
                        arquivo.read(buffer);
                        pa.setBytes(buffer);
                        i = 0;
                    }
                }

                // Constrói o vetor de respostas
                int[] resposta = new int[lista.size()];
                for (int j = 0; j < lista.size(); j++)
                    resposta[j] = (int) lista.get(j);
                return resposta;
            }

            // Se não houver uma próxima página, retorna um vetor vazio
            else
                return new int[0];
        }

        // Chave ainda não foi encontrada, continua a busca recursiva pela árvore
        if (i == pa.n || chave1 <= pa.chaves1[i])
            return read1(chave1, pa.filhos[i]);
        else
            return read1(chave1, pa.filhos[i + 1]);
    }

    // Inclusão de novos elementos na árvore.
    public boolean create(int c1, int c2) throws Exception {

        // Validação das chaves
        if (c1 < 0 || c2 < 0) {
            System.out.println("Chaves não podem ser negativas");
            return false;
        }

        // Carrega a raiz
        arquivo.seek(0);
        long pagina;
        pagina = arquivo.readLong();

        chave1Aux = c1;
        chave2Aux = c2;

        paginaAux = -1;
        cresceu = false;

        // Chamada recursiva para a inserção do par de chaves
        boolean inserido = create1(pagina);

        // Testa a necessidade de criação de uma nova raiz.
        if (cresceu) {

            // Cria a nova página que será a raiz. O ponteiro esquerdo da raiz
            // será a raiz antiga e o seu ponteiro direito será para a nova página.
            Pagina novaPagina = new Pagina(ordem);
            novaPagina.n = 1;
            novaPagina.chaves1[0] = chave1Aux;
            novaPagina.chaves2[0] = chave2Aux;
            novaPagina.filhos[0] = pagina;
            novaPagina.filhos[1] = paginaAux;

            // Acha o espaço em disco. Nesta versão, todas as novas páginas
            // são escrita no fim do arquivo.
            arquivo.seek(arquivo.length());
            long raiz = arquivo.getFilePointer();
            arquivo.write(novaPagina.getBytes());
            arquivo.seek(0);
            arquivo.writeLong(raiz);
        }

        return inserido;
    }

    // Função recursiva de inclusão.
    private boolean create1(long pagina) throws Exception {

        if (pagina == -1) {
            cresceu = true;
            paginaAux = -1;
            return false;
        }

        // Lê a página passada como referência
        arquivo.seek(pagina);
        Pagina pa = new Pagina(ordem);
        byte[] buffer = new byte[pa.TAMANHO_PAGINA];
        arquivo.read(buffer);
        pa.setBytes(buffer);

        // Busca o próximo ponteiro de descida.
        int i = 0;
        while (i < pa.n && (chave1Aux > pa.chaves1[i] || (chave1Aux == pa.chaves1[i] && chave2Aux > pa.chaves2[i]))) {
            i++;
        }

        if (i < pa.n && pa.filhos[0] == -1 && chave1Aux == pa.chaves1[i] && chave2Aux == pa.chaves2[i]) {
            cresceu = false;
            return false;
        }

        boolean inserido;
        if (i == pa.n || chave1Aux < pa.chaves1[i] || (chave1Aux == pa.chaves1[i] && chave2Aux < pa.chaves2[i]))
            inserido = create1(pa.filhos[i]);
        else
            inserido = create1(pa.filhos[i + 1]);

        if (!cresceu)
            return inserido;

        // Se tiver espaço na página, faz a inclusão nela mesmo
        if (pa.n < maxElementos) {

            // Puxa todos elementos para a direita, começando do último
            // para gerar o espaço para o novo elemento
            for (int j = pa.n; j > i; j--) {
                pa.chaves1[j] = pa.chaves1[j - 1];
                pa.chaves2[j] = pa.chaves2[j - 1];
                pa.filhos[j + 1] = pa.filhos[j];
            }

            // Insere o novo elemento
            pa.chaves1[i] = chave1Aux;
            pa.chaves2[i] = chave2Aux;
            pa.filhos[i + 1] = paginaAux;
            pa.n++;

            // Escreve a página atualizada no arquivo
            arquivo.seek(pagina);
            arquivo.write(pa.getBytes());

            // Encerra o processo de crescimento e retorna
            cresceu = false;
            return true;
        }

        // Cria uma nova página
        Pagina np = new Pagina(ordem);

        // Copia a metade superior dos elementos para a nova página,
        // considerando que maxElementos pode ser ímpar
        int meio = maxElementos / 2;
        for (int j = 0; j < (maxElementos - meio); j++) {

            // copia o elemento
            np.chaves1[j] = pa.chaves1[j + meio];
            np.chaves2[j] = pa.chaves2[j + meio];
            np.filhos[j + 1] = pa.filhos[j + meio + 1];

            // limpa o espaço liberado
            pa.chaves1[j + meio] = 0;
            pa.chaves2[j + meio] = 0;
            pa.filhos[j + meio + 1] = -1;
        }
        np.filhos[0] = pa.filhos[meio];
        np.n = maxElementos - meio;
        pa.n = meio;

        // Testa o lado de inserção
        // Caso 1 - Novo registro deve ficar na página da esquerda
        if (i <= meio) {

            // Puxa todos os elementos para a direita
            for (int j = meio; j > 0 && j > i; j--) {
                pa.chaves1[j] = pa.chaves1[j - 1];
                pa.chaves2[j] = pa.chaves2[j - 1];
                pa.filhos[j + 1] = pa.filhos[j];
            }

            // Insere o novo elemento
            pa.chaves1[i] = chave1Aux;
            pa.chaves2[i] = chave2Aux;
            pa.filhos[i + 1] = paginaAux;
            pa.n++;

            // Se a página for folha, seleciona o primeiro elemento da página
            // da direita para ser promovido, mantendo-o na folha
            if (pa.filhos[0] == -1) {
                chave1Aux = np.chaves1[0];
                chave2Aux = np.chaves2[0];
            }

            // caso contrário, promove o maior elemento da página esquerda
            // removendo-o da página
            else {
                chave1Aux = pa.chaves1[pa.n - 1];
                chave2Aux = pa.chaves2[pa.n - 1];
                pa.chaves1[pa.n - 1] = 0;
                pa.chaves2[pa.n - 1] = 0;
                pa.filhos[pa.n] = -1;
                pa.n--;
            }
        }

        // Caso 2 - Novo registro deve ficar na página da direita
        else {
            int j;
            for (j = maxElementos - meio; j > 0 && (chave1Aux < np.chaves1[j - 1]
                    || (chave1Aux == np.chaves1[j - 1] && chave2Aux < np.chaves2[j - 1])); j--) {
                np.chaves1[j] = np.chaves1[j - 1];
                np.chaves2[j] = np.chaves2[j - 1];
                np.filhos[j + 1] = np.filhos[j];
            }
            np.chaves1[j] = chave1Aux;
            np.chaves2[j] = chave2Aux;
            np.filhos[j + 1] = paginaAux;
            np.n++;

            // Seleciona o primeiro elemento da página da direita para ser promovido
            chave1Aux = np.chaves1[0];
            chave2Aux = np.chaves2[0];

            // Se não for folha, remove o elemento promovido da página
            if (pa.filhos[0] != -1) {
                for (j = 0; j < np.n - 1; j++) {
                    np.chaves1[j] = np.chaves1[j + 1];
                    np.chaves2[j] = np.chaves2[j + 1];
                    np.filhos[j] = np.filhos[j + 1];
                }
                np.filhos[j] = np.filhos[j + 1];

                // apaga o último elemento
                np.chaves1[j] = 0;
                np.chaves2[j] = 0;
                np.filhos[j + 1] = -1;
                np.n--;
            }

        }

        // Se a página era uma folha e apontava para outra folha,
        // então atualiza os ponteiros dessa página e da página nova
        if (pa.filhos[0] == -1) {
            np.proxima = pa.proxima;
            pa.proxima = arquivo.length();
        }

        // Grava as páginas no arquivos arquivo
        paginaAux = arquivo.length();
        arquivo.seek(paginaAux);
        arquivo.write(np.getBytes());

        arquivo.seek(pagina);
        arquivo.write(pa.getBytes());

        return true;
    }

    // Remoção elementos na árvore.
    public boolean delete(int chave1, int chave2) throws Exception {

        // Encontra a raiz da árvore
        arquivo.seek(0);
        long pagina;
        pagina = arquivo.readLong();

        // variável global de controle da redução do tamanho da árvore
        diminuiu = false;

        // Chama recursivamente a exclusão de registro (na chave1Aux e no
        // chave2Aux) passando uma página como referência
        boolean excluido = delete1(chave1, chave2, pagina);

        // Se a exclusão tiver sido possível e a página tiver reduzido seu tamanho,
        // por meio da fusão das duas páginas filhas da raiz, elimina essa raiz
        if (excluido && diminuiu) {

            // Lê a raiz
            arquivo.seek(pagina);
            Pagina pa = new Pagina(ordem);
            byte[] buffer = new byte[pa.TAMANHO_PAGINA];
            arquivo.read(buffer);
            pa.setBytes(buffer);

            // Se a página tiver 0 elementos, apenas atualiza o ponteiro para a raiz,
            // no cabeçalho do arquivo, para o seu primeiro filho.
            if (pa.n == 0) {
                arquivo.seek(0);
                arquivo.writeLong(pa.filhos[0]);
            }
        }

        return excluido;
    }

    // Função recursiva de exclusão.
    private boolean delete1(int chave1, int chave2, long pagina) throws Exception {

        // Declaração de variáveis
        boolean excluido = false;
        int diminuido;

        // Testa se o registro não foi encontrado na árvore, ao alcançar uma folha
        // inexistente (filho de uma folha real)
        if (pagina == -1) {
            diminuiu = false;
            return false;
        }

        // Lê o registro da página no arquivo
        arquivo.seek(pagina);
        Pagina pa = new Pagina(ordem);
        byte[] buffer = new byte[pa.TAMANHO_PAGINA];
        arquivo.read(buffer);
        pa.setBytes(buffer);

        // Encontra a página em que o par de chaves está presente
        // Nesse primeiro passo, salta todas os pares de chaves menores
        int i = 0;
        while (i < pa.n && (chave1 > pa.chaves1[i] || (chave1 == pa.chaves1[i] && chave2 > pa.chaves2[i]))) {
            i++;
        }

        // Chaves encontradas em uma folha
        if (i < pa.n && pa.filhos[0] == -1 && chave1 == pa.chaves1[i] && chave2 == pa.chaves2[i]) {

            // Puxa todas os elementos seguintes para uma posição anterior, sobrescrevendo
            // o elemento a ser excluído
            int j;
            for (j = i; j < pa.n - 1; j++) {
                pa.chaves1[j] = pa.chaves1[j + 1];
                pa.chaves2[j] = pa.chaves2[j + 1];
            }
            pa.n--;

            // limpa o último elemento
            pa.chaves1[pa.n] = 0;
            pa.chaves2[pa.n] = 0;

            // Atualiza o registro da página no arquivo
            arquivo.seek(pagina);
            arquivo.write(pa.getBytes());

            // Se a página contiver menos elementos do que o mínimo necessário,
            // indica a necessidade de fusão de páginas
            diminuiu = pa.n < maxElementos / 2;
            return true;
        }

        if (i == pa.n || chave1 < pa.chaves1[i] || (chave1 == pa.chaves1[i] && chave2 < pa.chaves2[i])) {
            excluido = delete1(chave1, chave2, pa.filhos[i]);
            diminuido = i;
        } else {
            excluido = delete1(chave1, chave2, pa.filhos[i + 1]);
            diminuido = i + 1;
        }

        // Testa se há necessidade de fusão de páginas
        if (diminuiu) {

            // Carrega a página filho que ficou com menos elementos do
            // do que o mínimo necessário
            long paginaFilho = pa.filhos[diminuido];
            Pagina pFilho = new Pagina(ordem);
            arquivo.seek(paginaFilho);
            arquivo.read(buffer);
            pFilho.setBytes(buffer);

            // Cria uma página para o irmão (da direita ou esquerda)
            long paginaIrmao;
            Pagina pIrmao;

            // Tenta a fusão com irmão esquerdo
            if (diminuido > 0) {

                // Carrega o irmão esquerdo
                paginaIrmao = pa.filhos[diminuido - 1];
                pIrmao = new Pagina(ordem);
                arquivo.seek(paginaIrmao);
                arquivo.read(buffer);
                pIrmao.setBytes(buffer);

                // Testa se o irmão pode ceder algum registro
                if (pIrmao.n > maxElementos / 2) {

                    // Move todos os elementos do filho aumentando uma posição
                    // à esquerda, gerando espaço para o elemento cedido
                    for (int j = pFilho.n; j > 0; j--) {
                        pFilho.chaves1[j] = pFilho.chaves1[j - 1];
                        pFilho.chaves2[j] = pFilho.chaves2[j - 1];
                        pFilho.filhos[j + 1] = pFilho.filhos[j];
                    }
                    pFilho.filhos[1] = pFilho.filhos[0];
                    pFilho.n++;

                    // Se for folha, copia o elemento do irmão, já que o do pai
                    // será extinto ou repetido
                    if (pFilho.filhos[0] == -1) {
                        pFilho.chaves1[0] = pIrmao.chaves1[pIrmao.n - 1];
                        pFilho.chaves2[0] = pIrmao.chaves2[pIrmao.n - 1];
                    }

                    // Se não for folha, rotaciona os elementos, descendo o elemento do pai
                    else {
                        pFilho.chaves1[0] = pa.chaves1[diminuido - 1];
                        pFilho.chaves2[0] = pa.chaves2[diminuido - 1];
                    }

                    // Copia o elemento do irmão para o pai (página atual)
                    pa.chaves1[diminuido - 1] = pIrmao.chaves1[pIrmao.n - 1];
                    pa.chaves2[diminuido - 1] = pIrmao.chaves2[pIrmao.n - 1];

                    // Reduz o elemento no irmão
                    pFilho.filhos[0] = pIrmao.filhos[pIrmao.n];
                    pIrmao.n--;
                    diminuiu = false;
                }

                // Se não puder ceder, faz a fusão dos dois irmãos
                else {

                    // Se a página reduzida não for folha, então o elemento
                    // do pai deve ser copiado para o irmão
                    if (pFilho.filhos[0] != -1) {
                        pIrmao.chaves1[pIrmao.n] = pa.chaves1[diminuido - 1];
                        pIrmao.chaves2[pIrmao.n] = pa.chaves2[diminuido - 1];
                        pIrmao.filhos[pIrmao.n + 1] = pFilho.filhos[0];
                        pIrmao.n++;
                    }

                    // Copia todos os registros para o irmão da esquerda
                    for (int j = 0; j < pFilho.n; j++) {
                        pIrmao.chaves1[pIrmao.n] = pFilho.chaves1[j];
                        pIrmao.chaves2[pIrmao.n] = pFilho.chaves2[j];
                        pIrmao.filhos[pIrmao.n + 1] = pFilho.filhos[j + 1];
                        pIrmao.n++;
                    }
                    pFilho.n = 0; // aqui o endereço do filho poderia ser incluido em uma lista encadeada no
                                  // cabeçalho, indicando os espaços reaproveitáveis

                    // Se as páginas forem folhas, copia o ponteiro para a folha seguinte
                    if (pIrmao.filhos[0] == -1)
                        pIrmao.proxima = pFilho.proxima;

                    // puxa os registros no pai
                    int j;
                    for (j = diminuido - 1; j < pa.n - 1; j++) {
                        pa.chaves1[j] = pa.chaves1[j + 1];
                        pa.chaves2[j] = pa.chaves2[j + 1];
                        pa.filhos[j + 1] = pa.filhos[j + 2];
                    }
                    pa.chaves1[j] = 0;
                    pa.chaves2[j] = 0;
                    pa.filhos[j + 1] = -1;
                    pa.n--;
                    diminuiu = pa.n < maxElementos / 2; // testa se o pai também ficou sem o número mínimo de elementos
                }
            }

            // Faz a fusão com o irmão direito
            else {

                // Carrega o irmão
                paginaIrmao = pa.filhos[diminuido + 1];
                pIrmao = new Pagina(ordem);
                arquivo.seek(paginaIrmao);
                arquivo.read(buffer);
                pIrmao.setBytes(buffer);

                // Testa se o irmão pode ceder algum elemento
                if (pIrmao.n > maxElementos / 2) {

                    // Se for folha
                    if (pFilho.filhos[0] == -1) {

                        // copia o elemento do irmão
                        pFilho.chaves1[pFilho.n] = pIrmao.chaves1[0];
                        pFilho.chaves2[pFilho.n] = pIrmao.chaves2[0];
                        pFilho.filhos[pFilho.n + 1] = pIrmao.filhos[0];
                        pFilho.n++;

                        // sobe o próximo elemento do irmão
                        pa.chaves1[diminuido] = pIrmao.chaves1[1];
                        pa.chaves2[diminuido] = pIrmao.chaves2[1];

                    }

                    // Se não for folha, rotaciona os elementos
                    else {

                        // Copia o elemento do pai, com o ponteiro esquerdo do irmão
                        pFilho.chaves1[pFilho.n] = pa.chaves1[diminuido];
                        pFilho.chaves2[pFilho.n] = pa.chaves2[diminuido];
                        pFilho.filhos[pFilho.n + 1] = pIrmao.filhos[0];
                        pFilho.n++;

                        // Sobe o elemento esquerdo do irmão para o pai
                        pa.chaves1[diminuido] = pIrmao.chaves1[0];
                        pa.chaves2[diminuido] = pIrmao.chaves2[0];
                    }

                    // move todos os registros no irmão para a esquerda
                    int j;
                    for (j = 0; j < pIrmao.n - 1; j++) {
                        pIrmao.chaves1[j] = pIrmao.chaves1[j + 1];
                        pIrmao.chaves2[j] = pIrmao.chaves2[j + 1];
                        pIrmao.filhos[j] = pIrmao.filhos[j + 1];
                    }
                    pIrmao.filhos[j] = pIrmao.filhos[j + 1];
                    pIrmao.n--;
                    diminuiu = false;
                }

                // Se não puder ceder, faz a fusão dos dois irmãos
                else {

                    // Se a página reduzida não for folha, então o elemento
                    // do pai deve ser copiado para o irmão
                    if (pFilho.filhos[0] != -1) {
                        pFilho.chaves1[pFilho.n] = pa.chaves1[diminuido];
                        pFilho.chaves2[pFilho.n] = pa.chaves2[diminuido];
                        pFilho.filhos[pFilho.n + 1] = pIrmao.filhos[0];
                        pFilho.n++;
                    }

                    // Copia todos os registros do irmão da direita
                    for (int j = 0; j < pIrmao.n; j++) {
                        pFilho.chaves1[pFilho.n] = pIrmao.chaves1[j];
                        pFilho.chaves2[pFilho.n] = pIrmao.chaves2[j];
                        pFilho.filhos[pFilho.n + 1] = pIrmao.filhos[j + 1];
                        pFilho.n++;
                    }
                    pIrmao.n = 0; // aqui o endereço do irmão poderia ser incluido em uma lista encadeada no
                                  // cabeçalho, indicando os espaços reaproveitáveis

                    // Se a página for folha, copia o ponteiro para a próxima página
                    pFilho.proxima = pIrmao.proxima;

                    // puxa os registros no pai
                    for (int j = diminuido; j < pa.n - 1; j++) {
                        pa.chaves1[j] = pa.chaves1[j + 1];
                        pa.chaves2[j] = pa.chaves2[j + 1];
                        pa.filhos[j + 1] = pa.filhos[j + 2];
                    }
                    pa.n--;
                    diminuiu = pa.n < maxElementos / 2; // testa se o pai também ficou sem o número mínimo de elementos
                }
            }

            // Atualiza todos os registros
            arquivo.seek(pagina);
            arquivo.write(pa.getBytes());
            arquivo.seek(paginaFilho);
            arquivo.write(pFilho.getBytes());
            arquivo.seek(paginaIrmao);
            arquivo.write(pIrmao.getBytes());
        }
        return excluido;
    }

    // Imprime a árvore
    public void print() throws Exception {
        long raiz;
        arquivo.seek(0);
        raiz = arquivo.readLong();
        if (raiz != -1)
            print1(raiz);
        System.out.println();
    }

    public int achar(int chav) throws Exception {
        long raiz;
        arquivo.seek(0);
        raiz = arquivo.readLong();
        if (raiz != -1)
            return achar2(chav, raiz);
        return -1;
    }

    private int achar2(int chav, long pagina) throws Exception {
        int pos = -1;
        if (pagina == -1)
            return pos;
        arquivo.seek(pagina);
        Pagina pa = new Pagina(ordem);
        byte[] buffer = new byte[pa.TAMANHO_PAGINA];
        arquivo.read(buffer);
        pa.setBytes(buffer);
        boolean bandeira = true;
        for (int i = 0, j = 0; i < pa.filhos.length && bandeira; i++, j = j + 2) {
            if (pa.chaves1[i] == chav) {
                pos = pa.chaves2[i];
                bandeira = false;
            } else {
                if (pa.chaves1[i] > chav) {
                    pos = achar2(chav, pa.filhos[j]);
                    bandeira = false;
                }
            }
        }
        if (bandeira) {
            pos = achar2(chav, pa.filhos[pa.filhos.length - 1]);
        }
        return pos;
    }

    // Impressão recursiva
    private void print1(long pagina) throws Exception {

        // Retorna das chamadas recursivas
        if (pagina == -1)
            return;
        int i;

        // Lê o registro da página passada como referência no arquivo
        arquivo.seek(pagina);
        Pagina pa = new Pagina(ordem);
        byte[] buffer = new byte[pa.TAMANHO_PAGINA];
        arquivo.read(buffer);
        pa.setBytes(buffer);

        // Imprime a página
        String endereco = String.format("%04d", pagina);
        System.out.print("Endereço na arvore: " + endereco + "  elementos presentes: " + pa.n + ":"); // endereço e
                                                                                                      // número de
                                                                                                      // elementos
        for (i = 0; i < maxElementos; i++) {
            System.out.print(
                    "(" + String.format("%04d", pa.filhos[i]) + ") " + "Id: " + String.format("%2d", pa.chaves1[i])
                            + "," + "Endereço: " + String.format("%2d", pa.chaves2[i]) + " ");
        }
        System.out.print("(" + String.format("%04d", pa.filhos[i]) + ")");
        if (pa.proxima == -1)
            System.out.println();
        else
            System.out.println(" --> (" + String.format("%04d", pa.proxima) + ")");

        // Chama recursivamente cada filho, se a página não for folha
        if (pa.filhos[0] != -1) {
            for (i = 0; i < pa.n; i++)
                print1(pa.filhos[i]);
            print1(pa.filhos[i]);
        }
    }

}

/**
 * Lista
 */
class Lista extends AEDS3 {

    // Lista Invertida
    public static boolean listaInvertidaAno(int ano) throws IOException, ParseException {
        RandomAccessFile raf = new RandomAccessFile("../Saida/Arquivo.db", "rw");
        RandomAccessFile lista = new RandomAccessFile("../Saida/listaInvertida.db", "rw");
        if (lista.length() != 0) {
            lista.setLength(0);
        }

        System.out.println("\n\tano: " + ano);
        lista.writeInt(ano);

        raf.seek(4);
        int contador = 0;
        while (raf.length() != raf.getFilePointer()) {
            double pointer = raf.getFilePointer(); // setar o ano
            if (raf.readByte() == 1) {
                byte[] b = new byte[raf.readInt()];
                raf.read(b);
                Filme a = new Filme();
                a.fromByteArray(b);
                if (a.getYear() == ano) {
                    System.out.println("\tID: " + a.getID_Series() + " - Posição: " + (int) pointer);
                    lista.writeInt(a.getID_Series());
                    lista.writeInt((int) pointer);
                    contador++;
                }
            } else {
                raf.skipBytes(raf.readInt());
            }

        }

        System.out.println("\tQuantidade de registros: " + contador);
        lista.writeInt(contador);

        lista.close();
        raf.close();
        return true;
    }

    public static boolean listaInvertidaGenero(String genero) throws IOException, ParseException {
        RandomAccessFile raf = new RandomAccessFile("../Saida/Arquivo.db", "rw");
        RandomAccessFile lista = new RandomAccessFile("../Saida/listaInvertida.db", "rw");
        if (lista.length() != 0) {
            lista.setLength(0);
        }

        System.out.println("\n\tgenero: " + genero);
        lista.writeUTF(genero);

        raf.seek(4);
        int contador = 0;
        while (raf.length() != raf.getFilePointer()) {
            double pointer = raf.getFilePointer();
            if (raf.readByte() == 1) {
                byte[] b = new byte[raf.readInt()];
                raf.read(b);
                Filme a = new Filme();
                a.fromByteArray(b);
                String[] c = new String[a.getTotalGenero()];
                c = a.Generos();
                for (int i = 0; i < c.length; i++) {
                    if (c[i].equals(genero)) {
                        System.out.println("\tID: " + a.getID_Series() + " - Posição: " + (int) pointer);
                        lista.writeInt(a.getID_Series());
                        lista.writeInt((int) pointer);
                        contador++;
                    }
                }
            } else {
                raf.skipBytes(raf.readInt());
            }
            raf.close();
        }

        System.out.println("\tQuantidade de registros: " + contador);
        lista.writeInt(contador);

        lista.close();
        return true;
    }
}
