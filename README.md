# 🐙 OctoMind

### Your skills are worth more together.

**OctoMind is an AI-powered platform designed to help people with multiple interests discover, develop, connect, and monetize their unique combinations of skills.**

Most career and productivity tools encourage people to specialize, choose one direction, or manage their interests separately.

OctoMind takes a different approach.

We believe that having many interests is not a weakness. The connections between seemingly unrelated skills can become a person's greatest competitive advantage.

**You don't have to choose between your interests. Connect them.**

---

## 💡 The Vision

Imagine someone who knows Spanish, enjoys acting, creates videos, understands programming, and loves storytelling.

Traditional career platforms might recommend becoming a translator, actor, content creator, or software developer.

OctoMind asks a different question:

**What could this person create by combining these skills?**

For example:

**Acting + Storytelling + Video Editing**

→ Offer personalized short films based on people's life stories.

**Spanish + Content Creation**

→ Produce original video content for Spanish-speaking brands.

**Programming + Languages**

→ Develop multilingual digital tools or offer localization-focused technical services.

**Art + Education**

→ Create illustrated learning materials for children.

These are not simply career suggestions. They are potential services, products, businesses, and professional opportunities that users may not have considered before.

OctoMind aims to help people turn these discoveries into practical action.

---

## 💼 OctoMind Opportunities

**The next major product priority is helping users discover how to earn money from their existing skills.**

The planned Opportunities module will transform a user's skill profile into personalized monetization recommendations.

### 1. Discover Services You Can Offer

OctoMind will analyze individual Minds and their combinations to suggest concrete services a user could offer.

Each recommendation may include:

- Service name and description
- Skills used
- Potential customers
- Suggested deliverables
- Ways to differentiate the service
- Practical steps to get started
- Possible pricing approaches

For example:

**Personalized Mini Films**

Skills: Acting, Storytelling, Video Editing

Create short personalized films inspired by clients' stories, relationships, or special occasions.

Potential customers include couples, families, creators, and small businesses.

### 2. Find Clients and Selling Channels

Discover where and how to offer each service.

OctoMind will recommend relevant channels such as:

- Freelance marketplaces
- Creator and UGC platforms
- Professional communities
- Social media
- Direct outreach
- Specialized industry platforms
- Local business opportunities

Recommendations will explain not only where to look, but also how to approach potential customers.

### 3. Discover Matching Job Opportunities

The planned job discovery system will help users identify real vacancies matching their skills, experience, languages, and preferred work arrangements.

Instead of relying exclusively on traditional job titles, OctoMind aims to recognize unconventional skill combinations.

The system will:

- Search supported sources for current job listings
- Match vacancies against user profiles
- Explain why a position may be relevant
- Highlight important missing qualifications
- Provide links to original job listings and application pages
- Help users prioritize suitable opportunities

**Job listings must come from verifiable external sources. AI-generated suggestions will be clearly distinguished from real vacancies.**

### 4. Generate Personalized Resumes

OctoMind will help users transform their diverse experiences into professional resumes.

Planned capabilities include:

- Building a structured professional profile
- Selecting relevant skills and experience
- Generating resumes for different career directions
- Tailoring resumes to specific vacancies
- Exporting resumes in PDF and DOCX formats
- Supporting multilingual resumes

The system will use verified user-provided information and will not invent professional experience or qualifications.

---

## 🐙 Mind Ocean

The foundation of OctoMind is a personal collection of **Minds**.

Each Mind represents an interest, skill, goal, or area of life.

Examples:

- Programming
- Languages
- Acting
- Writing
- Photography
- Design
- Music
- Sport

Together, these Minds form the user's personal **Mind Ocean**.

Users can organize their interests independently while also discovering connections between them.

### Current Mind Features

The backend currently supports:

- Creating Minds
- Retrieving and updating Minds
- Archiving and restoring Minds
- Filtering active and archived Minds
- Ownership-based access control

### Future Mind Development

