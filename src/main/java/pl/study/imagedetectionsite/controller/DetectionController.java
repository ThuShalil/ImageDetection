package pl.study.imagedetectionsite.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.study.imagedetectionsite.service.DetectionService;
import pl.study.imagedetectionsite.service.StorageService;

@Controller
public class DetectionController {

    private final StorageService storageService;
    private final DetectionService detectionService;

    public DetectionController(StorageService storageService, DetectionService detectionService) {
        this.storageService = storageService;
        this.detectionService = detectionService;
    }

    @GetMapping("/detection/{temp}")
    public String detectionSite(Model model, @PathVariable String temp)
    {
        model.addAttribute("originalimg","/detectionres" + detectionService.getOriginalFile(temp));
        model.addAttribute("detectedimg","/detectionres" + detectionService.getDetectedFile(temp));
        model.addAttribute("results",detectionService.getTexResults(temp));
        return "detectionForm";
    }

    @GetMapping("/detectionres/{dirname:.+}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String dirname,@PathVariable String filename) {

        Resource file = storageService.loadAsResource(dirname, filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
