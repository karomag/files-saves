import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static String saveGamesPath = "D:/Games/savegames";

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(100, 2, 1, 15);
        GameProgress gameProgress2 = new GameProgress(90, 5, 3, 190);
        GameProgress gameProgress3 = new GameProgress(50, 1, 2, 150);

        saveGame(saveGamesPath + "/save1.dat", gameProgress1);
        saveGame(saveGamesPath + "/save2.dat", gameProgress2);
        saveGame(saveGamesPath + "/save3.dat", gameProgress3);

        String[] saves = {saveGamesPath + "/save1.dat", saveGamesPath + "/save2.dat", saveGamesPath + "/save3.dat"};
        zipFiles(saveGamesPath + "/zip.zip", saves);
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String path, String[] saveFilesPath) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String savePath : saveFilesPath) {
                try (FileInputStream fis = new FileInputStream(savePath)) {
                    ZipEntry entry = new ZipEntry(savePath);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                File tempFile = new File(savePath);
                tempFile.delete();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}