package az.cargora.cargora.service;

import az.cargora.cargora.dto.response.PackageHistoryResponseDTO;
import az.cargora.cargora.entity.Package;
import az.cargora.cargora.entity.PackageHistory;
import az.cargora.cargora.entity.User;
import az.cargora.cargora.enums.PackageStatus;
import az.cargora.cargora.repository.PackageHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackageHistoryServiceTest {

    @Mock
    private PackageHistoryRepository repo;

    @Mock
    private UserService userService;

    // InjectMocks automatically injects the mocked repo and userService into the PackageHistoryService
    @InjectMocks
    private PackageHistoryService packageHistoryService;

    @Test
    void testNewStatus_WhenStatusIsDelivered_ShouldAddBonusAndSaveHistory() {
        // 1. Arrange: Set up our test data
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setBonus(new BigDecimal("10.0"));

        Package mockPackage = new Package();
        mockPackage.setUser(mockUser);

        // 2. Act: Call the method we are testing
        packageHistoryService.newStatus(mockPackage, PackageStatus.DELIVERED);

        // 3. Assert: Verify the expected behaviors occurred

        // Verify that updateBonus was called exactly once with the correct User ID and new Bonus (10.0 + 2.5 = 12.5)
        verify(userService, times(1)).updateBonus(eq(1L), eq(new BigDecimal("12.5")));

        // Verify that the history repository save method was called exactly once
        verify(repo, times(1)).save(any(PackageHistory.class));
    }

    @Test
    void testNewStatus_WhenStatusIsNotDelivered_ShouldOnlySaveHistory() {
        // 1. Arrange: Set up test data
        User mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setBonus(new BigDecimal("10.0"));

        Package mockPackage = new Package();
        mockPackage.setUser(mockUser);

        // 2. Act: Call the method with a status OTHER than DELIVERED
        packageHistoryService.newStatus(mockPackage, PackageStatus.ON_THE_WAY); // Assuming ON_THE_WAY exists

        // 3. Assert: Verify behaviors

        // Verify that updateBonus was NEVER called because it's not delivered
        verify(userService, never()).updateBonus(anyLong(), any());

        // Verify that the history repository save method was still called exactly once
        ArgumentCaptor<PackageHistory> historyCaptor = ArgumentCaptor.forClass(PackageHistory.class);
        verify(repo, times(1)).save(historyCaptor.capture());
        
        // Ensure the saved history has the correct status
        assertEquals(PackageStatus.ON_THE_WAY, historyCaptor.getValue().getStatus());
    }

    @Test
    void testGetPackageHistory_ShouldReturnMappedPage() {
        // 1. Arrange
        Long packageId = 100L;
        Pageable pageable = PageRequest.of(0, 10);
        
        Package mockPackage = new Package();
        PackageHistory history = new PackageHistory(mockPackage, PackageStatus.DELIVERED);
        history.setTimestamp(LocalDateTime.now());
        
        Page<PackageHistory> mockPage = new PageImpl<>(List.of(history));
        
        when(repo.findByRelatedPackageId(packageId, pageable)).thenReturn(mockPage);

        // 2. Act
        Page<PackageHistoryResponseDTO> result = packageHistoryService.getPackageHistory(packageId, pageable);

        // 3. Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(PackageStatus.DELIVERED, result.getContent().get(0).getStatus());
        
        verify(repo, times(1)).findByRelatedPackageId(packageId, pageable);
    }
}
