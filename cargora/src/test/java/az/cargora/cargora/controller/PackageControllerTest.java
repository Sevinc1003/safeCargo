package az.cargora.cargora.controller;

import az.cargora.cargora.dto.request.UpdateDestinationBranch;
import az.cargora.cargora.dto.request.UpdateWeightRequest;
import az.cargora.cargora.dto.request.newPackageRequest;
import az.cargora.cargora.dto.response.PackageResponse;
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
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
@AutoConfigureMockMvc(addFilters = false)
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
        List<PackageResponse> packages = List.of(createPackageResponse("TRK222"));
        Page<PackageResponse> pageResponse = new PageImpl<>(packages);

        Mockito.when(packageService.getUserPackages(eq("PIN123"), any(Pageable.class))).thenReturn(pageResponse);

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
        UpdateWeightRequest request = new UpdateWeightRequest();
        // Assuming your DTO has setters or you might need to use a constructor instead
        // request.setPackageId(4L);
        // request.setWeight(BigDecimal.valueOf(5.5));

        // Notice we use doNothing() because the service method now returns void
        Mockito.doNothing().when(packageService).updateWeight(any(), any());

        mockMvc.perform(patch("/packages/update-weight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"packageId\": 4, \"weight\": 5.5}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Weight updated successfully"));
    }

    @Test
    void updateDestinationBranch_returnsUpdatedPackage() throws Exception {
        // Notice we use doNothing() because the service method now returns void
        Mockito.doNothing().when(packageService).updatePickUpPoints(any(), any());

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

        mockMvc.perform(post("/packages/create-new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("New package successfully added"));
    }
}
