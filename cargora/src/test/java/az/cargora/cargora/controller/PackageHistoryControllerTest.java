package az.cargora.cargora.controller;

import az.cargora.cargora.dto.response.PackageHistoryResponseDTO;
import az.cargora.cargora.dto.response.PageResponse;
import az.cargora.cargora.enums.PackageStatus;
import az.cargora.cargora.service.PackageHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PackageHistoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class PackageHistoryControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private PackageHistoryService packageHistoryService;

        @MockitoBean
        private az.cargora.cargora.security.JwtTokenProvider jwtTokenProvider;

        @Test
        void getPackageHistory_returnsHistoryPage() throws Exception {
                // Arrange
                Long packageId = 1L;
                PackageHistoryResponseDTO historyDto = new PackageHistoryResponseDTO(
                                PackageStatus.DELIVERED,
                                LocalDateTime.of(2023, 10, 1, 12, 0));

                List<PackageHistoryResponseDTO> content = List.of(historyDto);
                Page<PackageHistoryResponseDTO> page = new PageImpl<>(content, PageRequest.of(0, 10), 1);
                PageResponse<PackageHistoryResponseDTO> mockResponse = PageResponse.from(page);

                // Use any() for Pageable to avoid strict object equality issues
                when(packageHistoryService.getPackageHistory(eq(packageId), any(Pageable.class)))
                                .thenReturn(mockResponse);

                // Act & Assert
                mockMvc.perform(get("/package-history/{packageId}", packageId)
                                .param("page", "0")
                                .param("size", "10")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                // Check your PageResponse field names!
                                // If PageResponse uses 'content', use $.content. If it uses 'data', use $.data.
                                .andExpect(jsonPath("$.content[0].status").value("DELIVERED"))
                                .andExpect(jsonPath("$.totalElements").value(1));

                verify(packageHistoryService).getPackageHistory(eq(packageId), any(Pageable.class));
        }
}