Planned features include:

- Skill proficiency and experience
- Personal goals
- XP and levels
- Activity history
- Progress tracking
- Subskills
- Sleeping Minds and comeback activities

The goal is to make personal development encouraging rather than punitive.

---

## 🔗 AI-Powered Mind Combinations

OctoMind already includes a backend system for discovering connections between two or more Minds.

For example:

**Spanish + Photography**

→ Create bilingual photography tutorials.

**Programming + Writing**

→ Build an interactive storytelling application.

**Acting + Languages + Video Creation**

→ Produce multilingual character-based promotional videos.

### Implemented Features

- Manual Mind combination creation
- Combinations containing two or more Minds
- Random selection of active Minds
- AI-powered crossover idea generation
- Persistent storage of generated suggestions
- Retrieval of saved suggestions
- User ownership validation

### AI Architecture

The generation system uses Spring AI and a dedicated `MindCombinationGenerator` abstraction.

```text
User Minds
    ↓
Mind Combination
    ↓
MindCombinationGenerator
    ↓
Spring AI ChatClient
    ↓
Google Gemini
    ↓
Structured Suggestions
    ↓
PostgreSQL
```

A rule-based generator is also available as an alternative implementation.

The architecture separates AI integration from business logic, allowing the generation strategy and provider to evolve independently.

---

## ✅ Activities

OctoMind supports activities connected to one or multiple Minds.

For example:

**Learn 10 Spanish words**

→ Spanish

**Create a 30-second video in Spanish**

→ Spanish + Content Creation

### Implemented Features

- Create activities
- Associate activities with one or multiple Minds
- Retrieve user activities
- Retrieve individual activities
- Mark activities as completed
- Record creation and completion timestamps
- Validate resource ownership

This provides the foundation for future activity-based progress and growth systems.

---

## 🌱 Progress, Goals & Gamification

OctoMind is designed to help users maintain motivation across multiple interests.

Planned progress features include two complementary systems.

**XP and Levels**

XP will represent a user's ongoing investment in a Mind.

An activity involving multiple Minds may contribute XP to each connected Mind.

**Goal Progress**

Each Mind may contain up to three major goals, inspired by an octopus's three hearts.

For example:

**Content Creation**

- ❤️ Reach 10,000 followers
- ❤️ Publish 100 videos
- ❤️ Learn professional editing

Each goal will have measurable progress.

**Subskills**

Inspired by the distributed nervous system of an octopus, Minds may also contain specialized development areas.

For Content Creation, these might include scriptwriting, filming, editing, storytelling, analytics, and personal branding.

**Sleeping Minds**

Inactive Minds may eventually enter a sleeping state rather than penalizing users for taking breaks.

The application will encourage users to return through small, achievable activities.

---

## 👥 Who Is OctoMind For?

OctoMind is designed for:

- **Multipotentialites and polymaths** who enjoy developing several interests.
- **Multidisciplinary creatives** combining artistic, technical, and communication skills.
- **Freelancers** searching for new services they could offer.
- **Career changers** looking for opportunities beyond their previous job titles.
- **Students and lifelong learners** exploring how their skills connect.
- **People with unconventional professional backgrounds** who struggle to represent their full potential through traditional resumes.

OctoMind aims to make diverse experience an advantage rather than something users need to hide or simplify.

---

## 🛠 Current Tech Stack

### Backend

- Java 21
- Spring Boot 4
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Docker
- Maven
- JWT authentication

### AI

- Spring AI
- Spring AI `ChatClient`
- Google Gemini
- Structured AI output
- Externalized prompt templates
- Provider-independent generation abstraction
- Rule-based generation implementation

### Development & Testing

- JUnit
- Mockito
- MockMvc
- Insomnia
- Git and GitHub
- Maven build and test pipeline

### Product Design

- Figma
- UX/UI design
- Mobile product concepts
- User flows and prototyping

---

## 🚧 Development Status

