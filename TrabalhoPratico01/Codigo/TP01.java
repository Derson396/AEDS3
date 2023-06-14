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
public class TP01 {
    public static void main(String[] args) throws Exception {
        int input = 1;
        Scanner a = new Scanner(System.in);
        while (input != 0) {
            System.out.println("\nSelecione uma opção:\n" +
                    "1.Criar a base dados\n" +
                    "2.Ler da base de dados\n" +
                    "3.Criar novo registro\n"+
                    "4.Remover um registro\n" +
                    "5.Atualizar um registro\n" +
                    "6.Pesquisar registro pelo id\n" +
                    "7.Ordenação externa parametrizavel\n" +
                    "0.Sair");

            input = a.nextInt();

            switch (input) {
                case 1:
                    Criar();
                    break;
                case 2:
                    ler();
                    break;
                    case 3:
                    System.out.println("Qual o nome do filme?");
                    Filme novo =new Filme();
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
                    System.out.print("ID a ser apagado:");
                    if (CRUD.delete(a.nextInt())) {
                        System.out.println("\nApagado com sucesso");
                    }
                    break;
                case 5:
                    atualizar();
                    break;
                case 6:
                    CRUD.Read(a.nextInt()).imprime();;
                    break;
                case 7:
                    System.out.println("Insira quantos caminhos e quantos blocos:");
                    VariableIntercalation abc = new VariableIntercalation(a.nextInt(), a.nextInt());
                    abc.sort();
                    break;
                case 0:
                    ;
                    break;
                default:
                    break;

            }
        }
        a.close();
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
     * @throws ParseException
     * @throws IOException
     *                        Atualiza um campo do registro desejado
     */
    private static void atualizar() throws ParseException, IOException {
        System.out.print("Id a ser alterado");
        Scanner abc = new Scanner(System.in);
        Filme movie = new Filme();
        movie = CRUD.Read(abc.nextInt());
        int input = 6;
        if (movie != null) {
            while (true) {
                movie.imprime();
                System.out.println("\nDeseja alterar:\n" +
                        "1.Nome\n" +
                        "2.Data de lançamento(Deverá ser formatado por exemplo como: 1820-01-30 dia e mes com no minimo 2 digitos)\n"
                        +
                        "3.Genero\n" +
                        "4.Nota IMDB\n" +
                        "0.Sair");
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
                "ID: (" + ID_Series + ") Titulo: (" + Series_Title + ") Data de lançamento: (" + Released_Year
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

}

// CRUD------------------------------------------------------------------
class CRUD {
    /**
     * @param objeto
     * @param rota
     * @param certo
     * @throws FileNotFoundException
     * @throws IOException
     *                               Create para ser usado exclusivamente para a
     *                               classe de Ordenação
     */
    public static void Create(Filme objeto, String rota, boolean certo) throws FileNotFoundException, IOException {
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
        }
    }

    /**
     * @param objeto
     * @param entrada
     * @throws IOException
     * @throws ParseException
     *                        Create padrão
     */
    public static void Create(Filme objeto) throws IOException, ParseException {
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
     * @throws IOException
     * @throws ParseException
     *                        Atualiza o registro no arquivo
     */
    public static boolean update(Filme novoObj) throws IOException, ParseException {
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
     * @throws IOException
     * @throws ParseException
     *                        deleta o registro no arquivo pelo seu id
     */
    public static boolean delete(int busca) throws IOException, ParseException {
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
        sortedFile.close();fileTempFinal.close();
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
            if (filminho!=null) {
                target.writeByte(1);
                target.writeInt(filminho.toByteArray().length);
                target.write(filminho.toByteArray());    
            }
        }
    }
}