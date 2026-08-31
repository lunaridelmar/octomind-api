package com.mind.octo.api.mind.service;

import com.mind.octo.api.mind.dto.CreateMindRequest;
import com.mind.octo.api.mind.dto.MindResponse;
import com.mind.octo.api.mind.dto.UpdateMindRequest;
import com.mind.octo.api.mind.entity.MindEntity;
import com.mind.octo.api.mind.exception.MindNotFoundException;
import com.mind.octo.api.mind.repository.MindRepository;
import com.mind.octo.api.user.entity.OctoUserEntity;
import com.mind.octo.api.user.exception.UserNotFoundException;
import com.mind.octo.api.user.repository.OctoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MindServiceTest {

    @Mock
    private MindRepository mindRepository;

    @Mock
    private OctoUserRepository octoUserRepository;

    private MindService mindService;

    @BeforeEach
    void setUp() {
        mindService = new MindService(
                mindRepository,
                octoUserRepository
        );
    }

    @Test
    void createMind_shouldCreateAndReturnMind() {

        Long userId = 1L;

        CreateMindRequest request = new CreateMindRequest(
                "Career",
                "Grow as a software developer",
                "code",
                "blue"
        );

        OctoUserEntity user = new OctoUserEntity();
        user.setId(userId);

        MindEntity savedMind = new MindEntity();
        savedMind.setId(10L);
        savedMind.setName("Career");
        savedMind.setDescription("Grow as a software developer");
        savedMind.setIcon("code");
        savedMind.setColor("blue");
        savedMind.setUser(user);
        savedMind.setCreatedAt(Instant.parse("2026-08-31T10:00:00Z"));
        savedMind.setUpdatedAt(Instant.parse("2026-08-31T10:00:00Z"));

        when(octoUserRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(mindRepository.save(any(MindEntity.class)))
                .thenReturn(savedMind);

        MindResponse response = mindService.createMind(userId, request);
        ArgumentCaptor<MindEntity> mindCaptor =
                ArgumentCaptor.forClass(MindEntity.class);

        verify(mindRepository).save(mindCaptor.capture());

        MindEntity mindToSave = mindCaptor.getValue();

        assertThat(mindToSave.getName()).isEqualTo("Career");
        assertThat(mindToSave.getDescription())
                .isEqualTo("Grow as a software developer");
        assertThat(mindToSave.getIcon()).isEqualTo("code");
        assertThat(mindToSave.getColor()).isEqualTo("blue");
        assertThat(mindToSave.getUser()).isEqualTo(user);
        assertThat(response.id()).isEqualTo(10L);
        assertThat(response.name()).isEqualTo("Career");
        assertThat(response.description()).isEqualTo("Grow as a software developer");
        assertThat(response.icon()).isEqualTo("code");
        assertThat(response.color()).isEqualTo("blue");
        assertThat(response.archived()).isFalse();
    }

    @Test
    void createMind_shouldThrowExceptionWhenUserNotFound() {

        Long userId = 999L;

        CreateMindRequest request = new CreateMindRequest(
                "Career",
                "Grow as a software developer",
                "code",
                "blue"
        );

        when(octoUserRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                mindService.createMind(userId, request)
        )
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");

        verify(mindRepository, never())
                .save(any(MindEntity.class));
    }

    @Test
    void getMindById_shouldReturnMind() {

        Long userId = 1L;
        Long mindId = 10L;

        MindEntity mind = new MindEntity();
        mind.setId(mindId);
        mind.setName("Career");
        mind.setDescription("Grow as a software developer");
        mind.setIcon("code");
        mind.setColor("blue");
        mind.setArchived(false);
        mind.setCreatedAt(Instant.parse("2026-08-31T10:00:00Z"));
        mind.setUpdatedAt(Instant.parse("2026-08-31T10:00:00Z"));

        when(mindRepository.findByIdAndUserId(mindId, userId))
                .thenReturn(Optional.of(mind));

        MindResponse response = mindService.getMindById(userId, mindId);

        assertThat(response.id()).isEqualTo(mindId);
        assertThat(response.name()).isEqualTo("Career");
        assertThat(response.description())
                .isEqualTo("Grow as a software developer");
        assertThat(response.icon()).isEqualTo("code");
        assertThat(response.color()).isEqualTo("blue");
        assertThat(response.archived()).isFalse();

        verify(mindRepository)
                .findByIdAndUserId(mindId, userId);
    }

    @Test
    void getMindById_shouldThrowExceptionWhenMindNotFound() {

        Long userId = 1L;
        Long mindId = 999L;

        when(mindRepository.findByIdAndUserId(mindId, userId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                mindService.getMindById(userId, mindId)
        )
                .isInstanceOf(MindNotFoundException.class)
                .hasMessage("Mind not found");
    }

    @Test
    void updateMind_shouldUpdateAndReturnMind() {

        Long userId = 1L;
        Long mindId = 10L;

        UpdateMindRequest request = new UpdateMindRequest(
                "Updated Career",
                "Updated description",
                "briefcase",
                "green"
        );

        MindEntity existingMind = new MindEntity();
        existingMind.setId(mindId);
        existingMind.setName("Career");
        existingMind.setDescription("Old description");
        existingMind.setIcon("code");
        existingMind.setColor("blue");
        existingMind.setArchived(false);
        existingMind.setCreatedAt(Instant.parse("2026-08-31T10:00:00Z"));
        existingMind.setUpdatedAt(Instant.parse("2026-08-31T10:00:00Z"));

        when(mindRepository.findByIdAndUserId(mindId, userId))
                .thenReturn(Optional.of(existingMind));

        when(mindRepository.save(any(MindEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MindResponse response = mindService.updateMind(
                userId,
                mindId,
                request
        );

        assertThat(response.name()).isEqualTo("Updated Career");
        assertThat(response.description()).isEqualTo("Updated description");
        assertThat(response.icon()).isEqualTo("briefcase");
        assertThat(response.color()).isEqualTo("green");
        assertThat(response.archived()).isFalse();

        verify(mindRepository).save(existingMind);
    }

    @Test
    void updateMind_shouldThrowExceptionWhenMindNotFound() {

        Long userId = 1L;
        Long mindId = 999L;

        UpdateMindRequest request = new UpdateMindRequest(
                "Updated Career",
                "Updated description",
                "briefcase",
                "green"
        );

        when(mindRepository.findByIdAndUserId(mindId, userId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                mindService.updateMind(userId, mindId, request)
        )
                .isInstanceOf(MindNotFoundException.class)
                .hasMessage("Mind not found");

        verify(mindRepository, never())
                .save(any(MindEntity.class));
    }

    @Test
    void archiveMind_shouldArchiveMind() {

        Long userId = 1L;
        Long mindId = 10L;

        MindEntity mind = new MindEntity();
        mind.setId(mindId);
        mind.setName("Career");
        mind.setArchived(false);

        when(mindRepository.findByIdAndUserId(mindId, userId))
                .thenReturn(Optional.of(mind));

        when(mindRepository.save(any(MindEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MindResponse response = mindService.archiveMind(userId, mindId);

        assertThat(response.archived()).isTrue();
        assertThat(mind.isArchived()).isTrue();

        verify(mindRepository).save(mind);
    }

    @Test
    void restoreMind_shouldRestoreMind() {

        Long userId = 1L;
        Long mindId = 10L;

        MindEntity mind = new MindEntity();
        mind.setId(mindId);
        mind.setName("Career");
        mind.setArchived(true);

        when(mindRepository.findByIdAndUserId(mindId, userId))
                .thenReturn(Optional.of(mind));

        when(mindRepository.save(any(MindEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MindResponse response = mindService.restoreMind(userId, mindId);

        assertThat(response.archived()).isFalse();
        assertThat(mind.isArchived()).isFalse();

        verify(mindRepository).save(mind);
    }

    @Test
    void archiveMind_shouldThrowExceptionWhenMindNotFound() {

        Long userId = 1L;
        Long mindId = 999L;

        when(mindRepository.findByIdAndUserId(mindId, userId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                mindService.archiveMind(userId, mindId)
        )
                .isInstanceOf(MindNotFoundException.class)
                .hasMessage("Mind not found");

        verify(mindRepository, never())
                .save(any(MindEntity.class));
    }

    @Test
    void restoreMind_shouldThrowExceptionWhenMindNotFound() {

        Long userId = 1L;
        Long mindId = 999L;

        when(mindRepository.findByIdAndUserId(mindId, userId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                mindService.restoreMind(userId, mindId)
        )
                .isInstanceOf(MindNotFoundException.class)
                .hasMessage("Mind not found");

        verify(mindRepository, never())
                .save(any(MindEntity.class));
    }

    @Test
    void getUserMinds_shouldReturnAllUserMinds() {

        Long userId = 1L;

        MindEntity firstMind = new MindEntity();
        firstMind.setId(10L);
        firstMind.setName("Career");
        firstMind.setArchived(false);

        MindEntity secondMind = new MindEntity();
        secondMind.setId(11L);
        secondMind.setName("Spanish");
        secondMind.setArchived(true);

        when(mindRepository.findAllByUserId(userId))
                .thenReturn(List.of(firstMind, secondMind));

        List<MindResponse> response = mindService.getUserMinds(userId);

        assertThat(response).hasSize(2);
        assertThat(response.get(0).name()).isEqualTo("Career");
        assertThat(response.get(1).name()).isEqualTo("Spanish");

        verify(mindRepository).findAllByUserId(userId);
    }

    @Test
    void getActiveMinds_shouldReturnOnlyActiveMinds() {

        Long userId = 1L;

        MindEntity activeMind = new MindEntity();
        activeMind.setId(10L);
        activeMind.setName("Career");
        activeMind.setArchived(false);

        when(mindRepository.findAllByUserIdAndArchivedFalse(userId))
                .thenReturn(List.of(activeMind));

        List<MindResponse> response = mindService.getActiveMinds(userId);

        assertThat(response).hasSize(1);
        assertThat(response.getFirst().name()).isEqualTo("Career");
        assertThat(response.getFirst().archived()).isFalse();

        verify(mindRepository)
                .findAllByUserIdAndArchivedFalse(userId);
    }

    @Test
    void getArchivedMinds_shouldReturnOnlyArchivedMinds() {

        Long userId = 1L;

        MindEntity archivedMind = new MindEntity();
        archivedMind.setId(11L);
        archivedMind.setName("Spanish");
        archivedMind.setArchived(true);

        when(mindRepository.findAllByUserIdAndArchivedTrue(userId))
                .thenReturn(List.of(archivedMind));

        List<MindResponse> response = mindService.getArchivedMinds(userId);

        assertThat(response).hasSize(1);
        assertThat(response.getFirst().name()).isEqualTo("Spanish");
        assertThat(response.getFirst().archived()).isTrue();

        verify(mindRepository)
                .findAllByUserIdAndArchivedTrue(userId);
    }
}