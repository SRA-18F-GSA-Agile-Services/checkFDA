### Definition of Done

A User Story is considered done once
* A pull request with the desired functionality has been issued
* The issued pull request is free of CodeNarc violations and has unit tested methods
* The new functionality has been proven through a suite automated tests
* The code once peer reviewed has been merged into the dev branch
  * Peer reviews insure quality through code organization and clarity, sufficient unit and automated testing, and non duplicated code. 
* The build has been deployed to the staging site through Jenkins
* The new functionality has been through functional user testing
* The final version has been vetted with our product owner

Additional considerations are applied to each user story, including additions to code quality, security, accessibility, performance, and scalability based on each user story's requirements. 

### Non Functional Requirements 

The standards for most of our non functional requirements are dependent on AWS and follow their [AWS EC2 SLA](http://aws.amazon.com/ec2/sla/).  

By using AWS we can assure solutions regarding scalability, availability, reliability, and maintainability. If we were to continue this project we could assure scalability through load balancers for new EC2 instances for increased site traffic. For availability and reliability AWS EC2 SLA guarantees 99.95% up time or else a bill credit will be issued regarding the downtime. By using our MVC (Grails) and coding practices required by CodeNark allows our application retain maintainability. 


### Security

We review security for all pages to ensure we have defended against cross-site scripting (XSS) and cross-site request forgery (CSRF) attacks. 

### Accessibility 
Pages are evaluated for accessibility to ensure that the following use cases pass:
* I need to be able to execute product functions from a keyboard on software where the function itself or the result of performing a function can be discerned textually.
* I need to be able to have on-screen focus on all UI elements that can be accessed by the keyboard. 
* I need to have an indicator of the current focus on the UI that also moves among interactive UI elements as the input focus changes.
* The application needs to have the on-screen focus programmatically exposed so that my AT can track focus and focus changes.
* I need at least one mode of operation and information retrieval that does not require vision. I need the system to support the use of assistive technology that is used by blind or visually impaired users.
* The application needs to have appropriate names, alt text, table summaries, and labels that are programmatically linked to the correct user interface elements.