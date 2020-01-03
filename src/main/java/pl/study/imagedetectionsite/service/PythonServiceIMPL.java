package pl.study.imagedetectionsite.service;

import org.springframework.stereotype.Service;
import pl.study.imagedetectionsite.config.PythonProperties;

import java.io.IOException;

@Service
public class PythonServiceIMPL implements PythonService {

    private final String pythonBin;
    private final String pythonScript;
    private final String modelPath;

    public PythonServiceIMPL(PythonProperties properties) {

        pythonBin = properties.getPythonBin();
        pythonScript = properties.getPythonScript();
        modelPath = properties.getModelPath();
    }

    @Override
    public String getImageDetectionResoult(String type, String image) {

        ProcessBuilder processBuilder = new ProcessBuilder(pythonBin,pythonScript,
                "--type",type,
                "--modelpath",modelPath,
                "--file",image,
                "--resoult",image.substring(0,image.indexOf(".")));
        processBuilder.inheritIO();
        try {
            Process process = processBuilder.start();
            while(process.isAlive()){}

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
