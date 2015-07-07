https://checkFDA.srarad.com
===========================

Build Status (Master)
[![Build Status](http://jenkins-hackathon.srarad.com/buildStatus/icon?job=checkFDA)](http://jenkins-hackathon.srarad.com/job/checkFDA/)

Build Status (Dev)
[![Build Status](http://jenkins-hackathon.srarad.com/buildStatus/icon?job=checkFDA-Dev)](http://jenkins-hackathon.srarad.com/job/checkFDA-Dev/)

GSA’s 18F is laying down a foundation for historic changes by reshaping how Government procures and builds digital services. By issuing an RFQ focused entirely on building a working prototype versus asking Offerors to write volumes of promises, 18F is asking Offerors to cast aside outdated practices and embrace the ethos of agility. SRA stands ready to join forces with 18F to lead the transformation. As a medium-sized company of approximately 5,400 built on an unwavering commitment to Honesty and Service® since 1976, we have partnered with our Federal Government clients through almost four decades of changes and advancements. Currently, we stand as an industry leader in Agile and DevOps approaches, and are transforming clients such as Customs and Border Protection (CBP), the Department of Veterans Affairs (VA), and other components of the Department of Homeland Security (DHS) using software development practices that help our customers excel at their missions. Our company is small enough to stay agile through market and technological challenges by operating with an integrator mindset where the best idea wins, and big enough to have reach-back to industry experts in human-centered design, agile software development, cyber security, cloud technologies, and organizational change management. Our size and culture are a perfect incubator for self-organizing, self-managing teams to come together to create amazing things, as was the case with our team for this effort.

To prepare, we agreed that Jenny Madorsky would be our Product Manager and gave her authority and responsibility for the quality of the prototype (criteria a – for evidence, see https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/issues?q=involves%3Amadjenjen). We assembled an all-star, cross-cutting team including members from our Rapid Application Development (RAD) group, a DevOps team, and our User Experience (UX) practice. We partnered with Frog Design, a global design and innovation firm focused on human-centered design to enhance our team (criteria b - https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/project_resources/Team_Overview/Our-Team.md). We collocated the majority of our team to allow for face-to-face interactions, but also accommodated our remote team members using Slack, GitHub, WebEx, and frequent conference calls.

Upon RFQ release, we first agreed on team norms and standards (https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/project_resources/Team_Overview/How-We-Work.md), discussed and agreed on everyone’s roles and responsibilities and then began to digest the challenge. To put the needs of the users first, our UX designers led an opportunity canvas building session to understand the problems, users and customers, solutions, metrics, and strategies that would to drive our product evolution (criteria d 1 -  https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/project_resources/User_Centered_Design_Process/Concepting/Opportunity-Canvas.md). We then identified and interviewed potential users to gather preliminary input on whether our ideas would solve any real user needs (criteria c – https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/project_resources/User_Centered_Design_Process/Discovery/Discovery-User-Interviews.md). From these interviews, we developed a light-weight persona named Stacy Soccer Mom that guided our design and development efforts (criteria d 2 - https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/project_resources/User_Centered_Design_Process/Discovery/Discovery-Persona.md). We identified the problems Stacy faces in her day-to-day life, considered traits that may affect our work, and brainstormed ideas on how the openFDA datasets could solve her needs. With Stacy in mind, we mapped our potential product flow on a story map using a free tool called Stories on Board and vetted the map with our interviewees (criteria d 3 – https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/project_resources/User_Centered_Design_Process/Solutioning/Solutioning-Story-Map.md).

We named our resulting idea **checkFDA**: trusted health information, one simple search. We envision a one-stop, federated search of trusted FDA information related to recalls, adverse events, and labels for food, drugs, and devices—a place where Stacy can quickly get definitive information on whether she needs to return her spinach due to an ongoing recall, or whether her son’s allergy medicine will react badly with a new prescription. We also envision benefiting the FDA by collecting information on most popular searches by location so that FDA can take proactive actions on not-yet-reported potential adverse events.

Once we settled on the problem and had a vision for our product, we began to build our backlog. Throughout our iterative development process, we continued to vet our ideas, designs, and functioning prototypes with our interviewees to ensure we stayed on track with their needs and provided usable systems. All of their feedback was captured in Google Documents and our GitHub Wiki for all team members to reference as we continued to evolve and refine the prototype (criteria f  – https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/project_resources/User_Centered_Design_Process/Discovery/Discovery_Artifacts/Discovery_Interviews_Synthesized_Excel.pdf).

We used GitHub Issues and Labels along with Waffle.io to create a Kanban board that reflected our process. The columns included Backlog, In Design, Ready for Development, In Development, Ready for Test, In Test, Ready for Deployment, and Closed to create a pull-system where team members could pull top priority work at any time. We agreed on a team cadence of three stand-ups per day and scheduled a retrospective after the first day of development to make sure we could inspect and adapt early (https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/project_resources/Team_Overview/Retrospectives.md). We created a Minimum Viable Product (MVP) milestone to timebox our work for the response deadline and added other ideas to another milestone titled Super Stretch Goals with no set deadline. When we received extensions, we created a new milestone titled Submission which we used to track user stories we could complete before the new deadlines. We created epics, features, and user stories and tracked dependencies by including references to other issues in the affected user stories. Each user story contained a lightweight user value statement and acceptance criteria. We often iterated on these stories as new information came to light from our designers, developers, or users. Each story moved through the Kanban board and met our working Definition of Done before closing (criteria g – https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/issues).

We established our development environment to accommodate releasing early, iteratively, and often. We built our application using Amazon Web Services (AWS) as the Infrastructure as a Service (Iaas) platform (criteria j – https://checkfda.srarad.com/). We set up a development server on which we and our users could test our work and a production server to which we would release major milestones. We used an entirely open-source tool stack (criteria q – https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/project_resources/Team_Overview/Our-Tools.md) to build our product starting with SRA’s own open-sourced Single-Tenant-Base Grails project (criteria i 1 – https://github.com/SRARAD/SingleTenantBase). We used Semantic UI as a design and theme framework for frontend development (criteria i 2), C3.js Charts to quickly build interactive visualizations (criteria i 3), Grails 2.4.5 for our web framework (criteria i 4), MySQL for our database (criteria i 5), and Jenkins for continuous integration (criteria l).

To support efficient delivery, we built in quality and security from the start. Our Definition of Done included unit test coverage (criteria k – https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/tree/master/test), accessibility checklist, responsive design standards (criteria h – https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/issues/6), and required that each component followed the design style guide (criteria e – https://checkfda.srarad.com/styleguide). We implemented CodeNarc and a Test Coverage plug-in for Jenkins to enforce our standards. We used Pull Requests to ensure each submitted piece of code was peer reviewed before integration. Our developers typically pair programmed to build in continuity and real-time code review. The Product Manager was always available for final quality validation and acceptance. Using AWS ensured that the product will be secure, available, scalable, and reliable. We used Docker containers to allow for stand-alone configuration of the prototype (criteria o – https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/tree/master/docker). To mitigate operational risks, we implemented the Java Melody Grails plug-in for continuous monitoring so that an administrator could take proactive action when thresholds such as memory and performance surpassed risk-tolerance levels (criteria n – https://checkfda.srarad.com/monitoring, credentials provided in checklist). We also used Grails Spring Security to create configuration management options for administrators to change passwords, data sources, and URLs when needed (criteria m – https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/Standalone_Deploy.md).

To maximize value delivery, we employed light-weight Weighted Shortest Job First (WSJF) techniques by having the Product Manager ascribe each user story a business value and the development team a job size. The largest WSJF stories went through the Kanban process first to ensure we delivered the most value using least amount of effort. We built checkFDA with the future in mind—thanks to our flexible and extensible design, it is a prototype and a product that can stand on its own as an MVP or could continue to evolve given more time. We considered non-functional requirements (NFRs) as we went to safeguard against building a product that couldn’t grow knowing that a mission like FDA’s will always demand bigger and better in the future (https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/project_resources/Team_Overview/Our-Standards.md).

Our work was always done in the open. Our wall-to-wall whiteboard filled up quickly with information radiators, idea scribbles, and team standards. The Kanban board shone brightly on the projector, and our conference line remained open each day for anyone to join. We conducted frequent demonstrations to our business stakeholders and incorporated feedback quickly. To install and run **checkFDA**, please follow instructions located in our repository (criteria p – https://github.com/SRA-18F-GSA-Agile-Services/checkFDA/blob/master/project_resources/Team_Overview/Our-Standards.md).

The SRA Team is proud to present **checkFDA** to GSA’s 18F. We want to thank 18F for the opportunity to participate in this historic, and frankly, fun effort. We know that with 18F’s vision for a better Government and with SRA’s long-standing track-record of partnership, agility, and successful transformations, we will make a strong team. Thank you for your consideration.
