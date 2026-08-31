# 🐙 OctoMind

OctoMind is a personal productivity and idea-generation application for people with many interests, skills, and areas of life competing for their attention.

Instead of forcing users to focus on only one thing, OctoMind helps them organize their different interests and discover ways to connect them.

The goal is not just to manage multiple areas of life, but to turn their combination into new ideas, activities, projects, and opportunities.

## 💡 The Idea

Each user can create multiple **Minds** representing their interests, skills, goals, or areas of life.

For example:

- Spanish
- Video Creation
- Programming
- Travel
- Photography
- Cooking

Each Mind can exist independently, but the core idea of OctoMind is that users can **combine two or more Minds**.

For example:

**Spanish + Video Creation**

could lead to ideas such as:

- Create videos in Spanish
- Watch and analyze Spanish-speaking creators
- Practice Spanish by writing video scripts
- Create educational content for Spanish-speaking audiences

Users will be able to choose which Minds they want to combine or ask OctoMind to select a random combination when they want inspiration.

The long-term goal is to experiment with different ways of generating useful and unexpected connections between Minds, including custom algorithms, rule-based generation, AI, or a combination of approaches.

The project is currently in active development.

## 🛠 Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Docker
- JWT authentication
- Maven

### Development & Testing

- JUnit
- Mockito
- MockMvc
- Insomnia

## 🚧 Current Progress

### Authentication

- User registration
- Login
- Password hashing with BCrypt
- JWT authentication
- Protected API endpoints
- Current user endpoint
- Input validation and error handling

### Minds

- Create a Mind
- Get all user Minds
- Get a Mind by ID
- Update a Mind
- Archive and restore Minds
- Get active Minds
- Get archived Minds
- User ownership validation

### Testing

- Unit tests for the Mind service
- Controller tests with MockMvc
- Automated API regression collection in Insomnia

## 🔜 Roadmap

### Mind Combinations

The next major feature is the ability to combine two or more Minds.

Planned functionality:

- Manually select Minds to combine
- Support combinations of two or more Minds
- Generate ideas based on the selected Minds
- Randomly select Minds for users who want inspiration
- Save Mind combinations and generated suggestions

### Combination Engine

The generation logic will be designed as a separate component so that different strategies can be used without changing the rest of the application.

Possible strategies include:

- Rule-based generation
- Custom algorithms
- AI-powered generation
- Hybrid approaches

The initial implementation will remain inside the main OctoMind backend while keeping a clear architectural boundary. This will make it possible to move the combination engine into a separate service in the future if needed.

### Future Features

As the project grows, Minds may also contain:

- Goals
- Tasks
- Notes
- Habits
- Progress tracking

These features will build on top of the Mind system rather than replacing the core idea of connecting different interests.

## 👩‍💻 Author

**Kateryna Yashnyk (Lunari del Mar)**  
Java Backend Developer & Creator

📄 [View my Developer CV](KATERYNA_YASHNYK_CV.md)