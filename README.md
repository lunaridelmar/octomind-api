# 🐙 OctoMind

OctoMind is a personal growth and productivity application for people with many interests, skills, goals, and areas of life competing for their attention.

Instead of forcing users to choose one interest and abandon the others, OctoMind helps them organize their different **Minds**, make progress in each of them, and discover meaningful ways to connect them.

The core idea is simple:

> **You don't have to choose between your interests. Connect them.**

## 💡 The Idea

Each user can create multiple **Minds** representing interests, skills, goals, or areas of life.

For example:

- Spanish
- Content Creation
- Programming
- Writing
- Art
- Sport
- Travel
- Photography

Together, these Minds form the user's personal **Mind Ocean**.

Each Mind can develop independently through activities, goals, and progress. But the key feature of OctoMind is that different Minds can also work together.

For example:

**Spanish + Content Creation**

could produce an activity such as:

> Create a 30-second video in Spanish.

That single activity contributes to both interests.

Other examples:

**Programming + Spanish**

> Read a programming article in Spanish.

**Art + Content Creation**

> Create an illustration and publish a process video.

**Writing + Spanish**

> Write a short story in Spanish.

OctoMind can generate these connections using AI, while users can also choose their own activities.

## 🐙 Minds

Minds are the central areas of a user's OctoMind.

Users can currently:

- Create Minds
- View their Minds
- Update them
- Archive and restore them
- Separate active and archived Minds
- Attach multiple Minds to the same activity

In the future, Minds will also develop through XP, goals, activity history, and other progress indicators.

Inactive Minds will eventually be able to enter a sleeping state rather than punishing the user for taking a break, creating a more encouraging way to return to abandoned interests.

## 🔗 Mind Combinations

Users can combine two or more Minds to discover useful connections between their interests.

OctoMind currently supports:

- Creating combinations manually
- Combining two or more Minds
- Random Mind selection
- AI-generated crossover suggestions
- Saving Mind combinations
- Persisting generated suggestions
- Retrieving previous suggestions
- User ownership validation

For example:

```text
Spanish + Photography
        ↓
Spring AI
        ↓
AI-generated crossover ideas
        ↓
Saved suggestions
```

The generation system is separated from the rest of the application through a `MindCombinationGenerator` abstraction.

This allows OctoMind to use different generation strategies without coupling the business logic to a specific AI provider.

Current strategies include:

- AI-powered generation through Spring AI
- Rule-based generation as an alternative implementation

Google Gemini is currently used as the AI provider, while the application architecture is designed to make provider changes easier in the future.

## ✅ Activities

Activities turn ideas into concrete actions.

An activity can belong to one Mind:

```text
Learn 10 Spanish words
        ↓
Spanish
```

or multiple Minds:

```text
Create a 30-second video in Spanish
        ↓
Spanish + Content Creation
```

The backend currently supports:

- Creating activities
- Connecting an activity to one or multiple Minds
- Retrieving user activities
- Retrieving an activity by ID
- Completing activities
- Recording completion time
- User ownership validation

This creates the foundation for the main OctoMind progress loop:

```text
Mind
 ↓
Activity
 ↓
Complete Activity
 ↓
Progress
 ↓
Mind grows
```

## 🌱 Progress System

The next stage of development is turning completed activities into meaningful progress.

Two complementary systems are planned:

**XP** represents overall investment and growth in a Mind.

```text
Spanish
XP: 1,280
Level: 7
```

**Goal progress** represents progress toward a concrete outcome.

```text
Goal: Reach C1 Spanish
Progress: 42%
```

Because one activity can belong to several Minds, completing a crossover activity will be able to contribute progress to multiple Minds at once.

## ❤️ Goals

The original OctoMind concept uses the biology of an octopus as part of the product structure rather than only as decoration.

Each Mind may eventually contain up to **3 major goals**, inspired by an octopus's three hearts.

For example:

```text
Content Creation

❤️ Reach 10K followers
❤️ Publish 100 videos
❤️ Learn professional editing
```

Each goal will have its own measurable progress.

## 🧠 Subskills

A future Mind can also contain different areas or subskills, inspired by the distributed nervous system of an octopus.

For example:

```text
Content Creation

Ideas
Scriptwriting
Filming
Editing
Storytelling
Analytics
Social Media
Design
Personal Brand
```

This will allow broad Minds to develop in more specific directions without creating a separate top-level Mind for everything.

## 🛠 Tech Stack

### Backend

- Java 21
- Spring Boot 4
- Spring Security
- Spring Data JPA
- Spring AI
- PostgreSQL
- Docker
- JWT authentication
- Maven

### AI

- Spring AI `ChatClient`
- Google Gemini
- Structured AI output
- Externalized prompt templates
- Provider-independent generation abstraction
- Rule-based fallback implementation

### Development & Testing

- JUnit
- Mockito
- MockMvc
- Insomnia
- Maven automated build and test pipeline

## 🚧 Current Progress

### Authentication

- User registration
- Login
- Password hashing with BCrypt
- JWT authentication
- Protected API endpoints
- Current user endpoint
- Input validation
- Centralized error handling

### Minds

- Create a Mind
- Get all user Minds
- Get a Mind by ID
- Update a Mind
- Archive and restore Minds
- Get active Minds
- Get archived Minds
- User ownership validation

### Mind Combinations

- Create a Mind combination
- Get user combinations
- Get a combination by ID
- Randomly select active Minds
- Generate AI suggestions
- Persist generated suggestions
- Retrieve saved suggestions
- Spring AI integration
- Structured AI responses

### Activities

- Create an activity
- Connect one or multiple Minds
- Get user activities
- Get an activity by ID
- Complete an activity
- Track creation and completion timestamps

### Testing

- Unit tests for Mind services
- Controller tests with MockMvc
- Automated API regression collection in Insomnia
- Maven build verification

## 🔜 Roadmap

The next major development areas are:

1. **Mind XP and levels**
    - Award XP when activities are completed
    - Allow crossover activities to contribute to multiple Minds
    - Use XP to represent long-term Mind growth

2. **Goals**
    - Create goals for individual Minds
    - Track percentage progress
    - Connect activities to goals

3. **Progress tracking**
    - Weekly and monthly activity
    - Completion history
    - Streaks
    - Mind growth

4. **Sleeping Minds**
    - Detect long periods without activity
    - Allow inactive Minds to enter a sleeping state
    - Provide gentle comeback activities instead of punishment for broken streaks

5. **AI → Activity workflow**
    - Turn a generated Mind-combination suggestion into a real activity
    - Complete that activity
    - Apply progress to every connected Mind

6. **Subskills**
    - Add more detailed areas inside each Mind
    - Track development across different parts of a skill

## 🏗 Architecture Direction

OctoMind is currently implemented as a modular Spring Boot monolith.

Business concepts such as Minds, combinations, activities, authentication, and AI generation are kept in separate application areas while remaining inside one deployable backend.

AI-specific integration is hidden behind application abstractions:

```text
OctoMind Business Logic
        ↓
MindCombinationGenerator
        ↓
Spring AI ChatClient
        ↓
AI Provider
```

This keeps the core product independent from a particular AI provider and leaves room for additional generation strategies as the project evolves.

## 👩‍💻 Author

**Kateryna Yashnyk (Lunari del Mar)**  
Java Backend Developer & Creator

📄 [View my Developer CV](KATERYNA_YASHNYK_CV.md)