**OctoMind is an early-stage product currently under development.**

The existing repository contains a working backend foundation, not a finished consumer application.

### Implemented

**Authentication**
- User registration and login
- BCrypt password hashing
- JWT authentication
- Protected API endpoints
- Input validation and centralized error handling

**Mind Management**
- Mind creation, retrieval, and updating
- Archive and restore
- Active and archived filtering
- Ownership validation

**Mind Combinations**
- Manual and random combinations
- Spring AI integration
- AI-generated crossover suggestions
- Persisted suggestions
- Suggestion retrieval

**Activities**
- Activity creation and retrieval
- Multiple Minds per activity
- Activity completion tracking

**Testing & Infrastructure**
- Unit tests for Mind services
- Controller tests with MockMvc
- API regression testing using Insomnia
- Dockerized PostgreSQL environment

### In Development

- Mind progress and XP data model
- Activity XP rewards

These features are not yet considered complete.

---

## 🗺 Product Roadmap

### Phase 1 — Skill Monetization MVP

**Highest priority**

- Structured skill profiles
- AI-generated service opportunities
- Recommendations for finding clients
- Saved monetization ideas
- Personalized next steps

**Goal:** Help users identify practical ways to earn money from their existing skills.

### Phase 2 — Career Opportunities

- Real job vacancy integrations
- Skill-based job matching
- Job relevance explanations
- Verified application links
- Professional profile management

**Goal:** Connect users with real employment opportunities that match their capabilities.

### Phase 3 — Resume Generation

- Automated resume creation
- Role-specific resume customization
- Multilingual resumes
- PDF and DOCX export

**Goal:** Help users communicate their multidisciplinary experience effectively.

### Phase 4 — Personal Growth

- XP and levels
- Goals and percentage-based progress
- Activity-based development
- Weekly progress and streaks
- Sleeping Minds
- Subskills

**Goal:** Support sustainable personal development across multiple interests.

### Phase 5 — Product Experience & Expansion

- Complete Mind Ocean interface
- Mobile-first user experience
- Personalized onboarding
- AI-assisted activity planning
- Additional AI and opportunity integrations
- Product analytics and feedback-driven improvements

---

## 🏗 Architecture Direction

The current backend is implemented as a modular Spring Boot monolith.

Authentication, Minds, combinations, activities, and AI generation are organized into separate application areas.

Future modules may include:

```text
OctoMind
│
├── Authentication
├── Minds
├── Mind Combinations
├── Activities
├── Progress
│
├── Opportunities (planned)
│   ├── Service Discovery
│   ├── Client Channels
│   └── Saved Opportunities
│
├── Career (planned)
│   ├── Professional Profiles
│   ├── Job Search
│   └── Job Matching
│
└── Resume Generation (planned)
```

The architecture is intended to evolve with the product without requiring premature microservice decomposition.

---

## 🚀 Project Direction

OctoMind is being developed as an independent product with the long-term goal of becoming a scalable platform.

The current prototype demonstrates the initial backend architecture and AI-powered Mind combination capabilities.

The next priority is validating the skill monetization concept with potential users and building a focused MVP.

The project is open to exploring opportunities for:

- Early-stage funding
- Technical collaboration
- Product design collaboration
- Startup acceleration
- Strategic partnerships

The long-term vision is to create a platform where people can discover the professional and creative value of their unique combinations of interests.

---

## 👩‍💻 Founder

**Kateryna Yashnyk (Lunari del Mar)**

Founder & Product Lead  
Java Backend Developer · Multidisciplinary Creator

**GitHub:** [github.com/lunaridelmar](https://github.com/lunaridelmar)

**LinkedIn:** [Kateryna Yashnyk](https://www.linkedin.com/in/kateryna-yashnyk-94368239a/)

**Email:** lunaridelmar@gmail.com

**Developer CV:** [View CV](KATERYNA_YASHNYK_CV.md)

---

**OctoMind — Your skills are worth more together.**