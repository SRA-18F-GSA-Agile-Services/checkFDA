### Testing Round 1
##### Interviewees
* Brian, 31-year-old male: Wednesday, June 24
* Caroline, 32-year-old female: Thursday, June 25
* Stephen, 23-year old male: Thursday, June 25

##### Raw Data File PDF:  [Validation_Testing](https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/dev/project_resources/User_Centered_Design_Process/Testing/Testing_Artifactsb/Validation_Testing_Round1_Excel.pdf)

### Development Recommendations
######Context with Search Help
* Add a description of what the user is looking at for labels, recalls, and adverse events
* Provide examples of good ways to search for top use cases
* If search box does not recognize drug name, don't allow to complete search, but have suggestions right in the search box
* Don't post device adverse events if typed in a drug name

######Better Graphic Labels 
* Age graph has overlapping 'Age Group' label and strange left hand bar
* Describe what user is looking at in for each type of graph
* Describe if looking at a graph for one drug or more than one drug
* Describe what 'event' means in terms of '1000 event results' for adverse events

######Allow Narrow Search and Learn More
* Enable clicking of graph bar and chart areas.
* Enable clicking of key words

######Recognize Key Trigger Words from Top Use Cases
* 'AND' should trigger a comparison view and drug interaction information results
* 'OR' triggers comparison results side-by-side
* 'kids', 'pediatrics' should trigger pediatric label information
* 'risks' should trigger adverse events and drug label warning information
* 'generic' should pull information from label on generic name of drug
* 'inactive ingredient' should trigger the ingredient list for that one drug, not all drugs
* 'recall' should pull recalls that meet the descriptor like 'Sabra', and not the word 'recall' itself. I want to search under the recall category for Sabra, not the recall category for 'Sabra OR recall' because then I just get ALL recalls.
* a geographic location like 'San Francisco' should pull recall map. 
* a demographic text like 'for women' should pull up demographic information about other people taking treatment.

######Future New Data Set Recommendations
* 'side effect' should trigger a list of top side effects (nausea, dizziness) when FDA info available
* 'symptom' should trigger a list of symptoms for which the drug addresses
* 'condition' should trigger a list of medical condition for which the drug addresses


