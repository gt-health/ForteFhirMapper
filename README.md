# MdiFhirDataMapping

This project contains a mapping from common Medicalogical Death Investigation (MDI) data into a FHIR format.
The components are broken out into 2 major parts
* A data component defining the mapping.
* A simple web service to import a csv of MDI data and create a set of fhir resources from that csv

This project also uses the fhir profile VRDR java library implementation found here: https://github.com/gt-health/VRDR_javalib