package pl.study.imagedetectionsite.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("python")
public class PythonProperties {

    private String pythonBin = "python/ImageRecognition/bin/python3.6";
    private String pythonScript = "python/ImageRecognition.py";
    private String modelPath = "python/yolo.h5";

    public String getPythonBin() {
        return pythonBin;
    }

    public void setPythonBin(String pythonBin) {
        this.pythonBin = pythonBin;
    }

    public String getPythonScript() {
        return pythonScript;
    }

    public void setPythonScript(String pythonScript) {
        this.pythonScript = pythonScript;
    }

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }
}
