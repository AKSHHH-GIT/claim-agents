# claim-agents 
- An intelligent claims triage system that processes FNOL documents, validates mandatory fields, and recommends workflow routing using rule-based classification.

# Features : 

- Accepts FNOL documents in PDF or TXT format

- Extracts required claim fields

- Detects missing mandatory fields

- Applies routing rules to classify claims

- Returns structured JSON output

# Tech Stack : 

- Java 17

- Spring Boot

- Apache PDFBox

- Maven

# How It Works

- A FNOL document (PDF or TXT) is uploaded to the API.

- The document text is extracted using Apache PDFBox (for PDFs) or standard file reading (for TXT).

- Required fields are extracted using a lightweight rule-based extractor.

- Mandatory fields are validated.

- Routing rules are applied.

- A JSON response containing extracted fields, missing fields, routing decision, and reasoning is returned.

# Setps to check with PostMan

- Set method to POST

- Set URL to:
http://localhost:8080/claims/process

- Go to the Body tab

- Select form-data

- Add a key named file

- Set its type to File

- Select a FNOL PDF or TXT file

- Click Send
