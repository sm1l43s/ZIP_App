package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip {

    private ArrayList<File> files;
    private String directory;

    public UnZip(ArrayList<File> files, String directory) {
        this.files = files;
        this.directory = directory;
    }

    public String unzip() {
        for (int i = 0; i < files.size(); i++) {
            try {
                ZipInputStream zin = new ZipInputStream(new FileInputStream(files.get(i).getAbsoluteFile()));
                ZipEntry entry;
                String name;
                long size;
                while((entry=zin.getNextEntry())!=null){

                    name = entry.getName(); // получим название файла
                    size=entry.getSize();  // получим его размер в байтах

                    // распаковка
                    FileOutputStream fout = new FileOutputStream(directory + name);
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }
                    fout.flush();
                    zin.closeEntry();
                    fout.close();
                }
                    return "Разархивация прошла успешно!";
            } catch (IOException e) {
                return "Ошибка: " + e;
            }
        }
        return "";
    }



}
