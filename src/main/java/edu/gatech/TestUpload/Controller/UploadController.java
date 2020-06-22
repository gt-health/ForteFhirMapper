package edu.gatech.TestUpload.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import edu.gatech.Mapping.Service.OpenMDIToVRDRService;
import edu.gatech.OpenMDI.model.OpenMDIInputFields;

@Controller
public class UploadController {
	OpenMDIToVRDRService mappingService;
	
	@Autowired
	public UploadController(OpenMDIToVRDRService mappingService) {
		this.mappingService = mappingService;
	}
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload-csv-file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of `InputField` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                // create csv bean reader
                CsvToBean<OpenMDIInputFields> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(OpenMDIInputFields.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // convert `CsvToBean` object to list of users
                List<OpenMDIInputFields> inputFields = csvToBean.parse();
                String fhirOutput = "";
                for(OpenMDIInputFields inputField: inputFields) {
                	String jsonBundle = mappingService.convertToVRDRString(inputField);
                	if(fhirOutput.isEmpty()) {
                		fhirOutput = jsonBundle;
                	}
                	System.out.println("JSON BUNDLE:");
                	System.out.println(jsonBundle);
                }
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(fhirOutput);
                String prettyFhirOutput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
                // TODO: save users in DB?

                // save users list on model
                model.addAttribute("inputFields", inputFields);
                model.addAttribute("status", true);
                model.addAttribute("fhirOutput", prettyFhirOutput);

            } catch (Exception ex) {
            	ex.printStackTrace(System.out);
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }

        return "file-upload-status";
    }
}