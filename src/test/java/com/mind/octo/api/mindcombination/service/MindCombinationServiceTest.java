package com.mind.octo.api.mindcombination.service;

import com.mind.octo.api.mind.entity.MindEntity;
import com.mind.octo.api.mind.repository.MindRepository;
import com.mind.octo.api.mindcombination.dto.CreateMindCombinationRequest;
import com.mind.octo.api.mindcombination.dto.MindCombinationResponse;
import com.mind.octo.api.mindcombination.dto.RandomMindCombinationRequest;
import com.mind.octo.api.mindcombination.entity.MindCombinationEntity;
import com.mind.octo.api.mindcombination.exception.InvalidMindCombinationException;
import com.mind.octo.api.mindcombination.exception.NotEnoughMindsException;
import com.mind.octo.api.mindcombination.generator.MindCombinationGenerator;
import com.mind.octo.api.mindcombination.repository.MindCombinationRepository;
import com.mind.octo.api.user.entity.OctoUserEntity;
import com.mind.octo.api.user.exception.UserNotFoundException;
import com.mind.octo.api.user.repository.OctoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MindCombinationServiceTest {

    @Mock
    private MindCombinationRepository mindCombinationRepository;

    @Mock
    private MindRepository mindRepository;

    @Mock
    private OctoUserRepository octoUserRepository;

    @Mock
    private MindCombinationGenerator mindCombinationGenerator;

    private MindCombinationService mindCombinationService;

    @BeforeEach
    void setUp() {
        mindCombinationService = new MindCombinationService(
                mindCombinationRepository,
                mindRepository,
                octoUserRepository,
                mindCombinationGenerator
        );
    }

    @Test
    void createCombinationCreatesCombinationWithRequestedMinds() {
        Long userId = 1L;

        MindEntity spanish = new MindEntity();
        spanish.setId(1L);
        spanish.setName("Spanish");

        MindEntity video = new MindEntity();
        video.setId(2L);
        video.setName("Video Creation");

        OctoUserEntity user = new OctoUserEntity();
        user.setId(userId);

        spanish.setUser(user);
        video.setUser(user);

        CreateMindCombinationRequest request =
                new CreateMindCombinationRequest(Set.of(1L, 2L));

        when(octoUserRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(mindRepository.findAllById(Set.of(1L, 2L)))
                .thenReturn(List.of(spanish, video));

        when(mindCombinationRepository.save(any(MindCombinationEntity.class)))
                .thenAnswer(invocation -> {
                    MindCombinationEntity combination = invocation.getArgument(0);
                    combination.setId(10L);
                    return combination;
                });

        MindCombinationResponse response =
                mindCombinationService.createCombination(userId, request);

        assertEquals(10L, response.id());
        assertEquals(2, response.minds().size());

        verify(mindCombinationRepository)
                .save(any(MindCombinationEntity.class));
    }

    @Test
    void createCombinationThrowsWhenOneOrMoreMindsAreInvalid() {
        Long userId = 1L;

        OctoUserEntity user = new OctoUserEntity();
        user.setId(userId);

        CreateMindCombinationRequest request =
                new CreateMindCombinationRequest(Set.of(1L, 999L));

        when(octoUserRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(mindRepository.findAllById(Set.of(1L, 999L)))
                .thenReturn(List.of(new MindEntity()));

        InvalidMindCombinationException exception =
                assertThrows(
                        InvalidMindCombinationException.class,
                        () -> mindCombinationService.createCombination(
                                userId,
                                request
                        )
                );

        assertEquals(
                "One or more minds are invalid",
                exception.getMessage()
        );
    }

    @Test
    void createCombinationThrowsWhenMindBelongsToAnotherUser() {
        Long userId = 1L;

        OctoUserEntity user = new OctoUserEntity();
        user.setId(userId);

        OctoUserEntity anotherUser = new OctoUserEntity();
        anotherUser.setId(2L);

        MindEntity spanish = new MindEntity();
        spanish.setId(1L);
        spanish.setUser(user);

        MindEntity foreignMind = new MindEntity();
        foreignMind.setId(2L);
        foreignMind.setUser(anotherUser);

        CreateMindCombinationRequest request =
                new CreateMindCombinationRequest(Set.of(1L, 2L));

        when(octoUserRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(mindRepository.findAllById(Set.of(1L, 2L)))
                .thenReturn(List.of(spanish, foreignMind));

        InvalidMindCombinationException exception =
                assertThrows(
                        InvalidMindCombinationException.class,
                        () -> mindCombinationService.createCombination(
                                userId,
                                request
                        )
                );

        assertEquals(
                "One or more minds are invalid",
                exception.getMessage()
        );
    }

    @Test
    void getUserCombinationsReturnsAllUserCombinations() {
        Long userId = 1L;

        MindEntity spanish = new MindEntity();
        spanish.setId(1L);
        spanish.setName("Spanish");

        MindEntity video = new MindEntity();
        video.setId(2L);
        video.setName("Video Creation");

        MindCombinationEntity firstCombination = new MindCombinationEntity();
        firstCombination.setId(10L);
        firstCombination.setMinds(Set.of(spanish, video));

        MindCombinationEntity secondCombination = new MindCombinationEntity();
        secondCombination.setId(11L);
        secondCombination.setMinds(Set.of(spanish));

        when(mindCombinationRepository.findAllByUserId(userId))
                .thenReturn(List.of(firstCombination, secondCombination));

        List<MindCombinationResponse> responses =
                mindCombinationService.getUserCombinations(userId);

        assertEquals(2, responses.size());
        assertEquals(10L, responses.get(0).id());
        assertEquals(11L, responses.get(1).id());

        verify(mindCombinationRepository)
                .findAllByUserId(userId);
    }

    @Test
    void getCombinationByIdReturnsRequestedCombination() {
        Long userId = 1L;
        Long combinationId = 10L;

        MindEntity spanish = new MindEntity();
        spanish.setId(1L);
        spanish.setName("Spanish");

        MindEntity video = new MindEntity();
        video.setId(2L);
        video.setName("Video Creation");

        MindCombinationEntity combination = new MindCombinationEntity();
        combination.setId(combinationId);
        combination.setMinds(Set.of(spanish, video));

        when(
                mindCombinationRepository.findByIdAndUserId(
                        combinationId,
                        userId
                )
        ).thenReturn(Optional.of(combination));

        MindCombinationResponse response =
                mindCombinationService.getCombinationById(
                        userId,
                        combinationId
                );

        assertEquals(combinationId, response.id());
        assertEquals(2, response.minds().size());

        verify(mindCombinationRepository)
                .findByIdAndUserId(combinationId, userId);
    }

    @Test
    void getCombinationByIdThrowsWhenCombinationDoesNotExist() {
        Long userId = 1L;
        Long combinationId = 999L;

        when(
                mindCombinationRepository.findByIdAndUserId(
                        combinationId,
                        userId
                )
        ).thenReturn(Optional.empty());

        InvalidMindCombinationException exception =
                assertThrows(
                        InvalidMindCombinationException.class,
                        () -> mindCombinationService.getCombinationById(
                                userId,
                                combinationId
                        )
                );

        assertEquals(
                "Mind combination not found",
                exception.getMessage()
        );
    }

    @Test
    void createRandomCombinationCreatesCombinationFromActiveMinds() {
        Long userId = 1L;

        OctoUserEntity user = new OctoUserEntity();
        user.setId(userId);

        MindEntity spanish = new MindEntity();
        spanish.setId(1L);
        spanish.setName("Spanish");
        spanish.setUser(user);
        spanish.setArchived(false);

        MindEntity video = new MindEntity();
        video.setId(2L);
        video.setName("Video Creation");
        video.setUser(user);
        video.setArchived(false);

        MindEntity travel = new MindEntity();
        travel.setId(3L);
        travel.setName("Travel");
        travel.setUser(user);
        travel.setArchived(false);

        RandomMindCombinationRequest request =
                new RandomMindCombinationRequest(2);

        when(octoUserRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(mindRepository.findAllByUserIdAndArchivedFalse(userId))
                .thenReturn(List.of(spanish, video, travel));

        when(mindCombinationRepository.save(any(MindCombinationEntity.class)))
                .thenAnswer(invocation -> {
                    MindCombinationEntity combination = invocation.getArgument(0);
                    combination.setId(10L);
                    return combination;
                });

        MindCombinationResponse response =
                mindCombinationService.createRandomCombination(
                        userId,
                        request
                );

        assertEquals(10L, response.id());
        assertEquals(2, response.minds().size());

        verify(mindRepository)
                .findAllByUserIdAndArchivedFalse(userId);

        verify(mindCombinationRepository)
                .save(any(MindCombinationEntity.class));
    }

    @Test
    void createRandomCombinationThrowsWhenNotEnoughActiveMinds() {
        Long userId = 1L;

        OctoUserEntity user = new OctoUserEntity();
        user.setId(userId);

        MindEntity spanish = new MindEntity();
        spanish.setId(1L);
        spanish.setUser(user);
        spanish.setArchived(false);

        RandomMindCombinationRequest request =
                new RandomMindCombinationRequest(2);

        when(octoUserRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(mindRepository.findAllByUserIdAndArchivedFalse(userId))
                .thenReturn(List.of(spanish));

        NotEnoughMindsException exception =
                assertThrows(
                        NotEnoughMindsException.class,
                        () -> mindCombinationService.createRandomCombination(
                                userId,
                                request
                        )
                );

        assertEquals(
                "Not enough active minds to create this combination",
                exception.getMessage()
        );
    }

    @Test
    void createRandomCombinationThrowsWhenUserDoesNotExist() {
        Long userId = 999L;

        RandomMindCombinationRequest request =
                new RandomMindCombinationRequest(2);

        when(octoUserRepository.findById(userId))
                .thenReturn(Optional.empty());

        UserNotFoundException exception =
                assertThrows(
                        UserNotFoundException.class,
                        () -> mindCombinationService.createRandomCombination(
                                userId,
                                request
                        )
                );

        assertEquals(
                "User not found",
                exception.getMessage()
        );
    }

    @Test
    void generateSuggestionsPassesAllCombinationMindsToGenerator() {
        Long userId = 1L;
        Long combinationId = 10L;

        MindEntity spanish = new MindEntity();
        spanish.setId(1L);
        spanish.setName("Spanish");

        MindEntity travel = new MindEntity();
        travel.setId(2L);
        travel.setName("Travel");

        MindEntity photography = new MindEntity();
        photography.setId(3L);
        photography.setName("Photography");

        MindCombinationEntity combination = new MindCombinationEntity();
        combination.setId(combinationId);
        combination.setMinds(
                Set.of(spanish, travel, photography)
        );

        when(
                mindCombinationRepository.findByIdAndUserId(
                        combinationId,
                        userId
                )
        ).thenReturn(Optional.of(combination));

        when(mindCombinationGenerator.generate(anyList()))
                .thenReturn(List.of("Test suggestion"));

        mindCombinationService.generateSuggestions(
                userId,
                combinationId
        );

        ArgumentCaptor<List<MindEntity>> captor =
                ArgumentCaptor.forClass(List.class);

        verify(mindCombinationGenerator)
                .generate(captor.capture());

        List<MindEntity> passedMinds = captor.getValue();

        assertEquals(3, passedMinds.size());
        assertTrue(passedMinds.contains(spanish));
        assertTrue(passedMinds.contains(travel));
        assertTrue(passedMinds.contains(photography));
    }

    @Test
    void generateSuggestionsThrowsWhenCombinationDoesNotExist() {
        Long userId = 1L;
        Long combinationId = 999L;

        when(
                mindCombinationRepository.findByIdAndUserId(
                        combinationId,
                        userId
                )
        ).thenReturn(Optional.empty());

        InvalidMindCombinationException exception =
                assertThrows(
                        InvalidMindCombinationException.class,
                        () -> mindCombinationService.generateSuggestions(
                                userId,
                                combinationId
                        )
                );

        assertEquals(
                "Mind combination not found",
                exception.getMessage()
        );
    }

    @Test
    void generateSuggestionsReturnsGeneratorResult() {
        Long userId = 1L;
        Long combinationId = 10L;

        MindEntity spanish = new MindEntity();
        spanish.setId(1L);
        spanish.setName("Spanish");

        MindEntity video = new MindEntity();
        video.setId(2L);
        video.setName("Video Creation");

        MindCombinationEntity combination = new MindCombinationEntity();
        combination.setId(combinationId);
        combination.setMinds(Set.of(spanish, video));

        when(
                mindCombinationRepository.findByIdAndUserId(
                        combinationId,
                        userId
                )
        ).thenReturn(Optional.of(combination));

        List<String> generatedSuggestions = List.of(
                "Suggestion one",
                "Suggestion two"
        );

        when(mindCombinationGenerator.generate(anyList()))
                .thenReturn(generatedSuggestions);

        var response = mindCombinationService.generateSuggestions(
                userId,
                combinationId
        );

        assertEquals(combinationId, response.combinationId());
        assertEquals(generatedSuggestions, response.suggestions());
    }
}