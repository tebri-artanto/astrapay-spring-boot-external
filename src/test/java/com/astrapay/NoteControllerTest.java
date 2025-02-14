package com.astrapay;

import com.astrapay.controller.NoteController;
import com.astrapay.dto.ApiResponseDto;
import com.astrapay.dto.NoteDto;
import com.astrapay.dto.NoteResponseDto;
import com.astrapay.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(NoteController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {NoteController.class})
public class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private ObjectMapper objectMapper;

    private NoteDto noteDto;
    private ApiResponseDto<NoteResponseDto> expectedResponse;

    @BeforeEach
    public void init(){
        noteDto = NoteDto.builder().title("Test").content("TestContent").build();
        NoteResponseDto noteResponseDto = new NoteResponseDto();
        noteResponseDto.setTitle("Test");
        noteResponseDto.setContent("TestContent");
        expectedResponse = new ApiResponseDto<>(noteResponseDto);
    }

    @Test
    public void testCreateNote() throws Exception {
        NoteResponseDto resDto = new NoteResponseDto();
        resDto.setTitle("Test");
        resDto.setContent("TestContent");
        ApiResponseDto<NoteResponseDto> expectedResDto = new ApiResponseDto<>(resDto);
        given(noteService.createNote(ArgumentMatchers.any()))
                .willReturn(expectedResDto);

        ResultActions res = mockMvc.perform(post("/v1/api/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(noteDto)));

        res.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("TestContent"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testUpdateNote() throws Exception {
        UUID id = UUID.randomUUID();
        given(noteService.updateNote(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(NoteDto.class)))
                .willReturn(expectedResponse);

        ResultActions res = mockMvc.perform(put("/v1/api/notes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(noteDto)));

        res.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("TestContent"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetAllNotes() throws Exception  {
        NoteResponseDto resDto1 = new NoteResponseDto();
        resDto1.setTitle("Test");
        resDto1.setContent("TestContent");

        NoteResponseDto resDto2 = new NoteResponseDto();
        resDto2.setTitle("Test2");
        resDto2.setContent("TestContent2");

        ApiResponseDto<Collection<NoteResponseDto>> expectedResDto = new ApiResponseDto<>(Arrays.asList(resDto1, resDto2));

        when(noteService.getAllNotes()).thenReturn(expectedResDto);
        ResultActions res = mockMvc.perform(get("/v1/api/notes")
                .contentType(MediaType.APPLICATION_JSON));


        res.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].content").value("TestContent"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].title").value("Test2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].content").value("TestContent2"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeleteNote() throws Exception {
        NoteResponseDto resDto = new NoteResponseDto();
        resDto.setTitle("Test");
        resDto.setContent("TestContent");

        UUID id = UUID.randomUUID();

        ApiResponseDto<NoteResponseDto> expectedResDto = ApiResponseDto.success(resDto, "Notes with Title: Test Successfully Deleted");
        given(noteService.deleteNote(ArgumentMatchers.any()))
                .willReturn(expectedResDto);

        ResultActions res = mockMvc.perform(delete("/v1/api/notes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON));

        res.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("TestContent"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Notes with Title: Test Successfully Deleted"))
                .andDo(MockMvcResultHandlers.print());
    }

}
