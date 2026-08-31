package com.mind.octo.api.mind.coltroller;

import com.mind.octo.api.mind.dto.CreateMindRequest;
import com.mind.octo.api.mind.dto.MindResponse;
import com.mind.octo.api.mind.dto.UpdateMindRequest;
import com.mind.octo.api.mind.service.MindService;
import com.mind.octo.api.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MindController.class)
class MindControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MindService mindService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void getMinds_shouldReturnUserMinds() throws Exception {

        Long userId = 1L;

        MindResponse mind = new MindResponse(
                10L,
                "Career",
                "Grow as a software developer",
                "code",
                "blue",
                false,
                Instant.parse("2026-08-31T10:00:00Z"),
                Instant.parse("2026-08-31T10:00:00Z")
        );

        when(mindService.getUserMinds(userId))
                .thenReturn(List.of(mind));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        mockMvc.perform(
                        get("/api/minds")
                                .with(authentication(authentication))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].name").value("Career"))
                .andExpect(jsonPath("$[0].color").value("blue"))
                .andExpect(jsonPath("$[0].archived").value(false));
    }

    @Test
    void createMind_shouldReturnCreatedMind() throws Exception {

        Long userId = 1L;

        MindResponse response = new MindResponse(
                10L,
                "Career",
                "Grow as a software developer",
                "code",
                "blue",
                false,
                Instant.parse("2026-08-31T10:00:00Z"),
                Instant.parse("2026-08-31T10:00:00Z")
        );

        when(mindService.createMind(eq(userId), any(CreateMindRequest.class)))
                .thenReturn(response);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        mockMvc.perform(
                        post("/api/minds")
                                .with(authentication(authentication))
                                .with(csrf())
                                .contentType("application/json")
                                .content("""
                                    {
                                      "name": "Career",
                                      "description": "Grow as a software developer",
                                      "icon": "code",
                                      "color": "blue"
                                    }
                                    """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Career"))
                .andExpect(jsonPath("$.description")
                        .value("Grow as a software developer"))
                .andExpect(jsonPath("$.icon").value("code"))
                .andExpect(jsonPath("$.color").value("blue"))
                .andExpect(jsonPath("$.archived").value(false));
    }

    @Test
    void createMind_shouldReturnBadRequestWhenNameIsBlank() throws Exception {

        Long userId = 1L;

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        mockMvc.perform(
                        post("/api/minds")
                                .with(authentication(authentication))
                                .with(csrf())
                                .contentType("application/json")
                                .content("""
                                    {
                                      "name": "",
                                      "description": "Test",
                                      "icon": "code",
                                      "color": "blue"
                                    }
                                    """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());

        verify(mindService, never())
                .createMind(anyLong(), any(CreateMindRequest.class));
    }

    @Test
    void getMindById_shouldReturnMind() throws Exception {

        Long userId = 1L;
        Long mindId = 10L;

        MindResponse response = new MindResponse(
                mindId,
                "Career",
                "Grow as a software developer",
                "code",
                "blue",
                false,
                Instant.parse("2026-08-31T10:00:00Z"),
                Instant.parse("2026-08-31T10:00:00Z")
        );

        when(mindService.getMindById(userId, mindId))
                .thenReturn(response);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        mockMvc.perform(
                        get("/api/minds/{id}", mindId)
                                .with(authentication(authentication))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Career"))
                .andExpect(jsonPath("$.description")
                        .value("Grow as a software developer"))
                .andExpect(jsonPath("$.archived").value(false));

        verify(mindService).getMindById(userId, mindId);
    }

    @Test
    void updateMind_shouldReturnUpdatedMind() throws Exception {

        Long userId = 1L;
        Long mindId = 10L;

        MindResponse response = new MindResponse(
                mindId,
                "Updated Career",
                "Updated description",
                "briefcase",
                "green",
                false,
                Instant.parse("2026-08-31T10:00:00Z"),
                Instant.parse("2026-08-31T11:00:00Z")
        );

        when(mindService.updateMind(
                eq(userId),
                eq(mindId),
                any(UpdateMindRequest.class)
        )).thenReturn(response);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        mockMvc.perform(
                        put("/api/minds/{id}", mindId)
                                .with(authentication(authentication))
                                .with(csrf())
                                .contentType("application/json")
                                .content("""
                                    {
                                      "name": "Updated Career",
                                      "description": "Updated description",
                                      "icon": "briefcase",
                                      "color": "green"
                                    }
                                    """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Updated Career"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.icon").value("briefcase"))
                .andExpect(jsonPath("$.color").value("green"))
                .andExpect(jsonPath("$.archived").value(false));

        verify(mindService).updateMind(
                eq(userId),
                eq(mindId),
                any(UpdateMindRequest.class)
        );
    }

    @Test
    void updateMind_shouldReturnBadRequestWhenNameIsBlank() throws Exception {

        Long userId = 1L;
        Long mindId = 10L;

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        mockMvc.perform(
                        put("/api/minds/{id}", mindId)
                                .with(authentication(authentication))
                                .with(csrf())
                                .contentType("application/json")
                                .content("""
                                    {
                                      "name": "",
                                      "description": "Updated description",
                                      "icon": "briefcase",
                                      "color": "green"
                                    }
                                    """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists());

        verify(mindService, never())
                .updateMind(
                        eq(userId),
                        eq(mindId),
                        any(UpdateMindRequest.class)
                );
    }

    @Test
    void getActiveMinds_shouldReturnActiveMinds() throws Exception {

        Long userId = 1L;

        MindResponse response = new MindResponse(
                10L,
                "Career",
                "Grow as a software developer",
                "code",
                "blue",
                false,
                Instant.parse("2026-08-31T10:00:00Z"),
                Instant.parse("2026-08-31T10:00:00Z")
        );

        when(mindService.getActiveMinds(userId))
                .thenReturn(List.of(response));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        mockMvc.perform(
                        get("/api/minds/active")
                                .with(authentication(authentication))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Career"))
                .andExpect(jsonPath("$[0].archived").value(false));

        verify(mindService).getActiveMinds(userId);
    }

    @Test
    void getArchivedMinds_shouldReturnArchivedMinds() throws Exception {

        Long userId = 1L;

        MindResponse response = new MindResponse(
                11L,
                "Spanish",
                "Improve Spanish",
                "language",
                "purple",
                true,
                Instant.parse("2026-08-31T10:00:00Z"),
                Instant.parse("2026-08-31T11:00:00Z")
        );

        when(mindService.getArchivedMinds(userId))
                .thenReturn(List.of(response));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        mockMvc.perform(
                        get("/api/minds/archived")
                                .with(authentication(authentication))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Spanish"))
                .andExpect(jsonPath("$[0].archived").value(true));

        verify(mindService).getArchivedMinds(userId);
    }

    @Test
    void archiveMind_shouldReturnArchivedMind() throws Exception {

        Long userId = 1L;
        Long mindId = 10L;

        MindResponse response = new MindResponse(
                mindId,
                "Career",
                "Grow as a software developer",
                "code",
                "blue",
                true,
                Instant.parse("2026-08-31T10:00:00Z"),
                Instant.parse("2026-08-31T11:00:00Z")
        );

        when(mindService.archiveMind(userId, mindId))
                .thenReturn(response);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        mockMvc.perform(
                        patch("/api/minds/{id}/archive", mindId)
                                .with(authentication(authentication))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.archived").value(true));

        verify(mindService).archiveMind(userId, mindId);
    }

    @Test
    void restoreMind_shouldReturnRestoredMind() throws Exception {

        Long userId = 1L;
        Long mindId = 10L;

        MindResponse response = new MindResponse(
                mindId,
                "Career",
                "Grow as a software developer",
                "code",
                "blue",
                false,
                Instant.parse("2026-08-31T10:00:00Z"),
                Instant.parse("2026-08-31T12:00:00Z")
        );

        when(mindService.restoreMind(userId, mindId))
                .thenReturn(response);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        List.of()
                );

        mockMvc.perform(
                        patch("/api/minds/{id}/restore", mindId)
                                .with(authentication(authentication))
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.archived").value(false));

        verify(mindService).restoreMind(userId, mindId);
    }
}