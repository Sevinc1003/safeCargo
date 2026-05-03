package az.cargora.cargora.controller;
import az.cargora.cargora.dto.request.newPackageRequest;
import az.cargora.cargora.dto.response.PackageResponse;
import az.cargora.cargora.dto.response.PageResponse;
import az.cargora.cargora.enums.PackageStatus;
import az.cargora.cargora.repository.AccountRepository;
import az.cargora.cargora.service.PackageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PackageController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypasses security filters for pure controller testing
public class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PackageService packageService;

    @MockitoBean
    private AccountRepository accountRepository;

    @MockitoBean
    private az.cargora.cargora.security.JwtTokenProvider jwtTokenProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    // Helper method to create a fake PackageResponse
    private PackageResponse createPackageResponse(String trackingNumber) {
        return new PackageResponse(
                "PIN123",
                "Branch A",
                trackingNumber,
                "INT1",
                BigDecimal.valueOf(2.5),
                BigDecimal.ZERO,
                LocalDateTime.now(),
                null
        );
    }

    @Test
    void getPackage_byId_returnsPackage() throws Exception {
        PackageResponse pkg = createPackageResponse("TRK123");

        Mockito.when(packageService.getPackageById(1L)).thenReturn(pkg);

        mockMvc.perform(get("/packages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackingNumber").value("TRK123"))
                .andExpect(jsonPath("$.internalTrackingCode").value("INT1"));
    }

    @Test
    void getAllPackages_ofUser_returnsList() throws Exception {
        // Arrange
        List<PackageResponse> packages = List.of(createPackageResponse("TRK222"));
        
        // FIX: Create the custom PageResponse instead of Spring's PageImpl
        PageResponse<PackageResponse> customPageResponse = new PageResponse<>();
        customPageResponse.setContent(packages);
        customPageResponse.setPage(0);
        customPageResponse.setSize(10);
        customPageResponse.setTotalElements(1L);
        customPageResponse.setTotalPages(1);

        Mockito.when(packageService.getUserPackages(eq("PIN123"), any(Pageable.class)))
               .thenReturn(customPageResponse);

        // Act & Assert
        mockMvc.perform(get("/packages/of-user/PIN123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(1))
                .andExpect(jsonPath("$.content[0].trackingNumber").value("TRK222"));
    }

    @Test
    void getPackagesByStatus_returnsList() throws Exception {
        List<PackageResponse> packages = List.of(createPackageResponse("TRK333"));

        Mockito.when(packageService.getPackagesByStatus(PackageStatus.DECLARED))
                .thenReturn(packages);

        mockMvc.perform(get("/packages/status/DECLARED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].trackingNumber").value("TRK333"));
    }

    @Test
    void updateWeight_returnsUpdatedPackage() throws Exception {
        // Explicitly defining the mock arguments is safer
        Mockito.doNothing().when(packageService).updateWeight(any(Long.class), any(BigDecimal.class));

        mockMvc.perform(patch("/packages/update-weight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"packageId\": 4, \"weight\": 5.5}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Weight updated successfully"));
    }

    @Test
    void updateDestinationBranch_returnsUpdatedPackage() throws Exception {
        // Explicitly defining the mock arguments
        Mockito.doNothing().when(packageService).updatePickUpPoints(any(Long.class), any(Long.class));

        mockMvc.perform(patch("/packages/update-destinationBranch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"packageId\": 5, \"destinationBranchId\": 2}"))
                .andExpect(status().isOk())
                .andExpect(content().string("PickUpPoint updated successfully"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createPackage_returnsOkMessage() throws Exception {
        newPackageRequest request = new newPackageRequest();
        request.setUserId(1L);
        request.setDestinationBranchId(2L);
        request.setTrackingNumber("TRK999");
        request.setWeight(BigDecimal.valueOf(1.5));
        
        Mockito.doNothing().when(packageService).createPackage(any(newPackageRequest.class));

        mockMvc.perform(post("/packages/create-new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("New package successfully added"));
    }
}