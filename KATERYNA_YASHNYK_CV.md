# KATERYNA YASHNYK

### Java Backend Developer | Spring Boot | REST APIs | PostgreSQL

Ukraine · Remote · Open to Relocation

**Email:** lunaridelmar@gmail.com  
**Phone:** +380 93 663 45 99  
**LinkedIn:** [linkedin.com/in/kateryna-yashnyk-94368239a](https://www.linkedin.com/in/kateryna-yashnyk-94368239a/)  
**GitHub:** [github.com/lunaridelmar](https://github.com/lunaridelmar)  
**OctoMind API:** [github.com/lunaridelmar/octomind-api](https://github.com/lunaridelmar/octomind-api)

---

## PROFESSIONAL SUMMARY

Java Backend Developer with **3 years of professional software development experience**, specializing in **Java, Spring Boot, REST APIs, SQL, and relational databases**.

Commercial experience includes development of a **PSD2/Open Banking platform based on the Berlin Group NextGenPSD2 XS2A standard**.

Currently developing **OctoMind**, a Java 21 and Spring Boot application with PostgreSQL persistence, JWT authentication, Spring Security, automated testing, AI-powered functionality with Spring AI, and domain features for connecting multiple areas of user interest.

Additional experience in **UX/UI and digital product development** provides a broader product perspective and helps me approach backend development in terms of both technical architecture and the complete user experience.

---

## TECHNICAL SKILLS

**Backend:** Java 21 · Spring Boot · Spring Web · Spring Security · Spring Data JPA · Hibernate · REST APIs

**AI Integration:** Spring AI · ChatClient · Google Gemini · Structured AI Output · Prompt Engineering · Provider-Abstraction Patterns

**Security:** JWT Authentication · BCrypt · Authentication & Authorization · Bean Validation

**Databases:** PostgreSQL · SQL · Relational Database Design

**Testing:** JUnit · Mockito · MockMvc · Insomnia · API Regression Testing

**Development:** Git · GitHub · Maven · IntelliJ IDEA · Docker · JSON · Debugging

**Architecture & Practices:** Layered Architecture · DTOs · Repository Pattern · Service Layer · Exception Handling · API Design · Validation · Ownership-Based Resource Access · Dependency Abstraction

**Domain:** Open Banking · PSD2 · Berlin Group NextGenPSD2 · XS2A

**Product & Design:** Figma · UX/UI Design · Mobile Product Design · User Flows · Prototyping

**Additional:** HTML · CSS · AI Tools · Digital Content Production

---

## PROFESSIONAL EXPERIENCE

### Java Backend Developer

**Golden Dimension | Aug 2019 – Jun 2022**

**Project:** adorsys XS2A — Berlin Group NextGenPSD2 Open Banking Platform  
**Repository:** [github.com/adorsys/xs2a](https://github.com/adorsys/xs2a)

Worked on a Java-based Open Banking platform implementing the **Berlin Group NextGenPSD2 XS2A standard**, enabling integration between banks and third-party payment service providers (TPPs).

- Developed and maintained backend functionality and business logic using Java.
- Designed and implemented REST API endpoints for PSD2/Open Banking functionality.
- Worked with SQL, relational databases, and data-driven backend components.
- Contributed to banking integration functionality, including account access, payments, and consent-related flows.
- Debugged backend issues and participated in code reviews and technical discussions.
- Collaborated with frontend developers and QA engineers on feature implementation and testing.
- Participated in Agile development processes.
- Used Git-based workflows for version control and collaborative development.

---

## PROJECTS

### OctoMind — Java Backend & Product Development

**Personal Project | 2026 – Present**

**Backend:** Java 21 · Spring Boot 4 · Spring Security · Spring Data JPA · PostgreSQL · Hibernate · JWT · Maven · Docker  
**AI:** Spring AI · Google Gemini · Structured AI Output  
**Testing:** JUnit · Mockito · MockMvc · Insomnia  
**Product Design:** Figma · UX/UI

**GitHub:**  
[github.com/lunaridelmar/octomind-api](https://github.com/lunaridelmar/octomind-api)

**Figma:**  
[figma.com/design/yDyHPyHGCwxA6x0ZIxU8pQ/Octo-mind](https://www.figma.com/design/yDyHPyHGCwxA6x0ZIxU8pQ/Octo-mind)

OctoMind is a personal growth and productivity application for people with multiple interests, skills, goals, and areas of focus.

Users organize different areas of their lives into separate **Minds** and can connect multiple Minds to discover activities that develop several interests simultaneously.

For example:

`Spanish + Content Creation → Create a 30-second video in Spanish`

I develop the backend architecture and REST API while also designing the product's mobile UX/UI.

### Current Backend Implementation

- Built a REST API using Java 21 and Spring Boot with controller, service, repository, entity, and DTO layers.
- Implemented user registration and login with BCrypt password hashing.
- Implemented JWT-based authentication and protected endpoints with Spring Security.
- Integrated PostgreSQL persistence using Spring Data JPA and Hibernate.
- Implemented request validation with Jakarta Bean Validation.
- Added centralized exception handling and structured API error responses.
- Implemented ownership-based access control for user resources.
- Implemented Mind creation, retrieval, updating, archiving, restoring, and status filtering.
- Implemented creation and retrieval of combinations containing two or more Minds.
- Added random Mind combination generation.
- Designed a provider-independent `MindCombinationGenerator` abstraction.
- Integrated **Spring AI** using `ChatClient`.
- Integrated **Google Gemini** for AI-powered Mind combination suggestions.
- Implemented structured AI responses mapped directly to Java domain DTOs.
- Externalized AI prompt templates from application logic.
- Persisted AI-generated suggestions in PostgreSQL and exposed them through the REST API.
- Implemented Activities that can belong to one or multiple Minds.
- Implemented activity creation, retrieval, and completion tracking.
- Added activity creation and completion timestamps as the foundation for progress tracking.
- Added unit testing with JUnit and Mockito.
- Added controller testing with MockMvc.
- Built an automated API regression collection with Insomnia.
- Configured a Dockerized PostgreSQL development environment.
- Maintained incremental feature-based development using Git and GitHub.
- Designed the mobile product architecture, user flows, and interfaces in Figma.

### Architecture

OctoMind is currently developed as a modular Spring Boot monolith with clear boundaries between authentication, Minds, Mind combinations, Activities, and AI generation.

AI functionality is separated from application business logic through an abstraction:

```text
Business Logic
      ↓
MindCombinationGenerator
      ↓
Spring AI ChatClient
      ↓
AI Provider
```

This allows the AI provider and generation strategy to evolve without coupling the core application logic to a specific provider.

### Current Development Direction

The next stage of OctoMind focuses on the application's core progress loop:

`Mind → Activity → Completion → Progress → Growth`

Planned functionality includes:

- XP and levels for individual Minds.
- Goal creation and percentage-based goal progress.
- Activities contributing progress to multiple Minds.
- Weekly and monthly progress tracking.
- Streaks and activity history.
- Sleeping/inactive Mind states and comeback activities.
- Converting AI-generated crossover suggestions directly into actionable activities.
- Subskills and more detailed development areas within each Mind.

Alongside backend development, I design the **mobile UX/UI in Figma**, including application structure, user flows, interfaces, and product concepts.

---

## ADDITIONAL EXPERIENCE

### Multilingual Content Creator & Digital Producer

**Independent | 2023 – Present**

Manage digital projects for international audiences from concept through production and release, using analytics, content production, AI tools, and data-driven iteration.

### Online Spanish Tutor

**Self-employed | 2022 – Present**

Teach Spanish to adults and children from different linguistic backgrounds and develop personalized educational materials.

### Spanish Voice-Over Artist

**Freelance | 2024 – Present**

Record and edit Spanish-language voice-over for commercial, educational, narration, and character projects.

---

## SELECTED DESIGN WORK

### OctoMind — Mobile Product

**UX/UI · Mobile Product Design · Figma**

Designed the UX/UI and product concept while simultaneously developing the application's Java backend.

[figma.com/design/yDyHPyHGCwxA6x0ZIxU8pQ/Octo-mind](https://www.figma.com/design/yDyHPyHGCwxA6x0ZIxU8pQ/Octo-mind)

### Voice-Over Portfolio Website

**Web Design · Wix Studio**

Designed and built a portfolio website for voice-over work.

[lunaridelmar.wixstudio.com/aolanikauhilani](https://lunaridelmar.wixstudio.com/aolanikauhilani)

---

## EDUCATION

**National University of State Fiscal Service of Ukraine — Irpin, Ukraine**

**Master's Degree, Customs Control and Audit** | 2015–2017  
**Bachelor's Degree, Audit** | 2013–2015

---

## LANGUAGES

**Ukrainian** — Native  
**Russian** — Native  
**Spanish** — Fluent  
**English** — Fluent  
**Dutch** — Intermediatea