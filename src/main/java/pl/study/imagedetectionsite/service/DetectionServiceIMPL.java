package pl.study.imagedetectionsite.service;

import org.springframework.stereotype.Service;
import pl.study.imagedetectionsite.config.StorageProperties;
import pl.study.imagedetectionsite.entity.FileType;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DetectionServiceIMPL implements DetectionService {

    private final String rootLocation;

    public DetectionServiceIMPL(StorageProperties storageProperties) {
        rootLocation = storageProperties.getLocation();
    }

    @Override
    public String getOriginalFile(String dir) {
        return getFile(listFiles(dir),FileType.ORIGINAL).replaceFirst(rootLocation,"");
    }

    @Override
    public String getDetectedFile(String dir) {
        return getFile(listFiles(dir),FileType.DETECTED).replaceFirst(rootLocation,"");
    }

    @Override
    public String getResultsFile(String dir) {
        return getFile(listFiles(dir),FileType.RESULTS).replaceFirst(rootLocation,"");
    }

    @Override
    public List<String[]> getTexResults(String dir) {
        String file = getFile(listFiles(dir),FileType.RESULTS);
        List<String[]> temp = new ArrayList<>();


        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(file));
            String row;
            while((row = csvReader.readLine()) != null)
            {
                String[] data = row.split(",");
                temp.add(data);
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }





        return temp;
    }

    private List<String> listFiles(String dir)
    {
        try (Stream<Path> walk = Files.walk(Paths.get(rootLocation+"/"+dir))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFile(List<String> list, FileType type)
    {
        for(String f : list)
        {
            if(type.equals(FileType.DETECTED))
            {
                if(f.contains("detection"))
                {
                    return f;
                }
            }

            if(type.equals(FileType.RESULTS))
            {
                if(f.endsWith(".csv"))
                {
                    return f;
                }
            }

            if(type.equals(FileType.ORIGINAL))
            {
                if(!f.endsWith(".csv") && !f.contains("detection"))
                {
                    return f;
                }
            }
        }
        return null;
    }
}
