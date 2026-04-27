package az.cargora.cargora.controller;

import az.cargora.cargora.dto.request.newPackageRequest;
import az.cargora.cargora.entity.Package;
import az.cargora.cargora.entity.PickUpPoint;
import az.cargora.cargora.enums.PackageStatus;
import az.cargora.cargora.service.PackageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PackageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PackageService packageService;

    @Autowired
    private ObjectMapper objectMapper;

    private Package createPackage(Long id, String trackingNumber) {
        Package pkg = new Package();
        pkg.setId(id);
        pkg.setTrackingNumber(trackingNumber);
        pkg.setInternalTrackingCode("INT" + id);
        pkg.setWeight(BigDecimal.valueOf(2.5));
        pkg.setShippingFee(BigDecimal.ZERO);
        return pkg;
    }

    @Test
    void getPackage_byId_returnsPackage() throws Exception {
        Package pkg = createPackage(1L, "TRK123");

        Mockito.when(packageService.getPackageById(1L)).thenReturn(pkg);

        mockMvc.perform(get("/packages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.trackingNumber").value("TRK123"))
                .andExpect(jsonPath("$.internalTrackingCode").value("INT1"));
    }

    @Test
    void getAllPackages_ofUser_returnsList() throws Exception {
        List<Package> packages = List.of(
                createPackage(2L, "TRK222")
        );

        Mockito.when(packageService.getUserPackages(10L)).thenReturn(packages);

        mockMvc.perform(get("/packages/of-user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].trackingNumber").value("TRK222"));
    }

    @Test
    void getPackagesByStatus_returnsList() throws Exception {
        List<Package> packages = List.of(
                createPackage(3L, "TRK333")
        );

        Mockito.when(packageService.getPackagesByStatus(PackageStatus.DECLARED))
                .thenReturn(packages);

        mockMvc.perform(get("/packages/packages/status/DECLARED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].trackingNumber").value("TRK333"));
    }

    @Test
    void updateWeight_returnsUpdatedPackage() throws Exception {
        Package updated = createPackage(4L, "TRK444");
        updated.setWeight(BigDecimal.valueOf(5.5));

        Mockito.when(packageService.updateWeight(
                Mockito.eq(4L),
                Mockito.any(BigDecimal.class)
        )).thenReturn(updated);

        mockMvc.perform(patch("/packages/package/4/weight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("5.5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.weight").value(5.5));
    }

    @Test
    void updateDestinationBranch_returnsUpdatedPackage() throws Exception {
        Package updated = createPackage(5L, "TRK555");

        PickUpPoint pickUpPoint = new PickUpPoint();
        pickUpPoint.setId(1L);
        pickUpPoint.setName("Elmler filial");

        Mockito.when(packageService.updatePickUpPoints(
                Mockito.eq(5L),
                Mockito.any(PickUpPoint.class)
        )).thenReturn(updated);

        mockMvc.perform(patch("/packages/package/5/destiantionBracnh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pickUpPoint)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.trackingNumber").value("TRK555"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createPackage_returnsOkMessage() throws Exception {
        newPackageRequest request = new newPackageRequest();

        mockMvc.perform(post("/packages/create-new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Package created"));
    }
}