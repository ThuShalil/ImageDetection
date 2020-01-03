package pl.study.imagedetectionsite.service;

import java.util.List;

public interface DetectionService {
    String getOriginalFile(String dir);
    String getDetectedFile(String dir);
    String getResultsFile(String dir);
    List<String[]> getTexResults(String dir);
}
